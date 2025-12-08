package com.example.ecommerce.repo;

import com.example.ecommerce.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepo extends JpaRepository<ContactMessage, Long> {
}
