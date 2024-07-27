package com.workintech.ecommerce.E_Commerce_Backend.dto;

import com.workintech.ecommerce.E_Commerce_Backend.entity.CartItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record CartDTO(

        @NotNull(message = "Total price is required")
        @PositiveOrZero(message = "Total price must be zero or positive")
        Double totalPrice,

        List<CartItemDTO> cartItems

//        @NotNull(message = "User is required")
//        Long userId
) {}
