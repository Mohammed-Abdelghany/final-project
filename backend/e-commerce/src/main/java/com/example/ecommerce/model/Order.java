package com.example.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private Long id;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    @Column( nullable = false)
    private double price;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
