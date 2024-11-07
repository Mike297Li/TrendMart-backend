package com.omni.code.mapper;

import org.apache.ibatis.annotations.*;


@Mapper
public interface InventoryMapper {
    @Select("SELECT quantity FROM inventory WHERE product_id = #{productId}")
    Integer checkStock(Long productId);

    @Update("UPDATE inventory SET quantity = quantity - #{quantity} WHERE product_id = #{productId}")
    void deductStock(Long productId, int quantity);

    @Insert("INSERT INTO inventory (product_id, quantity, updated_at) " +
            "VALUES (#{productId}, #{quantity}, Now())")
    void insertInventory(@Param("productId") long productId,
                         @Param("quantity") int quantity);

}