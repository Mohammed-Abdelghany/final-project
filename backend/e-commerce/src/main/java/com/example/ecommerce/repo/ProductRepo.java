package com.example.ecommerce.repo;

import com.example.ecommerce.model.Product;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCase(@NotBlank(message = "Name is Required") String name);

    boolean existsByNameIgnoreCaseAndIdNot(@NotBlank(message = "Name is Required") String name, Long id);
    @Query("SELECT p FROM products p WHERE p.category.id = ?1")
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword, String keyword1,Pageable pageable);
}
