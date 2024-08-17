package com.workintech.ecommerce.E_Commerce_Backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemDTO(
        @NotNull(message = "Order ID is required")
        Long orderId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        Integer quantity,

        @NotNull(message = "Product ID is required")
        Long productId
) {}
