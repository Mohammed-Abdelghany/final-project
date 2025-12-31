package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto implements Serializable {

    private Long id;
    private LocalDateTime creationTimestamp;
    private Double totalPrice;
    private Long userId;
    private List<OrderItemDto> items;
}
