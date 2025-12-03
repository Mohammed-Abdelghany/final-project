package com.example.ecommerce.controller.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    @NotBlank(message = "register.username.required")
    private String username;
    @NotBlank(message = "register.name.required")
    private String name;
    @NotBlank(message = "register.password.required")
    private String password;
    private String spec;
    private String logoPath;
    private String faceLink;
    private String tweLink;
    private String instLink;

}
