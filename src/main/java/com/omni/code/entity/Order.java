package com.omni.code.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long orderId;
    private String userId; // ID of the user placing the order
    private BigDecimal totalAmount; // Total amount for the order
    private String orderStatus; // e.g., "Pending", "Completed"
    private String paymentStatus; // e.g., "Pending", "Completed"
    private LocalDateTime createdAt; // Timestamp of order creation
    private String address; // ID of the user placing the order
}
