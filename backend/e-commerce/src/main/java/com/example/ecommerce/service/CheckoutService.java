package com.example.ecommerce.service;

import com.example.ecommerce.controller.vm.CheckoutRequestDto;
import com.example.ecommerce.controller.vm.CheckoutResponseDto;

public interface CheckoutService {
     CheckoutResponseDto checkout(CheckoutRequestDto request);
}
