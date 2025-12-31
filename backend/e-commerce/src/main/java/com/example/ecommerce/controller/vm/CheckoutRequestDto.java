package com.example.ecommerce.controller.vm;

import com.example.ecommerce.dto.OrderItemDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequestDto {
        @NotEmpty(message = "order.items.cannot.be.empty")
        private List<OrderItemDto> items;

}
