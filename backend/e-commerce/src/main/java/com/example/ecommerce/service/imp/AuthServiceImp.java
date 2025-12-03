package com.example.ecommerce.service.imp;

import com.example.ecommerce.config.JwtHandler;
import com.example.ecommerce.controller.vm.LoginReq;
import com.example.ecommerce.controller.vm.LoginRes;
import com.example.ecommerce.controller.vm.RegisterReq;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.RoleRepo;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JwtHandler jwtHandler;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Autowired
    public AuthServiceImp(UserRepo userRepo, UserMapper userMapper, JwtHandler jwtHandler, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtHandler = jwtHandler;
        this.userMapper = userMapper;
        this.roleRepo = roleRepo;
    }
    @Override
    public LoginRes login(LoginReq loginReq) {
        User user = userRepo.findByUsername(loginReq.getUsername());
        if (user == null) {
            throw new RuntimeException("userName.or.password.invalid");
        }
        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new RuntimeException("userName.or.password.invalid");
        }
        String token = jwtHandler.generateToken(user);
        return new LoginRes(userMapper.userToUserDto(user), token);
    }


    @Override
    public UserDto register(RegisterReq userReq) {

        if (userRepo.findByUsername(userReq.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Create new User
        User user = new User();
        user.setName(userReq.getName());

        user.setUsername(userReq.getUsername());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));

        Role defaultRole = roleRepo.findByCode("USER");
        if (defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setCode("USER");
            defaultRole = roleRepo.save(defaultRole);
        }

        // 4. Assign role
        user.setRoles(new ArrayList<>());
        user.getRoles().add(defaultRole);

        // 5. Save user
        User savedUser = userRepo.save(user);

        // 6. Return DTO
        return userMapper.userToUserDto(savedUser);
    }


}
