package com.myproject.myproject.entities.Cart;

import com.myproject.myproject.entities.Product.Product;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class CartProduct {

    @EmbeddedId
    private CartProductId id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "product_id")
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "cart_id")
    private Product product;

    private int quantity;

    public CartProduct() {
    }

    public CartProduct(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.id = new CartProductId(cart.getId(), product.getId());
    }

    public CartProductId getId() {
        return id;
    }

    public void setId(CartProductId id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
