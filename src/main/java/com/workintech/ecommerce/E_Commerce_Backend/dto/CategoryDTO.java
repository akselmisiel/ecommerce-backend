package com.workintech.ecommerce.E_Commerce_Backend.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record CategoryDTO(
        Long id,

        @NotBlank(message = "Category name is required")

        @Size(max = 100, message = "Category name must be less than 100 characters")
        String categoryName,

        Long parentCategoryId,

        List<Long> subCategoryIds,

        List<Long> productIds
) {}
