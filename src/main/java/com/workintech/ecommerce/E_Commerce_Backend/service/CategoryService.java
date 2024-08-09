package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.CategoryDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.ProductDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO findCategoryById(Long categoryId);
    List<CategoryDTO> findAllCategories();
    List<ProductDTO> findProductsByCategoryId(Long categoryId);
    List<ProductDTO> findProductsByCategoryName(String categoryName);
}

