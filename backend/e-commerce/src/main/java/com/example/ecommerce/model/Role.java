package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Getter
@Setter
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
