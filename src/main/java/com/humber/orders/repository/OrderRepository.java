package com.humber.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.humber.orders.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

