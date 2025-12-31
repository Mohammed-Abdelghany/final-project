package com.example.ecommerce.service.imp;

import com.example.ecommerce.controller.vm.CheckoutRequestDto;
import com.example.ecommerce.controller.vm.CheckoutResponseDto;
import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.service.CheckoutService;
import com.example.ecommerce.service.OrderService;
import lombok.Locked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CheckoutServiceImp implements CheckoutService {
    private final ProductRepo productRepo;
    private final OrderService orderService;

    public CheckoutServiceImp(ProductRepo productRepo, OrderService orderService) {
        this.orderService = orderService;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public CheckoutResponseDto checkout(CheckoutRequestDto request) {



        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Checkout request is empty or invalid");
        }

        double totalAmount = 0;

        for (OrderItemDto item : request.getItems()) {

            if (item.getProductId() == null) {
                throw new IllegalArgumentException("Product ID is missing");
            }

            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (!product.getPrice().equals(item.getProductPrice())) {
                throw new RuntimeException("Price changed for product: " + product.getName());
            }

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for: " + product.getName());
            }

            double itemTotal = item.getProductPrice() * item.getQuantity();
            item.setTotalPrice(itemTotal);

            totalAmount += itemTotal;

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
        }

        try {
            orderService.createOrder(request);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create order: " + e.getMessage());
        }

        return new CheckoutResponseDto(
                request.getItems(),
                totalAmount,
                "Checkout successful"
        );
    }


}

