package com.omni.code.controller;

import com.omni.code.request.CheckoutRequest;
import com.omni.code.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody CheckoutRequest request) {
        try {
            checkoutService.checkout(request.getUserId(), request.getItems(),request.getAddress()); // Assuming checkout returns the created order
            return ResponseEntity.ok("Order placed successfully!"); // Return the created order
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Return null on error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while placing the order."); // Return null on error
        }
    }
}
