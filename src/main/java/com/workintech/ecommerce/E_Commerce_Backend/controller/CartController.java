package com.workintech.ecommerce.E_Commerce_Backend.controller;

import com.workintech.ecommerce.E_Commerce_Backend.dto.CartDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.CartItemDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Cart;
import com.workintech.ecommerce.E_Commerce_Backend.entity.CartItem;
import com.workintech.ecommerce.E_Commerce_Backend.entity.User;
import com.workintech.ecommerce.E_Commerce_Backend.service.CartService;
import com.workintech.ecommerce.E_Commerce_Backend.service.ProductService;
import com.workintech.ecommerce.E_Commerce_Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;
    private UserService userService;
    private ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                .map(cartItem -> new CartItemDTO(cartItem.getQuantity(), cartItem.getProduct().getId()))
                .toList();
        CartDTO cartDTO = new CartDTO(cart.getTotalPrice(), cartItemDTOs);
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/user/{userId}/items")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItemDTO> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<CartItemDTO> addItemToCart(@PathVariable Long userId, @Valid @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO addedItemDTO = cartService.addItemToCart(userId, cartItemDTO);
        return new ResponseEntity<>(addedItemDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{userId}/items/{productId}") //by product id
    public ResponseEntity<CartItemDTO> removeItemFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        CartItemDTO removedItem = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(removedItem);
    }

    @PutMapping("/user/{userId}/items/{productId}/{quantity}")
    public ResponseEntity<CartItemDTO> updateCartItemQuantity(@PathVariable Long userId, @PathVariable Long productId, @PathVariable int quantity) {
        CartItemDTO updatedItem = cartService.updateCartItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long userId) {
        CartDTO clearedCart = cartService.clearCart(userId);
        return ResponseEntity.ok(clearedCart);
    }

}
