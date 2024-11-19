package com.omni.code.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Payment {

    private String paymentId;
    private Long orderId;
    private String paymentMethod;
    private Long paymentAmount;
    private Timestamp paymentDate;
    private String stauts;

}

