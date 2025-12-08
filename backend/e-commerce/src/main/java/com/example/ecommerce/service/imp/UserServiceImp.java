package com.example.ecommerce.service.imp;

import com.example.ecommerce.config.JwtHandler;
import com.example.ecommerce.controller.vm.LoginReq;
import com.example.ecommerce.controller.vm.LoginRes;
import com.example.ecommerce.controller.vm.RegisterReq;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.helper.Pagination;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.RoleRepo;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImp(UserRepo userRepo,UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }
    @Override
    public Page<UserDto> getAllChefs(int page, int size) {
        Page<User> users=userRepo.findAllChef(Pagination.getPageRequest(page,size));

      return users.map(userMapper::userToUserDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }



    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
