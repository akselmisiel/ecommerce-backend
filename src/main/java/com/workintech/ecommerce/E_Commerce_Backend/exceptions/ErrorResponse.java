package com.workintech.ecommerce.E_Commerce_Backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private HttpStatus status;
}
