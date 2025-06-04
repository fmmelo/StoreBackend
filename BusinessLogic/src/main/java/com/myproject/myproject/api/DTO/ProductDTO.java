package com.myproject.myproject.api.DTO;

import com.myproject.myproject.entities.Product.Product;

public class ProductDTO {

    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private RatingDTO rating;
    private int stock;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String title, double price, String description, String category, String image,
            RatingDTO rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rating = rating;
    }

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.description = p.getDescription();
        this.category = p.getCategory();
        this.image = p.getImage();
        this.rating = new RatingDTO(p.getRating());
        this.stock = p.getStock();
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

    public void setPrice(double price) {
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

    public RatingDTO getRating() {
        return rating;
    }

    public void setRating(RatingDTO rating) {
        this.rating = rating;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
