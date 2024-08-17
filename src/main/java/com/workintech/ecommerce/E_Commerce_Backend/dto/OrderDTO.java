package com.workintech.ecommerce.E_Commerce_Backend.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record OrderDTO(

        Long id,

        @NotBlank(message = "Order date is required")
        String orderDate,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Total price is required")
        @Positive(message = "Total price must be positive")
        Double totalPrice,

        @NotNull(message = "Order items cannot be null")
        List<OrderItemDTO> orderItems,

        @NotNull(message = "User ID is required")
        Long userId
) {}
