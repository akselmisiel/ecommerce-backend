package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.*;
import com.workintech.ecommerce.E_Commerce_Backend.entity.*;
import com.workintech.ecommerce.E_Commerce_Backend.repository.AddressRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.CartRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.ProductRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository, AddressRepository addressRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }

    @Override
    public User createUser(CreateUserDTO createUserDTO) {
        if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User createdUser = new User();
        Cart cart = new Cart();
        createdUser.setFirstName(createUserDTO.firstName());
        createdUser.setLastName(createUserDTO.lastName());
        createdUser.setEmail(createUserDTO.email());
        createdUser.setPassword(createUserDTO.password());
        createdUser.setMobileNumber(createUserDTO.mobileNumber());
        createdUser.setCart(cart);
        cart.setUser(createdUser);

        User savedUser =  userRepository.save(createdUser);
        cartRepository.save(cart);

        return savedUser;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(Long id, UserResponseDTO userResponseDTO) {
          User foundUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            foundUser.setFirstName(userResponseDTO.firstName());
            foundUser.setLastName(userResponseDTO.lastName());
            foundUser.setEmail(userResponseDTO.email());
            foundUser.setMobileNumber(userResponseDTO.mobileNumber());
            return userRepository.save(foundUser);
    }

    @Override
    public User deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(userId);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User authenticateUser(String email, String password) {
        return userRepository.authenticateUser(email, password).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

//    @Override
//    public List<Address> getUserAddresses(Long userId) {
//        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getAddresses();
//    }
//
//    @Override
//    public Address addUserAddress(Long userId, AddressDTO addressDTO) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Address address = new Address();
//        address.setAddressLine(addressDTO.addressLine());
//        address.setCity(addressDTO.city());
//        address.setCountry(addressDTO.country());
//        address.setPostalCode(addressDTO.postalCode());
//        address.setStreetName(addressDTO.streetName());
//        address.setBuildingName(addressDTO.buildingName());
//        user.getAddresses().add(address);
//        userRepository.save(user);
//        return address;
//    }
//
//    @Override
//    public Address updateUserAddress(Long userId, Long addressId, AddressDTO addressDTO) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Address address = user.getAddresses().stream().filter(a -> a.getId().equals(addressId)).findFirst().orElseThrow(() -> new RuntimeException("Address not found"));
//        address.setAddressLine(addressDTO.addressLine());
//        address.setCity(addressDTO.city());
//        address.setCountry(addressDTO.country());
//        address.setPostalCode(addressDTO.postalCode());
//        address.setStreetName(addressDTO.streetName());
//        address.setBuildingName(addressDTO.buildingName());
//        addressRepository.save(address);
//        userRepository.save(user);
//        return address;
//    }
//
//    @Override
//    public Address deleteUserAddress(Long userId, Long addressId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Address address = user.getAddresses().stream().filter(a -> a.getId().equals(addressId)).findFirst().orElseThrow(() -> new RuntimeException("Address not found"));
//        user.getAddresses().remove(address);
//        userRepository.save(user);
//        addressRepository.deleteById(addressId);
//        return address;
//    }
//
//
//    @Override
//    public CartDTO getUserCart(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Cart cart = user.getCart();
//        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
//                .map(cartItem -> new CartItemDTO(cartItem.getQuantity(), cartItem.getProduct().getId()))
//                .collect(Collectors.toList());
//        return new CartDTO(cart.getTotalPrice(), cartItemDTOs);
//
//    }
//
//    @Override
//    public List<Order> getUserOrders(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        return user.getOrders();
//    }
//
//    @Override
//    public Order getUserOrder(Long userId, Long orderId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        return user.getOrders().stream().filter(o -> o.getId().equals(orderId)).findFirst().orElseThrow(() -> new RuntimeException("Order not found"));
//    }

//    @Override
//    public Order addUserOrder(Long userId, OrderDTO orderDTO) {
//        return null;
//    }

    public UserResponseDTO convertToUserResponseDTO(User user) {
        return new UserResponseDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getMobileNumber());
    }
}
