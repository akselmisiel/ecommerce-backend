package com.workintech.ecommerce.E_Commerce_Backend.repository;

import com.workintech.ecommerce.E_Commerce_Backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
}
