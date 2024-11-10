package com.omni.code.service;

import com.omni.code.entity.Order;
import com.omni.code.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public void updateOrderStatus(Order order){
        order.setPaymentStatus("Completed");
        order.setOrderStatus("deliver pending");
        orderMapper.updateOrderStatus(order);
    }
}
