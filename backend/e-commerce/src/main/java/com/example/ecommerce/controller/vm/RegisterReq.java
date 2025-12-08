package com.example.ecommerce.controller.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    @NotBlank(message = "register.username.required")
    @Size(min = 5, max = 15, message = "register.username.size")
    private String username;
    @Pattern(
            regexp = "^[A-Za-z]+(\\s[A-Za-z]+)+$",
            message = "register.name.invalid"
    )
    @NotBlank(message = "register.name.required")
    @Size(min = 7, max = 50, message = "register.name.size")
    private String name;
    @NotBlank(message = "register.password.required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"
            ,
            message = "register.password.weak"
    )
    private String password;
    private String spec;
    private String logoPath;
    private String faceLink;
    private String tweLink;
    private String instLink;

}
