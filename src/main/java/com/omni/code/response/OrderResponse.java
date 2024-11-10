package com.omni.code.response;

import lombok.Data;

@Data
public class OrderResponse {
    private int status;
    private String message;
    private long timestamp;
    private Long orderId;

    public OrderResponse(int status, String message, long timestamp, Long orderId) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.orderId = orderId;
    }
}
