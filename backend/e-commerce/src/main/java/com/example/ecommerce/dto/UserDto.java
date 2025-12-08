package com.example.ecommerce.dto;

import com.example.ecommerce.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    @NotBlank(message = "name.is.required")
    private String name;
    @NotBlank(message = "name.is.required")
    private String username;
    @NotBlank(message = "password.is.required")
    private String password;
    private String spec;
    private String logoPath;
    private String faceLink;
    private String tweLink;
    private String instLink;
    private List<Role> roles;
    @JsonIgnore
    private List<ContactMessageDto> contactMessages;
}
