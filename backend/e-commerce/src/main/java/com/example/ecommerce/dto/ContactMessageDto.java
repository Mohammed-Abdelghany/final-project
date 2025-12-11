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

    private Long id;
 @NotBlank(message = "subject.is.required")
    private String subject;
    @NotBlank(message = "message.is.required")

    private String message;
    private  UserDto userDto;
}
