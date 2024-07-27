package com.workintech.ecommerce.E_Commerce_Backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank(message = "First name is required")
        @Size(max = 55, message = "First name must be less than 55 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 55, message = "Last name must be less than 55 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Mobile number is required")
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Mobile number should be valid")
        String mobileNumber
){}
