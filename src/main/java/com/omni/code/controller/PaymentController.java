package com.omni.code.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omni.code.request.PaymentRequest;
import com.omni.code.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    @Autowired
    private  PaymentService paymentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestBody PaymentRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            // Convert amount to cents
            BigDecimal amountInCents = request.getAmount().multiply(new BigDecimal(100));
            log.info("=========="+objectMapper.writeValueAsString(request));
            // Create the PaymentIntent
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(amountInCents, "CAD", "Order #" + request.getOrderId(), request.getPaymentMethodId(),request.getEmail());

            // Check the PaymentIntent status and handle accordingly
            if ("requires_confirmation".equals(paymentIntent.getStatus())) {
                // Confirm the PaymentIntent if it requires confirmation
                PaymentIntent confirmedPaymentIntent = paymentIntent.confirm();

                if ("succeeded".equals(confirmedPaymentIntent.getStatus())) {
                    response.put("status", "success");
                    response.put("message", "Payment confirmed and successful");
                } else {
                    response.put("status", confirmedPaymentIntent.getStatus());
                    response.put("message", "Payment confirmation status: " + confirmedPaymentIntent.getStatus());
                }
            } else if ("succeeded".equals(paymentIntent.getStatus())) {
                response.put("status", "success");
                response.put("message", "Payment successful");
            } else if ("requires_action".equals(paymentIntent.getStatus())) {
                response.put("status", "requires_action");
                response.put("message", "Payment requires further action");
                response.put("clientSecret", paymentIntent.getClientSecret());
            } else {
                response.put("status", paymentIntent.getStatus());
                response.put("message", "Payment is pending or has another status: " + paymentIntent.getStatus());
                response.put("clientSecret", paymentIntent.getClientSecret());
            }

            return ResponseEntity.ok(response);
        } catch (StripeException | JsonProcessingException e) {
            response.put("status", "error");
            response.put("message", "Payment failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }


    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestBody Map<String, String> requestData) {
        String paymentIntentId = requestData.get("paymentIntentId");

        try {
            // Retrieve and confirm the PaymentIntent
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            PaymentIntent confirmedPaymentIntent = paymentIntent.confirm();

            if ("succeeded".equals(confirmedPaymentIntent.getStatus())) {
                // Payment is successfully completed
                return ResponseEntity.ok("Payment confirmed successfully.");
            } else {
                // Handle other statuses
                return ResponseEntity.ok("Payment status: " + confirmedPaymentIntent.getStatus());
            }
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error confirming payment: " + e.getMessage());
        }
    }

}

