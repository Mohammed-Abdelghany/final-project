package com.example.ecommerce.service.imp;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.helper.Pagination;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.repo.OrderRepo;
import com.example.ecommerce.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements OrderService {
    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;

    public OrderServiceImp(OrderRepo orderRepo, OrderMapper orderMapper) {
        this.orderRepo = orderRepo;
        this.orderMapper = orderMapper;
    }
    @Override
    public Page<OrderDto> getOrders(int size, int page) {
        return orderRepo.findAll(Pagination.getPageRequest(page, size))
                .map(orderMapper::toOrderDto);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        return null;
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
