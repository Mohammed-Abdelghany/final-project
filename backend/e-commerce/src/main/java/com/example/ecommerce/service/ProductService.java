package com.example.ecommerce.service;

import com.example.ecommerce.controller.vm.PageResponse;
import com.example.ecommerce.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<ProductDto> getProducts(int size, int page);
    ProductDto getProduct(Long id);
    ProductDto updateProduct(ProductDto productDto);

    List<ProductDto> saveProducts(List<ProductDto> productsDto);
    List<ProductDto> updateProducts(List<ProductDto> productsDto);
    ProductDto createProduct(ProductDto productDto);
    Page<ProductDto> getProductsByCategory(Long categoryId,int page , int size);
    void deleteProduct(Long id);
    ResponseEntity<Void> deleteListByIds(List<Long> ids);
    Page<ProductDto>getProductByNameOrDescription(String keyword,int page , int size);
}
