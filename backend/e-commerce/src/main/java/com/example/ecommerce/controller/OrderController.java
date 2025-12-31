package com.example.ecommerce.controller;

import com.example.ecommerce.controller.vm.CheckoutRequestDto;
import com.example.ecommerce.controller.vm.PageResponse;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<OrderDto> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<OrderDto> orders = orderService.getOrders(size, page);
        return new PageResponse<>(
                orders.getContent(),
                orders.getNumber(),
                orders.getTotalPages(),
                orders.getTotalElements(),
                orders.getSize(),
                orders.isFirst(),
                orders.isLast()
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public PageResponse<OrderDto> getUserOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam Long userId) {

        Page<OrderDto> orders = orderService.getOrders(size, page, userId);
        return new PageResponse<>(
                orders.getContent(),
                orders.getNumber(),
                orders.getTotalPages(),
                orders.getTotalElements(),
                orders.getSize(),
                orders.isFirst(),
                orders.isLast()
        );
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody @Valid CheckoutRequestDto orderDto) {
        return orderService.createOrder(orderDto);
    }
}
