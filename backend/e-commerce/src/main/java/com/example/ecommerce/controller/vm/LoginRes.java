package com.example.ecommerce.controller.vm;

import com.example.ecommerce.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRes {
    @NotBlank
    private UserDto user;
    @NotBlank(message = "login.token.required")
    private String token;
}
