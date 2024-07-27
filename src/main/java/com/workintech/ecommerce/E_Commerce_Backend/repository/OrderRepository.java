package com.workintech.ecommerce.E_Commerce_Backend.repository;

import com.workintech.ecommerce.E_Commerce_Backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
