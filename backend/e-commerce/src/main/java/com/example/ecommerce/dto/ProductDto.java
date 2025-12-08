package com.example.ecommerce.dto;

import com.example.ecommerce.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotBlank(message = "Name is Required")
    private String name;
    private String imagePath;
    @NotBlank(message = "Description is Required")
    private String description;

    private BigDecimal price;
    private CategoryDto categoryDto;
//    private List<MediaDto> images= new ArrayList<>(); ;
}
