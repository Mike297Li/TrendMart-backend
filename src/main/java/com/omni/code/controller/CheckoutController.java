package com.omni.code.controller;

import com.omni.code.request.CheckoutRequest;
import com.omni.code.response.ErrorResponse;
import com.omni.code.response.OrderResponse;
import com.omni.code.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody CheckoutRequest request) {
        try {
            // Perform the checkout operation and get the orderId
            Long orderId = checkoutService.checkout(request.getUserId(), request.getItems(), request.getAddress());

            // Create success response containing the orderId
            OrderResponse successResponse = new OrderResponse(
                    HttpStatus.OK.value(),
                    "Order placed successfully!",
                    System.currentTimeMillis(),
                    orderId // Include the orderId in the response
            );

            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Return specific error for insufficient stock
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Insufficient stock for product.", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Catch other exceptions
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while placing the order.", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
