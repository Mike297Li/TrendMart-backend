package com.omni.code.request;



import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private String orderId;
    private BigDecimal amount;
    private String paymentMethodId;
    private String email;

}
