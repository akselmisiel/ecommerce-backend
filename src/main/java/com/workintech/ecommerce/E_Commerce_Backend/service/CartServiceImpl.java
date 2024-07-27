package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.CartDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.CartItemDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Cart;
import com.workintech.ecommerce.E_Commerce_Backend.entity.CartItem;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Product;
import com.workintech.ecommerce.E_Commerce_Backend.entity.User;
import com.workintech.ecommerce.E_Commerce_Backend.repository.CartItemRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.CartRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.ProductRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService{

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCart();
    }

    @Override
    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItemDTO> cartItemDTOs = user.getCart().getCartItems().stream()
                .map(cartItem -> new CartItemDTO(cartItem.getQuantity(), cartItem.getProduct().getId()))
                .collect(Collectors.toList());
        return cartItemDTOs;
    }

    @Override
    public CartItemDTO addItemToCart(Long userId, CartItemDTO cartItemDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();

        Product product = productRepository.findById(cartItemDTO.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<CartItem> existingItems = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .collect(Collectors.toList());

        if (!existingItems.isEmpty()) {
            CartItem existingItem = existingItems.get(0);
            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.quantity());
            cartItemRepository.save(existingItem);
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * cartItemDTO.quantity());
            return new CartItemDTO(existingItem.getQuantity(), existingItem.getProduct().getId());
        }

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.quantity());
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItemRepository.save(cartItem);
        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * cartItemDTO.quantity());
        cartRepository.save(cart);

        return cartItemDTO;

    }

    @Override
    public CartItemDTO removeItemFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItemRepository.delete(cartItem);
        cartItems.remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartRepository.save(cart);
        return convertToDTO(cartItem);


//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Cart cart = user.getCart();
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
//        cartItemRepository.delete(cartItem);
//        cart.getCartItems().remove(cartItem);
//        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice() * cartItem.getQuantity());
//        cartRepository.save(cart);
//        return convertToDTO(cartItem);
    }

    @Override
    public CartItemDTO updateCartItemQuantity(Long userId, Long productId, int quantity) {
        if (quantity <= 0) throw new RuntimeException("Quantity must be greater than 0");
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        cart.setTotalPrice(cartItem.getProduct().getPrice() * quantity);
        cartRepository.save(cart);
        return convertToDTO(cartItem);
    }

    @Override
    public CartDTO clearCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.forEach(cartItem -> cartItemRepository.delete(cartItem));
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
        return convertToDTO(cart);
    }

    @Override
    public CartDTO convertToDTO(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        List<CartItemDTO> cartItemDTOs = cartItems.stream()
                .map(cartItem -> new CartItemDTO(cartItem.getQuantity(), cartItem.getProduct().getId()))
                .collect(Collectors.toList());
        return new CartDTO(cart.getTotalPrice(), cartItemDTOs);
    }

    @Override
    public CartItemDTO convertToDTO(CartItem cartItem) {
        return new CartItemDTO(cartItem.getQuantity(), cartItem.getProduct().getId());
    }

    @Override
    public Cart convertToEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setTotalPrice(cartDTO.totalPrice());
        List<CartItem> cartItems = cartDTO.cartItems().stream()
                .map(cartItemDTO -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setQuantity(cartItemDTO.quantity());
                    cartItem.setProduct(productRepository.findById(cartItemDTO.productId())
                            .orElseThrow(() -> new RuntimeException("Product not found")));
                    return cartItem;
                })
                .collect(Collectors.toList());
        cart.setCartItems(cartItems);
        return cart;
    }


}
