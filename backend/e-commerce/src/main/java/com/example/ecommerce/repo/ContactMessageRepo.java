package com.example.ecommerce.repo;

import com.example.ecommerce.model.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepo extends JpaRepository<ContactMessage, Long> {
    @Query("SELECT cm FROM contact_messages cm JOIN FETCH cm.user")
    // Fetch contact messages along with associated user details
    //different from join by fetching all data in one query and preventing n+1 problem
    Page<ContactMessage> findAllWithUser(Pageable pageable);

}
