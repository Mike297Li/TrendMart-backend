package com.omni.code.request;

import com.omni.code.entity.OrderItem;
import lombok.Data;

import java.util.List;
@Data
public class CheckoutRequest {
    private String userId;
    private List<OrderItem> items; // List of items to order
    private String address;

}
