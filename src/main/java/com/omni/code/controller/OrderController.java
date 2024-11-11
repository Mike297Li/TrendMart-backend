package com.omni.code.controller;

import com.omni.code.entity.Order;
import com.omni.code.response.OrderResultsResponse;
import com.omni.code.service.OrderService;
import com.omni.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/searchOrders")
    public OrderResultsResponse searchOrders(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status,
            @RequestParam String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Step 1: Check if the userId exists in the User table
        boolean isAdmin = userService.isUserAdmin(userId);

        // Step 2: Calculate offset for pagination
        int offset = (page - 1) * size;

        // Step 3: Fetch total count and orders based on user role
        long totalCount;
        List<Order> orders;

        if (isAdmin) {
            // Admin - fetch all orders within the date range and by status
            totalCount = orderService.getTotalCount(startDate, endDate, status, null); // Admin sees all, so userId filter is null
            if (totalCount == 0) {
                return new OrderResultsResponse(new ArrayList<>(), totalCount);
            }
            orders = orderService.searchOrders(startDate, endDate, status, null, offset, size);
        } else {
            // Customer - fetch only their orders within the date range and by status
            totalCount = orderService.getTotalCount(startDate, endDate, status, String.valueOf(userId)); // Pass userId to filter by this user
            if (totalCount == 0) {
                return new OrderResultsResponse(new ArrayList<>(), totalCount);
            }
            orders = orderService.searchOrders(startDate, endDate, status, userId, offset, size);
        }

        return new OrderResultsResponse(orders, totalCount);
    }

}
