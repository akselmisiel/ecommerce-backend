package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductResponseDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Category;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Product;
import com.workintech.ecommerce.E_Commerce_Backend.repository.CategoryRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO createProduct(ProductResponseDTO productResponseDTO) {
        Product product = convertToEntity(productResponseDTO);
        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct.getId(), savedProduct.getProductName(), savedProduct.getPrice(), savedProduct.getQuantity(),
                savedProduct.getDescription(), savedProduct.getProductImage(), savedProduct.getCategory().getId());
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductResponseDTO productResponseDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(productResponseDTO.productName());
        product.setPrice(productResponseDTO.price());
        product.setQuantity(productResponseDTO.quantity());
        product.setDescription(productResponseDTO.description());
        product.setProductImage(productResponseDTO.productImage());
        product.setCategory(categoryRepository.findById(productResponseDTO.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));

        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> getProductByNameLike(String productName) {
        List<Product> products = productRepository.findByProductNameLike(productName);
        return products.stream()
                .map(product -> new ProductDTO(product.getId(), product.getProductName(), product.getPrice(), product.getQuantity(),
                        product.getDescription(), product.getProductImage(), product.getCategory().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
        return convertToDTO(product);
    }

    private ProductResponseDTO convertToDTO(Product product) {
        return new ProductResponseDTO(
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                product.getProductImage(),
                product.getCategory().getId()
        );
    }

    public Product convertToEntity(ProductResponseDTO productResponseDTO) {
        Category category = categoryRepository.findById(productResponseDTO.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product(productResponseDTO.productName(), productResponseDTO.price(), productResponseDTO.quantity(),
                productResponseDTO.description(), productResponseDTO.productImage(), category);

        return product;
    }
}
