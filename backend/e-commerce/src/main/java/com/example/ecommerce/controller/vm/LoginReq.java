package com.example.ecommerce.controller.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
@NotBlank(message = "login.username.required")
    private String username;
@NotBlank(message = "login.password.required")
    private String password;

}
