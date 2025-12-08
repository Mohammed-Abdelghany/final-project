package com.example.ecommerce.repo;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM users u JOIN u.roles r WHERE r.code = 'CHEF'")
    Page<User> findAllChef(Pageable pageable);
}
