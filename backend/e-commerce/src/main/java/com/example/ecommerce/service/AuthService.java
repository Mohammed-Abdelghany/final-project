package com.example.ecommerce.service;

import com.example.ecommerce.controller.vm.LoginReq;
import com.example.ecommerce.controller.vm.LoginRes;
import com.example.ecommerce.controller.vm.RegisterReq;
import com.example.ecommerce.dto.UserDto;

public interface AuthService {
    LoginRes login(LoginReq loginReq);
    UserDto register(RegisterReq user);

}
