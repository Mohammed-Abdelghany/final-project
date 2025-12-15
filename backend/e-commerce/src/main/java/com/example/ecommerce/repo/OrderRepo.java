package com.example.ecommerce.repo;

import com.example.ecommerce.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM orders o JOIN FETCH o.user WHERE o.user.id = :userId")
    Page<Order> findByUserId(Long userId, Pageable pageable);
}
