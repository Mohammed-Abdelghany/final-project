package com.example.ecommerce.controller;

import com.example.ecommerce.controller.vm.LoginReq;
import com.example.ecommerce.controller.vm.LoginRes;
import com.example.ecommerce.controller.vm.RegisterReq;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public LoginRes login(@Valid @RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

    @PostMapping("/sign-up")
    public UserDto register(@Valid @RequestBody RegisterReq registerReq) {
        return authService.register(registerReq);
    }

}
