package com.example.ecommerce.service.imp;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.dto.MediaDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.helper.FileStorageHelper;
import com.example.ecommerce.helper.MessageResponse;
import com.example.ecommerce.mapper.CategoryMapper;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.model.Media;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryServiceImp categoryServiceImp;
    private final CategoryMapper categoryMapper;
    private final FileStorageHelper fileStorageHelper;

    @Autowired
    public ProductServiceImp(ProductRepo productRepo, ProductMapper productMapper,
                             CategoryServiceImp categoryServiceImp, CategoryMapper categoryMapper,FileStorageHelper fileStorageHelper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.categoryServiceImp = categoryServiceImp;
        this.categoryMapper = categoryMapper;
        this.fileStorageHelper = fileStorageHelper;
    }

    public List<ProductDto> getProducts() {
        return productMapper.toProductDtoList(productRepo.findAll());
    }

    public ProductDto getProduct(Long id) {
        return productMapper.toProductDto(
                productRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("product.notfound"))
        );
    }
@Transactional
    public ProductDto createProduct(ProductDto productDto, List<MultipartFile> imageFiles) {
        if (productDto.getId() != null) {
            throw new RuntimeException("product.new.hasId");
        }
        validateProduct(productDto);
        checkUnique(productDto, "create");
        CategoryDto categoryDto = categoryServiceImp.findById(productDto.getCategoryDto().getId());
        Product product = productMapper.toProduct(productDto);
        product.setCategory(categoryMapper.toCategory(categoryDto));
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile file : imageFiles) {
                try {
                    String fileName = fileStorageHelper.saveFile(file);

                    Media media = new Media();
                    media.setFileName(fileName);
                    media.setContentType(file.getContentType()); // مهم جداً

                    if (product.getImages() == null) {
                        product.setImages(new ArrayList<>());
                    }
                    product.getImages().add(media);

                } catch (IOException e) {
                    throw new RuntimeException("Error saving file", e);
                }
            }
        }

        // حفظ المنتج
        Product saved = productRepo.save(product);

        return productMapper.toProductDto(saved);
    }


    public ProductDto updateProduct(ProductDto productDto) {

        Product existing = productRepo.findById(productDto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        validateProduct(productDto);
        checkUnique(productDto, "update");

        CategoryDto categoryDto = categoryServiceImp.findById(productDto.getCategoryDto().getId());

        productMapper.updateProductFromDto(productDto, existing);
        existing.setCategory(categoryMapper.toCategory(categoryDto));
        Product updated = productRepo.save(existing);
        return productMapper.toProductDto(updated);
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
    }

    @Transactional
    public List<ProductDto> saveProducts(List<ProductDto> productsDto) {

        productsDto.forEach(dto -> {
            if (dto.getId() != null)
                throw new RuntimeException("product.bulk.noId");
            validateProduct(dto);
            checkUnique(dto, "create");
        });

        List<Product> newProducts = productsDto.stream()
                .map(dto -> {
                    CategoryDto categoryDto = categoryServiceImp.findById(dto.getCategoryDto().getId());
                    Product p = productMapper.toProduct(dto);
                    p.setCategory(categoryMapper.toCategory(categoryDto));
                    return p;
                })
                .toList();
        List<Product> saved = productRepo.saveAll(newProducts);
        return  productMapper.toProductDtoList(saved) ;
    }
    @Transactional
    public List<ProductDto> updateProducts(List<ProductDto> productsDto) {

        productsDto.forEach(dto -> {
            validateProduct(dto);
            checkUnique(dto, "update");
        });

        List<Product> updated = productsDto.stream()
                .map(dto -> {
                    Product existing = productRepo.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    CategoryDto categoryDto = categoryServiceImp.findById(dto.getCategoryDto().getId());
                    productMapper.updateProductFromDto(dto, existing);
                    existing.setCategory(categoryMapper.toCategory(categoryDto));
                    return existing;
                })
                .toList();
        return productMapper.toProductDtoList(productRepo.saveAll(updated));
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        CategoryDto categoryDto = categoryServiceImp.findById(categoryId);
        List<Product> products = productRepo.findByCategoryId(categoryDto.getId());
        return productMapper.toProductDtoList(products);
    }
    public List<ProductDto>getProductByNameOrDescription(String keyword){
        List<Product> products=productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,keyword);
        if (products.isEmpty()){
            throw new RuntimeException("product.notfound");
        }
        return productMapper.toProductDtoList(products);
    }


    public ResponseEntity<Void> deleteListByIds(List<Long> ids) {
        List<Product> productsToDelete = productRepo.findAllById(ids);
        if (productsToDelete.size() != ids.size()) {
            throw new RuntimeException("product.list.notfound");
        }
        productRepo.deleteAll(productsToDelete);
        return ResponseEntity.noContent().build();
    }



    private void validateProduct(ProductDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("product.name.empty");
        }
        if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("product.price.invalid");
        }
    }

    private void checkUnique(ProductDto dto, String operation) {

        switch (operation) {
            case "create" -> {
                if (productRepo.existsByNameIgnoreCase(dto.getName())) {
                    throw new RuntimeException("product.name.unique");
                }
            }
            case "update" -> {
                if (productRepo.existsByNameIgnoreCaseAndIdNot(dto.getName(), dto.getId())) {
                    throw new RuntimeException("Product with name " + dto.getName() + " already exists");
                }
            }
            default -> throw new RuntimeException("product.operation.invalid");
        }
    }


}









