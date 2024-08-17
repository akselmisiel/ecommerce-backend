package com.workintech.ecommerce.E_Commerce_Backend.controller;

import com.workintech.ecommerce.E_Commerce_Backend.dto.OrderDTO;
import com.workintech.ecommerce.E_Commerce_Backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long userId) {
        OrderDTO orderDTO = orderService.createOrder(userId);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.findOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> findOrdersByUserId(@PathVariable Long userId) {
        List<OrderDTO> orderDTOs = orderService.findOrdersByUserId(userId);
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> findAllOrders() {
        List<OrderDTO> orderDTOs = orderService.findAllOrders();
        return ResponseEntity.ok(orderDTOs);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(orderDTO);
    }

}
