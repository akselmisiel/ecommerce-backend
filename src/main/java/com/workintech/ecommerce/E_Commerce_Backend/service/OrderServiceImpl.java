package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.OrderDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.OrderItemDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Cart;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Order;
import com.workintech.ecommerce.E_Commerce_Backend.entity.OrderItem;
import com.workintech.ecommerce.E_Commerce_Backend.entity.User;
import com.workintech.ecommerce.E_Commerce_Backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
    }


    @Override
    public OrderDTO createOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setOrderDate(getCurrentDateTime());
        order.setEmail(user.getEmail());
        order.setTotalPrice(cart.getTotalPrice());
        order.setUser(user);

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setOrderProduct(cartItem.getProduct());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        cartService.clearCart(userId);

        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<OrderDTO> findOrdersByUserId(Long userId) {
        return userRepository.findById(userId)
                .map(user -> user.getOrders().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getOrderDate(),
                order.getEmail(),
                order.getTotalPrice(),
                order.getOrderItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getOrder().getId(),
                                orderItem.getQuantity(),
                                orderItem.getOrderProduct().getId()
                        ))
                        .collect(Collectors.toList()),
                order.getUser().getId()
        );
    }

    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getOrder().getId(),
                orderItem.getQuantity(),
                orderItem.getOrderProduct().getId()
        );
    }

    private String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
