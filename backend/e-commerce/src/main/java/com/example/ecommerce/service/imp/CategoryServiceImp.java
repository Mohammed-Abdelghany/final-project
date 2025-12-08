package com.example.ecommerce.service.imp;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.helper.Pagination;
import com.example.ecommerce.mapper.CategoryMapper;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.repo.CategoryRepo;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImp(CategoryRepo categoryRepo, CategoryMapper categoryMapper) {
        this.categoryRepo = categoryRepo;
        this.categoryMapper = categoryMapper;
    }

    public Page<CategoryDto> findAll(int page, int size) {
        Page<Category> categories = categoryRepo.findAll(Pagination.getPageRequest(page, size));

        return categories.map(categoryMapper::toCategoryDto);
    }

    public CategoryDto findById(Long id) {
        return categoryMapper.toCategoryDto(categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("category.notfound")));
    }

    public CategoryDto save(CategoryDto categoryDto) {
        validateCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepo.save(categoryMapper.toCategory(categoryDto)));
    }

    public void deleteById(Long id) {
        if (!categoryRepo.existsById(id)) {
            throw new RuntimeException("category.notfound.id" + id);
        }
        categoryRepo.deleteById(id);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() == null || !categoryRepo.existsById(categoryDto.getId())) {
            throw new RuntimeException("category.notfound");
        }
        CategoryDto existingCategory= findById(categoryDto.getId());
        existingCategory.setName(categoryDto.getName());
        existingCategory.setLogoPath(categoryDto.getLogoPath());
        Optional.ofNullable(categoryDto.getProductsDto()).filter(List::isEmpty).ifPresent(existingCategory::setProductsDto);
        return categoryMapper.toCategoryDto(categoryRepo.save(categoryMapper.toCategory(existingCategory)));
    }

    public List<CategoryDto> findAllActiveCategories() {
        List<CategoryDto> allCategories = categoryRepo.findAll().stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
        return allCategories.stream()
                .filter(categoryDto -> categoryDto.getFlag() != null && !categoryDto.getFlag())
                .toList();
    }


    public List<CategoryDto> saveList(List<CategoryDto> categoryDtoList) {
        return categoryDtoList.stream()
                .peek(this::save)
                .toList();

    }

    @Override
    public ResponseEntity<Void> deleteCategoriesByIds(List<Long> ids) {
        List<CategoryDto> categoriesToDelete = ids.stream()
                .map(this::findById)
                .toList();

        for (CategoryDto categoryDto : categoriesToDelete) {
            deleteById(categoryDto.getId());
        }
        return ResponseEntity.noContent().build();
    }

    public List<CategoryDto>updateList(List<CategoryDto> categoryDtoList){

        return categoryDtoList.stream()
                .map(this::updateCategory)
                .collect(Collectors.toList());
    }

    private void validateCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() != null)
            throw new RuntimeException("category.new.hasId");
        if (Objects.nonNull(categoryDto.getProductsDto()))
            throw new RuntimeException("category.new.hasProducts");
        if (categoryRepo.existsByName((categoryDto.getName()))){
            throw new RuntimeException("category.name.unique");
        }
        categoryDto.setFlag(false);
    }
//    private void isUniqueName(CategoryDto categoryDto) {
//        String name=categoryDto.getName();
//        if (categoryRepo.existsByName((name))){
//            throw new RuntimeException("Category name must be unique");
//        }
    }





