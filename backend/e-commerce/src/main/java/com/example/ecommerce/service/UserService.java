package com.example.ecommerce.service;


import com.example.ecommerce.controller.vm.LoginReq;
import com.example.ecommerce.controller.vm.LoginRes;
import com.example.ecommerce.controller.vm.RegisterReq;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

        List<UserDto> getAllUsers();
        UserDto getUserById(Long id);
          UserDto updateUser(UserDto  userDto);
        void deleteUser(Long id);


}
