package com.omni.code.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String features;
    private BigDecimal averageRating;
    private LocalDateTime createdAt;
    private String pictureBase64;
    private int quantity;
}
