package com.omni.code.mapper;

import com.omni.code.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO `order` (user_id, total_amount, order_status, payment_status, created_at, address) " +
            "VALUES (#{userId}, #{totalAmount}, #{orderStatus}, #{paymentStatus}, NOW(),#{address})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void insertOrder(Order order);

    // Update order status method
    @Update("UPDATE `order` SET order_status = #{orderStatus}, payment_status = #{paymentStatus}, updated_at = NOW() " +
            "WHERE order_id = #{orderId}")
    void updateOrderStatus(Order order);

}
