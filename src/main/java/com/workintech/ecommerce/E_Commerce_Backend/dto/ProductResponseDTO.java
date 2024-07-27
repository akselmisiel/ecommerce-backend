package com.workintech.ecommerce.E_Commerce_Backend.dto;

import jakarta.validation.constraints.*;

public record ProductResponseDTO(

        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must be less than 100 characters")
        String productName,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or positive")
        Double price,

        @NotNull(message = "Quantity is required")
        @PositiveOrZero(message = "Quantity must be zero or positive")
        Integer quantity,

        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,

        @Size(max = 255, message = "Product image URL must be less than 255 characters")
        String productImage,

        @NotNull(message = "Category is required")
        Long categoryId
) {}
