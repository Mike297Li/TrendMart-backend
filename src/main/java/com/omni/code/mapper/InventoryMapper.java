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

    // New method to update the quantity of an existing inventory item
    @Update("UPDATE inventory SET quantity = #{quantity}, updated_at = Now() WHERE product_id = #{productId}")
    void updateInventory(@Param("productId") long productId, @Param("quantity") int quantity);

    // New method to delete an inventory item by product_id
    @Delete("DELETE FROM inventory WHERE product_id = #{productId}")
    void deleteInventory(Long productId);

}
