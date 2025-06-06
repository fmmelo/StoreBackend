package com.myproject.myproject.entities.Cart;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"cart\"")
public class Cart {

    @Id
    private String id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> products;

    @Column
    private double total;

    public Cart() {
        this.products = new ArrayList<>();
        this.total = 0.0;
    }

    public Cart(String username) {
        this.id = username;
        this.products = new ArrayList<>();
        this.total = 0.0;
    }

    public Cart(String username, List<CartProduct> products, int total) {
        this.id = username;
        this.products = products;
        this.total = total;
    }

    public void addCartProduct(CartProduct cartProduct) {
        this.products.add(cartProduct);
        cartProduct.setCart(this);
    }

    public void removeCartProduct(CartProduct cartProduct) {
        int index = this.products.indexOf(cartProduct);
        CartProduct cp = this.products.get(index);
        if (cp.getQuantity() - cartProduct.getQuantity() <= 0) {
            this.products.remove(cp);
            cp.setCart(null);
        } else {
            cp.setQuantity(cp.getQuantity() - cartProduct.getQuantity());
        }
    }

    public void checkout() {
        this.products.removeAll(this.products);
        this.total = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void subtractFromTotal(double amount) {
        this.total -= amount;
    }

    public void addToTotal(double amount) {
        this.total += amount;
    }
}
