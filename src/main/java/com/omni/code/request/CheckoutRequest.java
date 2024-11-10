package com.omni.code.request;

import com.omni.code.entity.OrderItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Data
public class CheckoutRequest {
    @NotNull(message = "User ID is required.")
    private String userId;

    @NotNull(message = "Address is required.")
    private String address;

    @Size(min = 1, message = "At least one item is required in the order.")
    private List<OrderItem> items;
}
