package com.example.ecommerce.controller;


import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping

    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();

    }
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@Valid @PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }
    @PutMapping("/update")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@Valid @PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/list")
    public List<CategoryDto> createCategories(@Valid @RequestBody List<CategoryDto> categoriesDto) {
        return categoryService.saveList(categoriesDto);

    }
    @PutMapping("/list/update")
    public List<CategoryDto> updateCategories(@Valid @RequestBody List<CategoryDto> categoriesDto) {
        return categoryService.updateList(categoriesDto);
    }

    @DeleteMapping("/list")
    public ResponseEntity<Void> deleteCategories(@Valid @RequestBody List<Long> ids) {
        return categoryService.deleteCategoriesByIds(ids);
    }



}
