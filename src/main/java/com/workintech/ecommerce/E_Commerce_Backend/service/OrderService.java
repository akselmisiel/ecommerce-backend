package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(Long userId);
    OrderDTO findOrderById(Long orderId);
    List<OrderDTO> findOrdersByUserId(Long userId);
    List<OrderDTO> findAllOrders();
    OrderDTO deleteOrder(Long orderId);
}
