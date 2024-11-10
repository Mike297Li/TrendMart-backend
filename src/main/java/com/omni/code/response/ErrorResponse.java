package com.omni.code.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    // Constructors, Getters, and Setters
    public ErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

}

