package com.omni.code.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private Long orderItemId;
    private Long orderId; // ID of the associated order
    private Long productId; // ID of the product
    private int quantity;
    private BigDecimal price; // Store price at the time of order
}
