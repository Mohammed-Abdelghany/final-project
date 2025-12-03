package com.example.ecommerce.controller.vm;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRes {
    @NotBlank(message = "login.message.required")
    private String message;
    @NotBlank(message = "login.token.required")
    private String token;
}
