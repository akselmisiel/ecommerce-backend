package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.CartDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.CartItemDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Cart;
import com.workintech.ecommerce.E_Commerce_Backend.entity.CartItem;

import java.util.List;

public interface CartService {

    Cart getCartByUserId(Long userId);
    List<CartItemDTO> getCartItemsByUserId(Long userId);
    CartItemDTO addItemToCart(Long userId, CartItemDTO cartItemDTO);
    CartItemDTO removeItemFromCart(Long userId, Long cartItemId);
    CartItemDTO updateCartItemQuantity(Long userId, Long productId, int quantity);
    CartDTO clearCart(Long userId);
    CartDTO convertToDTO(Cart cart);
    CartItemDTO convertToDTO(CartItem cartItem);
    Cart convertToEntity(CartDTO cartDTO);
}

