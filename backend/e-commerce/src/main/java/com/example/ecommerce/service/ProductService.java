package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();
    ProductDto getProduct(Long id);
    List<ProductDto> saveProducts(List<ProductDto> productsDto);
    List<ProductDto> updateProducts(List<ProductDto> productsDto);
    ProductDto createProduct(ProductDto productDto, List<MultipartFile> files);
    List<ProductDto> getProductsByCategory(Long categoryId);
    void deleteProduct(Long id);
    ResponseEntity<Void> deleteListByIds(List<Long> ids);
    List<ProductDto>getProductByNameOrDescription(String keyword);
}
