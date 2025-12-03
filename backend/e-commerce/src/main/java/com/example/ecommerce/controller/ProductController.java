package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }
    @GetMapping("/{id}")
    public ProductDto getProduct(@Valid @PathVariable Long id) {
        return productService.getProduct(id);
    }
    @PostMapping("/list")
    public List<ProductDto> saveProducts(@Valid @RequestBody List<ProductDto> productsDto

    ) {
        return productService.saveProducts(productsDto);
    }
    @PutMapping("/list")
    public List<ProductDto> updateProducts(@Valid @RequestBody List<ProductDto> productsDto) {
        return productService.updateProducts(productsDto);
    }

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(
            @RequestPart("product") String productJson, // هنبعت JSON string
            @RequestPart(name = "files", required = false) List<MultipartFile> imageFiles) throws IOException {

        // تحويل JSON string لـ ProductDto
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);

        return productService.createProduct(productDto, imageFiles);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDto> getProductsByCategoryId(@Valid @PathVariable Long categoryId){
        return productService.getProductsByCategory(categoryId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@Valid @PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/list")
    public ResponseEntity<Void> deleteProducts(@Valid @RequestBody List<Long> ids){
      return   productService.deleteListByIds(ids);
    }

    @GetMapping("/by-name-or-description/{text}")
    public List<ProductDto> getProductsByNameOrDescription(@Valid @PathVariable String text) {
        return productService.getProductByNameOrDescription(text);
    }

}
