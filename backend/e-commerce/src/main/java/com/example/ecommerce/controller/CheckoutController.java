package com.example.ecommerce.controller;

import com.example.ecommerce.controller.vm.CheckoutRequestDto;
import com.example.ecommerce.controller.vm.CheckoutResponseDto;
import com.example.ecommerce.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")

public class CheckoutController {
    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @PostMapping
    public CheckoutResponseDto checkout(@RequestBody @Valid CheckoutRequestDto items) {
        return checkoutService.checkout(items);
    }

}
