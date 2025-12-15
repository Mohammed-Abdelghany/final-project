package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> getOrders(int size, int page);
    Page<OrderDto> getOrders(int size, int page, Long userId);
     OrderDto createOrder(OrderDto orderDto);

}
