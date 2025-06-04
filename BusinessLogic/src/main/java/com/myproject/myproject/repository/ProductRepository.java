package com.myproject.myproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.myproject.entities.Product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findAllByOrderById();

}
