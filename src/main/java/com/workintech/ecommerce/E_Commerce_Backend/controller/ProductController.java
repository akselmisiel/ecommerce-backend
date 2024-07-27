package com.workintech.ecommerce.E_Commerce_Backend.controller;

import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductResponseDTO;
import com.workintech.ecommerce.E_Commerce_Backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductResponseDTO productResponseDTO) {
        ProductDTO createdProduct = productService.createProduct(productResponseDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductResponseDTO productResponseDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(productId, productResponseDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        ProductResponseDTO product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<List<ProductDTO>> getProductByNameLike(@PathVariable String productName) {
        List<ProductDTO> products = productService.getProductByNameLike(productName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable Long productId) {
        ProductResponseDTO deletedProduct = productService.deleteProduct(productId);
        return ResponseEntity.ok(deletedProduct);
    }

}
