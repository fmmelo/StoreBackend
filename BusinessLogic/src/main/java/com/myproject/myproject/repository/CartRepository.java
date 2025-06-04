package com.myproject.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.myproject.entities.Cart.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {

}
