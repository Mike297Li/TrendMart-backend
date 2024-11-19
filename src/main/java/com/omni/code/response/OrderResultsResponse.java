package com.omni.code.response;


import com.omni.code.entity.Order;
import com.omni.code.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class OrderResultsResponse {
    private List<Order> orders;
    private long totalCount;

    public OrderResultsResponse(List<Order> orders, long totalCount) {
        this.orders = orders;
        this.totalCount = totalCount;
    }
}
