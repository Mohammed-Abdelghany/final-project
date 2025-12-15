package com.example.ecommerce.controller;

import com.example.ecommerce.controller.vm.PageResponse;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/{page}/{size}")
    public PageResponse<ProductDto> getProducts(@PathVariable int page, @PathVariable int size) {
        Page<ProductDto> pageProducts = productService.getProducts(page, size); // Page من Spring Data
        return new PageResponse<>(
                pageProducts.getContent(),
                pageProducts.getNumber(),
                pageProducts.getTotalPages(),
                pageProducts.getTotalElements(),
                pageProducts.getSize(),
                pageProducts.isFirst(),
                pageProducts.isLast()
        );
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
    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/list")
    public List<ProductDto> updateProducts(@Valid @RequestBody List<ProductDto> productsDto) {
        return productService.updateProducts(productsDto);
    }

//    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping
    public ProductDto createProduct(@RequestPart("product") ProductDto productDto){
        return productService.createProduct(productDto);
    }
    @GetMapping("/category/{categoryId}")
    public PageResponse<ProductDto> getProductsByCategoryId(@Valid @PathVariable Long categoryId,
                                                            @RequestParam int page,
                                                            @RequestParam int size) {
        Page<ProductDto> pageProducts = productService.getProductsByCategory(categoryId, page,size);
            return new PageResponse<>(
                    pageProducts.getContent(),
                    pageProducts.getNumber(),
                    pageProducts.getTotalPages(),
                    pageProducts.getTotalElements(),
                    pageProducts.getSize(),
                    pageProducts.isFirst(),
                    pageProducts.isLast()
            );

    }
    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@Valid @PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/list")
    public ResponseEntity<Void> deleteProducts(@Valid @RequestBody List<Long> ids){
      return   productService.deleteListByIds(ids);
    }

    @GetMapping("/by-name-or-description/{text}")
    public PageResponse<ProductDto> getProductsByNameOrDescription(@Valid @PathVariable String text,
    @RequestParam int page, @RequestParam int size){
        Page<ProductDto> productPage  = productService.getProductByNameOrDescription(text,page,size);
return new PageResponse<>(
        productPage.getContent(),
        productPage.getNumber(),
        productPage.getTotalPages(),
        productPage.getTotalElements(),
        productPage.getSize(),
        productPage.isFirst(),
        productPage.isLast()
);
    }
    @PreAuthorize("hasRole('ADMIN')")
@PutMapping("/update")
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
    }
}
