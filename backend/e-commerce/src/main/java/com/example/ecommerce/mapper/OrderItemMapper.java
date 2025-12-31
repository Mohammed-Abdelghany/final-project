package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")

    OrderItemDto toOrderItemDto(OrderItem orderItem);
    OrderItem toOrderItem(OrderItemDto orderItemDto);

    List<OrderItemDto> toOrderItemDto(List<OrderItem> orderItemList);
    List<OrderItem> toOrderItem(List<OrderItemDto> orderItemDtoList);
}
