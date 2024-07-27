package com.workintech.ecommerce.E_Commerce_Backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO(

        @NotBlank(message = "Address line is required")
        @Size(max = 255, message = "Address line must be less than 255 characters")
        String addressLine,

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City must be less than 100 characters")
        String city,

        @NotBlank(message = "Country is required")
        @Size(max = 100, message = "Country must be less than 100 characters")
        String country,

        @NotBlank(message = "Postal code is required")
        @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Postal code should be valid")
        String postalCode,

        @NotBlank(message = "Street name is required")
        @Size(max = 100, message = "Street name must be less than 100 characters")
        String streetName,

        @Size(max = 100, message = "Building name must be less than 100 characters")
        String buildingName
) {}
