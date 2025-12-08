package com.example.ecommerce.controller;

import com.example.ecommerce.controller.vm.PageResponse;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    @Autowired
   public UserController(UserService userService) {
        this.userService = userService;
   }

   @GetMapping("/users/chefs")
   public PageResponse<UserDto> getAllChefs(@RequestParam int page, @RequestParam int size) {
       Page <UserDto> pageUsers = userService.getAllChefs(page, size);
         return new PageResponse<>(
                pageUsers.getContent(),
                pageUsers.getNumber(),
                pageUsers.getTotalPages(),
                pageUsers.getTotalElements(),
                pageUsers.getSize(),
                pageUsers.isFirst(),
                pageUsers.isLast()
         );

   }

   // Default page and size
   }
       // Implementation will go here


