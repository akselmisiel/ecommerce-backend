package com.workintech.ecommerce.E_Commerce_Backend.controller;

import com.workintech.ecommerce.E_Commerce_Backend.dto.*;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Address;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Order;
import com.workintech.ecommerce.E_Commerce_Backend.entity.User;
import com.workintech.ecommerce.E_Commerce_Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User user = userService.createUser(createUserDTO);
        return new ResponseEntity<>(convertToDTO(user), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserResponseDTO userResponseDTO) {
        User updatedUser = userService.updateUser(id, userResponseDTO);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id) {
        User user = userService.deleteUser(id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserResponseDTO> authenticateUser(@RequestBody AuthenticationRequest request) {
        User user = userService.authenticateUser(request.email(), request.password());
        return ResponseEntity.ok(convertToDTO(user));
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<UserResponseDTO> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        User user = userService.changePassword(id, request.newPassword());
        return ResponseEntity.ok(convertToDTO(user));
    }

//
//    @GetMapping("/{id}/cart")
//    public ResponseEntity<CartDTO> getUserCart(@PathVariable Long id) {
//        CartDTO cart = userService.getUserCart(id);
//        return ResponseEntity.ok(cart);
//    }
//
//    @GetMapping("/{id}/orders")
//    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long id) {
//        List<Order> orders = userService.getUserOrders(id);
//        return ResponseEntity.ok(orders);
//    }
//
//    @GetMapping("/{id}/orders/{orderId}")
//    public ResponseEntity<Order> getUserOrder(@PathVariable Long id, @PathVariable Long orderId) {
//        Order order = userService.getUserOrder(id, orderId);
//        return ResponseEntity.ok(order);
//    }

    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getMobileNumber());
    }

}
