package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity(name = "Roles")
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String code;
   @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
