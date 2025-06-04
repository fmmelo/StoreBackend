package com.myproject.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.myproject.entities.Order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
