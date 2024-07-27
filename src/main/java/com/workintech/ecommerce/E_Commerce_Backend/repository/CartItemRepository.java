package com.workintech.ecommerce.E_Commerce_Backend.repository;

import com.workintech.ecommerce.E_Commerce_Backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
