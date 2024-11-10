package com.omni.code.controller;

import com.omni.code.entity.OrderItem;
import com.omni.code.request.CheckoutRequest;
import com.omni.code.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody CheckoutRequest request) {
        List<OrderItem> items = request.getItems();
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID is required.");
        }

        if (items == null || items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("At least one item is required in the order.");
        }

        if (request.getAddress() == null || request.getAddress().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is required.");
        }
        // Check each OrderItem for missing fields

        for (OrderItem item : items) {
            if (item.getProductId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID is required for each item.");
            }

            if (item.getQuantity() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be greater than zero for each item.");
            }

            if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must be greater than zero for each item.");
            }
        }
        try {
            checkoutService.checkout(request.getUserId(), request.getItems(), request.getAddress()); // Assuming checkout returns the created order
            return ResponseEntity.ok("Order placed successfully!"); // Return the created order
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock for product"); // Return null on error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while placing the order."); // Return null on error
        }
    }
}
