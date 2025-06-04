package com.myproject.myproject.entities.Product;

import com.myproject.myproject.api.DTO.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(nullable = false)
    private double price;

    @Column(length = 1024, nullable = false)
    private String description;

    @Column()
    private String category;

    @Column(nullable = false)
    private String image;

    @Embedded
    private Rating rating;

    @Column(nullable = false)
    private int stock;

    public Product() {
    }

    public Product(Long id, String title, double price, String description, String category, String image,
            Rating rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rating = rating;
        this.stock = 50;
    }

    public Product(ProductDTO p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.description = p.getDescription();
        this.category = p.getCategory();
        this.image = p.getImage();
        this.rating = new Rating(p.getRating());
        this.stock = 50;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
