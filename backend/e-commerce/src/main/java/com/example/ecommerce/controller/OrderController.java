package com.example.ecommerce.controller;

import com.example.ecommerce.controller.vm.PageResponse;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<OrderDto> getOrders(@RequestParam int page, @RequestParam int size) {
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
    public PageResponse<OrderDto> getUserOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam Long userId) {
        Page<OrderDto> orders = orderService.getOrders(size, page,userId);
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


}
