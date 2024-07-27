package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductResponseDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Product;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductResponseDTO productResponseDTO);
    ProductResponseDTO updateProduct(Long productId, ProductResponseDTO productResponseDTO);
    ProductResponseDTO getProductById(Long productId);
    List<ProductDTO> getProductByNameLike(String productName);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO deleteProduct(Long productId);
    public Product convertToEntity(ProductResponseDTO productResponseDTO);
}
