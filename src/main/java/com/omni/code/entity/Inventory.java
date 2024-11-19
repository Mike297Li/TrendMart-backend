package com.omni.code.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Inventory {
    private Long productId;
    private int stockQuantity; // Available stock quantity
    private LocalDateTime updatedAt;

}
