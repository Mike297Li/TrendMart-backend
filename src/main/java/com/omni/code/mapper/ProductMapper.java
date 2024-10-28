package com.omni.code.mapper;


import com.omni.code.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO product (name, description, price, features, average_rating, created_at, picture_base64) " +
            "VALUES (#{name}, #{description}, #{price}, #{features}, #{averageRating}, NOW(), #{pictureBase64})")
    @Options(useGeneratedKeys = true, keyProperty = "productId")
    void insertProduct(Product product);

    @Select("SELECT product_id AS productId, picture_base64 AS pictureBase64, name, description, price, features, average_rating AS averageRating, created_at AS createdAt FROM product WHERE product_id = #{productId}")
    Product getProductById(Long productId);

    @Select("SELECT product_id AS productId, picture_base64 AS pictureBase64, name, description, price, features, average_rating AS averageRating, created_at AS createdAt FROM product")
    List<Product> getAllProducts();

    @Update("UPDATE product SET name=#{name}, description=#{description}, price=#{price}, features=#{features}, " +
            "average_rating=#{averageRating}, picture_base64=#{pictureBase64} WHERE product_id=#{productId}")
    void updateProduct(Product product);

    @Delete("DELETE FROM product WHERE product_id = #{productId}")
    void deleteProduct(Long productId);
}