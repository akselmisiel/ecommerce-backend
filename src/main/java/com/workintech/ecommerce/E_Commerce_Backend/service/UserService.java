package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.*;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Address;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Order;
import com.workintech.ecommerce.E_Commerce_Backend.entity.User;

import java.util.List;

public interface UserService {
    User createUser(CreateUserDTO createUserDTO);
    List<UserResponseDTO> getAllUsers();
    User updateUser(Long id, UserResponseDTO userResponseDTO);
    User deleteUser(Long userId);
    User getUserByEmail(String email);
    User getUserById(Long userId);
    UserResponseDTO convertToUserResponseDTO(User user);

    User authenticateUser(String email, String password);
    User changePassword(Long userId, String newPassword);

//    List<Address> getUserAddresses(Long userId);
//    Address addUserAddress(Long userId, AddressDTO addressDTO);
//    Address updateUserAddress(Long userId, Long addressId, AddressDTO addressDTO);
//    Address deleteUserAddress(Long userId, Long addressId);
//
//    CartDTO getUserCart(Long userId);
//
//    List<Order> getUserOrders(Long userId);
//    Order getUserOrder(Long userId, Long orderId);
//    //Order addUserOrder(Long userId, OrderDTO orderDTO);




}
