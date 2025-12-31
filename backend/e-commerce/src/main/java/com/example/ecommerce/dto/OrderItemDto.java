package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto implements Serializable {

    private Long productId;
    private String productName;
    private Double  productPrice;
    private Integer quantity;
    private Double totalPrice;
}
