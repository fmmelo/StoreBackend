package com.myproject.myproject.entities.Product;

import com.myproject.myproject.api.DTO.RatingDTO;

import jakarta.persistence.Embeddable;

@Embeddable
public class Rating {

    private double rate;

    private int count;

    public Rating() {
    }

    public Rating(double rate, int count) {
        this.rate = rate;
        this.count = count;
    }

    public Rating(RatingDTO r) {
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
