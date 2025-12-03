package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Roles")
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String code;
   @ManyToMany(mappedBy = "roles")
   @JsonIgnore // ده يمنع recursion
   private List<User> users;

}
