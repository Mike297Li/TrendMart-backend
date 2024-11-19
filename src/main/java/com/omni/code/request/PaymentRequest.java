package com.omni.code.request;



import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotNull(message = "Order ID is required.")
    private String orderId;
    @NotNull(message = "Amount is required.")
    private BigDecimal amount;
    @NotNull(message = "Payment method is required.")
    private String paymentMethodId;
    @NotNull(message = "email is required.")
    private String email;

}
