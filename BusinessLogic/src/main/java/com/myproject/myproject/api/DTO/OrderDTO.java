package com.myproject.myproject.api.DTO;

import java.util.List;

import com.myproject.myproject.entities.Order.Order;

public class OrderDTO {

    private Long id;
    private double total;
    private double shipping;
    private List<ItemDTO> products;

    public OrderDTO() {
    }

    public OrderDTO(Long id, double total, double shipping, List<ItemDTO> products) {
        this.id = id;
        this.total = total;
        this.shipping = shipping;
        this.products = products;
    }

    public OrderDTO(Order o) {
        this.id = o.getId();
        this.total = o.getTotal();
        this.shipping = o.getShipping();
        this.products = o.getProducts().stream().map(p -> new ItemDTO(p)).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public List<ItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ItemDTO> products) {
        this.products = products;
    }
}
