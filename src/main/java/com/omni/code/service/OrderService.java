package com.omni.code.service;

import com.omni.code.entity.Order;
import com.omni.code.entity.OrderItem;
import com.omni.code.mapper.OrderItemMapper;
import com.omni.code.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    public void updateOrderStatus(Order order){
        order.setPaymentStatus("Completed");
        order.setOrderStatus("deliver pending");
        orderMapper.updateOrderStatus(order);
    }

    public long getTotalCount(String startDate, String endDate, String status, String userId) {
        return orderMapper.getTotalCount(startDate, endDate, status, userId);
    }


    public List<Order> searchOrders(String startDate, String endDate, String status, String userId, int offset, int size) {
        return orderMapper.searchOrders(startDate, endDate, status, userId, offset, size);
    }

    // Fetch order items by orderId
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        return orderItemMapper.getOrderItemsByOrderId(orderId);
    }
}
