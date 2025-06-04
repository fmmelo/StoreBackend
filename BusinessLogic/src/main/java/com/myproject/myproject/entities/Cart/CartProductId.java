package com.myproject.myproject.entities.Cart;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class CartProductId implements Serializable {

    private String cartId;
    private Long productId;

    public CartProductId() {
    }

    public CartProductId(String username, Long productId) {
        this.cartId = username;
        this.productId = productId;
    }

    public boolean equals(Object o) {
        if (!(o instanceof CartProductId))
            return false;

        CartProductId cpId = (CartProductId) o;

        return this.cartId.equals(cpId.getCartId()) && this.productId.equals(cpId.getProductId());
    }

    public int hashCode() {
        return Objects.hash(this.cartId, this.productId);
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
