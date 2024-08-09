package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.CategoryDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductDTO;
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
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setCategoryName(categoryDTO.categoryName());
        if (categoryDTO.parentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.parentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            existingCategory.setParentCategory(parentCategory);
        }

        existingCategory.setSubCategories(categoryDTO.subCategoryIds().stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found")))
                .collect(Collectors.toList()));
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDTO(updatedCategory);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
        return convertToDTO(category);
    }

    @Override
    public CategoryDTO findCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToDTO(category);
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getProducts().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getProducts().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(Category category) {
        Long parentCategoryId = category.getParentCategory() != null ? category.getParentCategory().getId() : null;
        List<Long> subCategoryIds = category.getSubCategories() != null ?
                category.getSubCategories().stream().map(Category::getId).collect(Collectors.toList()) : null;
        return new CategoryDTO(
                category.getId(),
                category.getCategoryName(),
                parentCategoryId,
                subCategoryIds
        );
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO(product.getId(), product.getProductName(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getProductImage(), product.getCategory().getId());
        return productDTO;
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.categoryName());
        if (categoryDTO.parentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.parentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }

        category.setSubCategories(categoryDTO.subCategoryIds().stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found")))
                .collect(Collectors.toList()));
        return category;
    }
}
