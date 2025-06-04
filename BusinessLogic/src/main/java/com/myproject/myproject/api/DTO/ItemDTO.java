package com.myproject.myproject.api.DTO;

import com.myproject.myproject.entities.Cart.CartProduct;
import com.myproject.myproject.entities.Order.OrderProduct;

public class ItemDTO {
    private ProductDTO product;
    private int quantity;

    public ItemDTO() {
    }

    public ItemDTO(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ItemDTO(OrderProduct p) {
        this.product = new ProductDTO(p.getProduct());
        this.quantity = p.getQuantity();
    }

    public ItemDTO(CartProduct p) {
        this.product = new ProductDTO(p.getProduct());
        this.quantity = p.getQuantity();
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
