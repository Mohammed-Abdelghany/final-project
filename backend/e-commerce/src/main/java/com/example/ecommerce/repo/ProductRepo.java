package com.example.ecommerce.repo;

import com.example.ecommerce.model.Product;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCase(@NotBlank(message = "Name is Required") String name);

    boolean existsByNameIgnoreCaseAndIdNot(@NotBlank(message = "Name is Required") String name, Long id);

    List<Product> findByCategoryId(Long id);

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword, String keyword1);
}
