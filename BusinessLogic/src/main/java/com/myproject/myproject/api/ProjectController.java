package com.myproject.myproject.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myproject.myproject.api.DTO.OrderDTO;
import com.myproject.myproject.api.DTO.CartOperationDTO;
import com.myproject.myproject.api.DTO.CartDTO;
import com.myproject.myproject.api.DTO.ItemDTO;
import com.myproject.myproject.api.DTO.ProductDTO;
import com.myproject.myproject.response.ErrorResponse;
import com.myproject.myproject.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/project") // TODO change to storeapi
@RestController
public class ProjectController {

    private final StoreService storeService;

    public ProjectController(StoreService projectService) {
        this.storeService = projectService;
    }

    @Operation(summary = "Get an order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/product/{id}")
    ResponseEntity<?> getProduct(@PathVariable Long id) {
        return storeService.getProduct(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list (empty or not) of all products", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Token is not valid")
    })
    @GetMapping("/product")
    ResponseEntity<?> getAllProducts(@RequestHeader(name = "authorization") String token) {
        return storeService.getAllProducts(token);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The order was created succesfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "A Product was out of stock", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "A Product was not found"),
            @ApiResponse(responseCode = "400", description = "Token is not valid")
    })
    @PostMapping(path = "/checkout", consumes = "application/json")
    public ResponseEntity<?> processCheckout(@RequestHeader(name = "authorization") String token) {
        return storeService.processCheckout(token);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Token is not valid")
    })
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return storeService.getOrder(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list (empty or not) of all orders", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Token is not valid")
    })
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(@RequestHeader(name = "authorization") String token) {
        return storeService.getAllOrders(token);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Adds the requested item to the Cart of the user with the requested token", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "Token is not valid or Product out of stock"),
            @ApiResponse(responseCode = "404", description = "Product does not exist")
    })
    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestHeader(name = "authorization") String token,
            @RequestBody CartOperationDTO item) {
        return storeService.addToCart(item.getItemId(), item.getQuantity(), token);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Removes the requested item from the Cart of the user with the requested token", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "Token is not valid or Product out of stock"),
            @ApiResponse(responseCode = "404", description = "Product does not exist")
    })
    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestHeader(name = "authorization") String token,
            @RequestBody CartOperationDTO item) {
        return storeService.removeFromCart(item.getItemId(), item.getQuantity(), token);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adds the requested item to the Cart of the user with the requested token", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "Token is not valid")
    })
    @GetMapping("/cart")
    public ResponseEntity<?> getCart(@RequestHeader(name = "authorization") String token) {
        return storeService.getCart(token);
    }

}
