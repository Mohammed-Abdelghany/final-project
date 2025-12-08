package com.example.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageDto {

    private long id;
    @NotBlank(message = "name.is.required")
    private String name;
        @Email(message = "email.is.invalid")
    private String email;
        @NotBlank(message = "subject.is.required")
    private String subject;
    private String message;
    private String createdAt;
    private  UserDto userDto;
}
