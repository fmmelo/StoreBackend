package com.myproject.myproject.api.DTO;

import java.util.List;

import com.myproject.myproject.entities.Cart.Cart;

public class CartDTO {

    private String username;
    private List<ItemDTO> items;
    private double total;

    public CartDTO() {
    }

    public CartDTO(Cart c) {
        this.username = c.getId();
        this.items = c.getProducts().stream().map(cp -> new ItemDTO(cp)).toList();
        this.total = c.getTotal();
    }

    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.username = id;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
