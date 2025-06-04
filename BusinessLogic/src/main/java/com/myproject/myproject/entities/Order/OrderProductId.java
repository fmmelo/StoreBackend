package com.myproject.myproject.entities.Order;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderProductId implements Serializable {

    private Long orderId;
    private Long productId;

    public OrderProductId() {
    }

    public OrderProductId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public boolean equals(Object o) {
        if (!(o instanceof OrderProductId))
            return false;

        OrderProductId opId = (OrderProductId) o;

        return this.orderId.equals(opId.getOrderId()) && this.productId.equals(opId.getProductId());
    }

    public int hashCode() {
        return Objects.hash(this.orderId, this.productId);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
