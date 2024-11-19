package com.omni.code.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Review {

    private Integer reviewId;
    private Integer productId;
    private String userId;
    private Integer rating;  // Rating out of 5
    private String reviewText;
    private Timestamp createdAt;
    private String userName;
}
