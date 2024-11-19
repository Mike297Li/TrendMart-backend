package com.omni.code.mapper;

import com.omni.code.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Insert("INSERT INTO order_item (order_id, product_id, quantity, price) VALUES (#{orderId}, #{productId}, #{quantity}, #{price})")
    void insertOrderItem(OrderItem orderItem);


    @Select("SELECT oi.order_item_id AS orderItemId, oi.order_id AS orderId, oi.product_id AS productId, oi.quantity, oi.price, p.name AS name " +
            "FROM order_item oi " +
            "LEFT JOIN product p ON oi.product_id = p.product_id " +
            "WHERE oi.order_id = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
