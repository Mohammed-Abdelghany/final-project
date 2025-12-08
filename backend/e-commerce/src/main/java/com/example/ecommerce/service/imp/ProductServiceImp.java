package com.example.ecommerce.service.imp;

import com.example.ecommerce.controller.vm.PageResponse;
import com.example.ecommerce.dto.CategoryDto;
//import com.example.ecommerce.dto.MediaDto;
import com.example.ecommerce.dto.ProductDto;
//import com.example.ecommerce.helper.FileStorageHelper;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.helper.Pagination;
import com.example.ecommerce.mapper.CategoryMapper;
import com.example.ecommerce.mapper.ProductMapper;
//import com.example.ecommerce.model.Media;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryServiceImp categoryServiceImp;
    private final CategoryMapper categoryMapper;
    private final UserRepo userRepo;
//    private final FileStorageHelper fileStorageHelper;

    @Autowired
    public ProductServiceImp(ProductRepo productRepo, ProductMapper productMapper,
                             CategoryServiceImp categoryServiceImp, CategoryMapper categoryMapper, UserRepo userRepo){
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.categoryServiceImp = categoryServiceImp;
        this.categoryMapper = categoryMapper;
//        this.fileStorageHelper = fileStorageHelper;
        this.userRepo = userRepo;
    }

    public Page<ProductDto> getProducts(int page, int size) {
        Page<Product> products = productRepo.findAll(Pagination.getPageRequest(page, size));
        return products.map(productMapper::toProductDto);
    }

    public ProductDto getProduct(Long id) {
        return productMapper.toProductDto(
                productRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("product.notfound"))
        );
    }
@Transactional
    public ProductDto createProduct(ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new RuntimeException("product.new.hasId");
        }

        validateProduct(productDto);
        checkUnique(productDto, "create");
        CategoryDto categoryDto = categoryServiceImp.findById(productDto.getCategoryDto().getId());
        Product product = productMapper.toProduct(productDto);
        product.setCategory(categoryMapper.toCategory(categoryDto));
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

        // 1) Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated())
            throw new RuntimeException("user.notfound");

        if (!(auth.getPrincipal() instanceof UserDto userDto))
            throw new RuntimeException("user.notfound");
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Chef"))) {
            throw new RuntimeException("user.cannot.create.product");
        }

        User user = userRepo.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("user.notfound"));

        productsDto.forEach(dto -> {
            if (dto.getId() != null)
                throw new RuntimeException("product.bulk.noId");

            validateProduct(dto);
            checkUnique(dto, "create");
        });

        // 3) Map and save
        List<Product> newProducts = productsDto.stream()
                .map(dto -> {
                    CategoryDto categoryDto = categoryServiceImp.findById(dto.getCategoryDto().getId());
                    Product p = productMapper.toProduct(dto);
                    p.setCategory(categoryMapper.toCategory(categoryDto));
                    p.setUser(user);
                    return p;
                })
                .toList();

        List<Product> saved = productRepo.saveAll(newProducts);
        return productMapper.toProductDtoList(saved);
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

    public Page<ProductDto> getProductsByCategory(Long categoryId,int page , int size) {
        CategoryDto categoryDto = categoryServiceImp.findById(categoryId);
        Page<Product> products = productRepo.findByCategoryId(categoryDto.getId(),Pagination.getPageRequest(page, size));
        return products.map(productMapper::toProductDto);
    }
    public Page<ProductDto>getProductByNameOrDescription(String keyword,int page , int size) {
        Page<Product> products=productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,keyword,Pagination.getPageRequest(page, size));
        if (products.isEmpty()){
            throw new RuntimeException("product.notfound");
        }
        return products.map(productMapper::toProductDto);
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









