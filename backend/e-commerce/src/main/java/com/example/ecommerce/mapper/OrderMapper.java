package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "items")
    OrderDto toOrderDto(Order order);
    Order toOrder(OrderDto orderDto);
}
