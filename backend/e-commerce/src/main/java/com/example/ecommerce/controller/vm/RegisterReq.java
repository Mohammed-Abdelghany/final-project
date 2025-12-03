package com.example.ecommerce.controller.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class RegisterReq {
    @NotBlank(message = "register.username.required")
    private String username;
    @NotBlank(message = "register.password.required")
    private String password;
    @NotBlank(message = "register.spec.required")
    private String spec;
    private String logoPath;
    private String faceLink;
    private String tweLink;
    private String instLink;

}
