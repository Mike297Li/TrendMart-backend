package com.omni.code.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface InventoryMapper {
    @Select("SELECT quantity FROM inventory WHERE product_id = #{productId}")
    Integer checkStock(Long productId);

    @Update("UPDATE inventory SET quantity = quantity - #{quantity} WHERE product_id = #{productId}")
    void deductStock(Long productId, int quantity);
}
