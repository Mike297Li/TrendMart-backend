package com.omni.code.controller;

import com.omni.code.request.CheckoutRequest;
import com.omni.code.response.ErrorResponse;
import com.omni.code.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/place-order")
    public ResponseEntity<ErrorResponse> placeOrder(@Valid @RequestBody CheckoutRequest request) {
        try {
            checkoutService.checkout(request.getUserId(), request.getItems(), request.getAddress());
            // Return success response
            ErrorResponse successResponse = new ErrorResponse(HttpStatus.OK.value(), "Order placed successfully!", System.currentTimeMillis());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Return specific error for insufficient stock
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Insufficient stock for product.", System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Catch other exceptions
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while placing the order.", System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
