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

    @Select("SELECT p.product_id AS productId, " +
            "p.picture_base64 AS pictureBase64, " +
            "p.name, " +
            "p.description, " +
            "p.price, " +
            "p.features, " +
            "p.average_rating AS averageRating, " +
            "p.created_at AS createdAt, " +
            "i.quantity AS quantity " +
            "FROM product p " +
            "JOIN inventory i ON p.product_id = i.product_id " +
            "WHERE p.product_id = #{productId}")
    Product getProductById(Long productId);

    @Select("SELECT p.product_id AS productId, " +
            "p.picture_base64 AS pictureBase64, " +
            "p.name, " +
            "p.description, " +
            "p.price, " +
            "p.features, " +
            "p.average_rating AS averageRating, " +
            "p.created_at AS createdAt, " +
            "i.quantity AS quantity " +
            "FROM product p " +
            "LEFT JOIN inventory i ON p.product_id = i.product_id")
    List<Product> getAllProducts();

    @Update("UPDATE product SET name=#{name}, description=#{description}, price=#{price}, features=#{features}, " +
            "average_rating=#{averageRating}, picture_base64=#{pictureBase64} WHERE product_id=#{productId}")
    void updateProduct(Product product);

    @Delete("DELETE FROM product WHERE product_id = #{productId}")
    void deleteProduct(Long productId);

    List<Product> searchProducts(@Param("name") String name,
                                 @Param("rating") Double rating,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice,
                                 @Param("offset") int offset,
                                 @Param("size") int size);

    long getTotalCount(@Param("name")String name,
                       @Param("rating") Double rating,
                       @Param("minPrice") Double minPrice,
                       @Param("maxPrice")Double maxPrice);
}