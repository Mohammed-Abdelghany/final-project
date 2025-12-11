package com.example.ecommerce.helper;

import com.example.ecommerce.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserAuthenticated {

    public static UserDto getUserDtoAuthenticated() {
             return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(UserDto.class::isInstance)

                .map(UserDto.class::cast)
                .orElseThrow(() -> new RuntimeException("user.notfound"));
    }
}


