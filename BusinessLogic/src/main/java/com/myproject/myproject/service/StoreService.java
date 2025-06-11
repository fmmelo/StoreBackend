package com.myproject.myproject.service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myproject.myproject.api.DTO.OrderDTO;
import com.myproject.myproject.api.DTO.CartDTO;
import com.myproject.myproject.api.DTO.ProductDTO;
import com.myproject.myproject.entities.Cart.Cart;
import com.myproject.myproject.entities.Cart.CartProduct;
import com.myproject.myproject.entities.Cart.CartProductId;
import com.myproject.myproject.entities.Order.Order;
import com.myproject.myproject.entities.Order.OrderProduct;
import com.myproject.myproject.entities.Product.Product;
import com.myproject.myproject.entities.Product.Rating;
import com.myproject.myproject.exception.InvalidTokenException;
import com.myproject.myproject.repository.CartRepository;
import com.myproject.myproject.repository.OrderRepository;
import com.myproject.myproject.repository.ProductRepository;
import com.myproject.myproject.response.ErrorResponse;

import jakarta.annotation.PostConstruct;

@Service
public class StoreService {

    private final AuthRESTClientService authClient;

    private final ProductRepository productRepo;

    private final OrderRepository orderRepo;

    private final CartRepository cartRepo;

    private final Logger logger = Logger.getLogger(StoreService.class.getName());

    public StoreService(ProductRepository productRepo, OrderRepository orderRepo, CartRepository cartRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        authClient = new AuthRESTClientService();
        authClient.build(RestClient.builder());
    }

    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "Populating Database elements");
        try {
            String responseBody = authClient.getAllProducts();

            Gson gson = new Gson();
            JsonArray jsonProducts = JsonParser.parseString(responseBody).getAsJsonArray();

            jsonProducts.forEach(element -> {
                JsonObject product = element.getAsJsonObject();
                Product p = new Product(
                        product.get("id").getAsLong(),
                        product.get("title").getAsString(),
                        product.get("price").getAsDouble(),
                        product.get("description").getAsString(),
                        product.get("category").getAsString(),
                        product.get("image").getAsString(),
                        gson.fromJson(product.get("rating"), Rating.class));
                productRepo.save(p);
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public ResponseEntity<?> getProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ProductDTO(optionalProduct.get()));
    }

    public ResponseEntity<?> getAllProducts(String token) {
        try {
            authClient.validateToken(token);
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Token was not valid", token));
        }
        return ResponseEntity.ok(productRepo.findAllByOrderById().stream().map(p -> new ProductDTO(p)).toList());
    }

    public ResponseEntity<?> processCheckout(String token) {
        String username;
        try {
            username = authClient.validateToken(token).username();
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Token was not valid", token));
        }

        double total = 0;
        double shipping = 0.0;
        Order order = new Order();

        Optional<Cart> optionalCart = cartRepo.findById(username);
        if (!optionalCart.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Cart cart = optionalCart.get();
        for (CartProduct item : cart.getProducts()) {
            Optional<Product> optionalProduct = productRepo.findById(item.getProduct().getId());
            if (!optionalProduct.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Product p = optionalProduct.get();
            if (p.getStock() < item.getQuantity()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Out of stock", "id: " + item.getProduct().getId()));
            }
            p.setStock(p.getStock() - item.getQuantity());
            productRepo.save(p);
            total += p.getPrice() * item.getQuantity();

            OrderProduct op = new OrderProduct(order, p, item.getQuantity());
            order.addOrderProduct(op);
        }
        cart.checkout();
        cartRepo.save(cart);

        order.setUsername(username);
        order.setTotal(total);
        order.setShipping(shipping);
        orderRepo.save(order);

        return ResponseEntity.ok(new OrderDTO(order));
    }

    public ResponseEntity<?> getOrder(Long id) {
        Optional<Order> order = orderRepo.findById(id);
        if (!order.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new OrderDTO(order.get()));
    }

    public ResponseEntity<?> getAllOrders(String token) {
        String username;
        try {
            username = authClient.validateToken(token).username();
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Token was not valid", token));
        }

        return ResponseEntity.ok(orderRepo.findAllByUsernameOrderById(username).stream().map(o -> new OrderDTO(o)).toList());
    }

    public ResponseEntity<?> addToCart(Long itemId, int quantity, String token) {
        String username;
        try {
            username = authClient.validateToken(token).username();
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Token was not valid", token));
        }

        Optional<Product> optionalProduct = productRepo.findById(itemId);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();
        if (product.getStock() < quantity) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Out of stock", "id: " + itemId));
        }

        Optional<Cart> optionalCart = cartRepo.findById(username);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart(username);
        }

        CartProductId productIdToAdd = new CartProductId(cart.getId(), product.getId());
        Optional<CartProduct> optionalCp = cart.getProducts().stream().filter(p -> p.getId().equals(productIdToAdd))
                .findFirst();
        CartProduct cp;
        if (!optionalCp.isPresent()) {
            cp = new CartProduct(cart, product, quantity);
            cart.addCartProduct(cp);
        } else {
            cp = optionalCp.get();
            cp.setQuantity(cp.getQuantity() + quantity);
        }
        cart.addToTotal(product.getPrice() * quantity);
        cartRepo.save(cart);

        return ResponseEntity.accepted().body(new CartDTO(cart));
    }

    public ResponseEntity<?> removeFromCart(Long itemId, int quantity, String token) {
        String username;
        try {
            username = authClient.validateToken(token).username();
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Token was not valid", token));
        }

        Optional<Product> optionalProduct = productRepo.findById(itemId);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cart> optionalCart = cartRepo.findById(username);
        if (!optionalCart.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = optionalCart.get();
        Product product = optionalProduct.get();
        CartProductId productIdToRemove = new CartProductId(cart.getId(), product.getId());
        CartProduct cp = cart.getProducts().stream().filter(p -> p.getId().equals(productIdToRemove)).findFirst().get();

        if (cp.getQuantity() <= quantity) {
            cart.getProducts().remove(cp);
        } else {
            cp.setQuantity(cp.getQuantity() - quantity);
        }

        cart.subtractFromTotal(product.getPrice() * quantity);
        cartRepo.save(cart);

        return ResponseEntity.ok().body(new CartDTO(cart));
    }

    public ResponseEntity<?> getCart(String token) {
        String username;
        try {
            username = authClient.validateToken(token).username();
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Token was not valid", token));
        }

        Optional<Cart> optionalCart = cartRepo.findById(username);
        if (optionalCart.isPresent()) {
            return ResponseEntity.ok(new CartDTO(optionalCart.get()));
        } else {
            Cart c = new Cart(username);
            cartRepo.save(c);
            return ResponseEntity.ok(new CartDTO(c));
        }
    }
}
