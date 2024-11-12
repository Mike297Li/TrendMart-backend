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


    @Select("SELECT order_item_id AS orderItemId, order_id AS orderId, product_id AS productId, quantity, price " +
            "FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
