package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {
     Category toCategory(CategoryDto categoryDto);
      CategoryDto toCategoryDto(Category category);

     List<Category> toCategoryList(List<CategoryDto> categoryDtoList);
     List<CategoryDto> toCategoryDtoList(List<Category> categoryList);
}
