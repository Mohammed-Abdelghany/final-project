package com.example.ecommerce.service;

import com.example.ecommerce.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    CategoryDto save(CategoryDto categoryDto);
    List<CategoryDto> saveList(List<CategoryDto> categoriesDto);
    CategoryDto updateCategory(CategoryDto categoryDto);
    List<CategoryDto> updateList(List<CategoryDto> categoriesDto);
    void deleteById(Long id);
     ResponseEntity<Void> deleteCategoriesByIds(List<Long> ids);
}
