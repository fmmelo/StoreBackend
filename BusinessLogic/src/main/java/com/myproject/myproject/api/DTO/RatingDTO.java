package com.myproject.myproject.api.DTO;

import com.myproject.myproject.entities.Product.Rating;

public class RatingDTO {
    private double rate;
    private int count;

    public RatingDTO() {
    }

    public RatingDTO(double value, int count) {
        this.rate = value;
        this.count = count;
    }

    public RatingDTO(Rating r) {
        this.rate = r.getRate();
        this.count = r.getCount();
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
