package com.example.ecommerce.service.imp;
import com.example.ecommerce.controller.vm.CheckoutRequestDto;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.helper.Pagination;
import com.example.ecommerce.helper.UserAuthenticated;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.OrderItemRepo;
import com.example.ecommerce.repo.OrderRepo;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImp implements OrderService {
    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final ProductRepo productRepo;
    private final UserMapper userMapper;

    public OrderServiceImp(OrderRepo orderRepo, OrderMapper orderMapper, ProductRepo productRepo, UserMapper userMapper) {
        this.orderRepo = orderRepo;
        this.orderMapper = orderMapper;
        this.productRepo = productRepo;
        this.userMapper = userMapper;
    }
    @Override
    public Page<OrderDto> getOrders(int size, int page) {
        return orderRepo.findAll(Pagination.getPageRequest(page, size))
                .map(orderMapper::toOrderDto);
    }
    @Override
        public OrderDto createOrder(CheckoutRequestDto orderDto) {
            Order order = new Order();
            User user = userMapper.userDtoToUser(UserAuthenticated.getUserDtoAuthenticated());
            order.setCreationTimestamp(LocalDateTime.now());
            order.setUser(user);

            List<OrderItem> orderItems = new ArrayList<>();
            double totalPrice = 0;

            for (OrderItemDto itemDto : orderDto.getItems()) {
                OrderItem item = new OrderItem();
                Product product = productRepo.findById(itemDto.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDto.getProductId()));
                item.setOrder(order);
                item.setProduct(product);
                item.setProductName(product.getName());
                item.setProductPrice(itemDto.getProductPrice());
                item.setQuantity(itemDto.getQuantity());
                item.setTotalPrice(itemDto.getProductPrice() * itemDto.getQuantity());
                totalPrice += item.getTotalPrice();
                orderItems.add(item);
            }
            order.setOrderItems(orderItems);
            order.setTotalPrice(totalPrice);
            Order savedOrder = orderRepo.save(order);
            return orderMapper.toOrderDto(savedOrder);
        }


    @Override
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public Page<OrderDto> getOrders(int size, int page, Long userId) {
        Page<Order> orders = orderRepo.findByUserId(
                userId,
                Pagination.getPageRequest(page, size)
        );
        return orders.map(orderMapper::toOrderDto);
    }


}
