package com.example.ecommerce.controller.vm;

import com.example.ecommerce.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseDto {

    private List<OrderItemDto> items;
    private Double totalAmount;
    private String status;


}
