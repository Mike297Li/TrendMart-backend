package com.omni.code.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, String description,String paymentMethodId, String email ) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount.longValue())
                .setCurrency(currency)
                .setReceiptEmail(email)
                .addPaymentMethodType("card")
                .setPaymentMethod(paymentMethodId)
                .setDescription(description)
                .build();
        return PaymentIntent.create(params);
    }
}

