package com.omni.code.mapper;

import com.omni.code.entity.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ReviewMapper {
    // SQL query to insert a review into the 'reviews' table
    @Insert("INSERT INTO reviews (product_id, user_id, rating, review_text,user_name, created_at) " +
            "VALUES (#{productId}, #{userId}, #{rating}, #{reviewText}, #{userName}, NOW())")
    void submitReview(@Param("productId") Integer productId,
                      @Param("userId") String userId,
                      @Param("rating") Integer rating,
                      @Param("reviewText") String reviewText,
                      @Param("userName") String userName);

    // SQL query to fetch reviews by product_id
    @Select("SELECT review_id AS reviewId, product_id AS productId, user_id AS userId, " +
            "rating, review_text AS reviewText,user_name As userName, created_at AS createdAt " +
            "FROM reviews WHERE product_id = #{productId}")
    List<Review> getReviewsByProductId(@Param("productId") Integer productId);

    // Update an existing review
    @Update("UPDATE reviews SET rating = #{rating}, review_text = #{reviewText}, updated_at = NOW() WHERE review_id = #{reviewId}")
    int updateReview(@Param("reviewId") Integer reviewId,
                     @Param("rating") Integer rating,
                     @Param("reviewText") String reviewText);

    @Select("SELECT review_id, product_id, user_id, rating, review_text, created_at, user_name " +
            "FROM reviews WHERE review_id = #{reviewId}")
    Review findById(@Param("reviewId") Integer reviewId);

    @Delete("DELETE FROM reviews WHERE review_id = #{reviewId}")
    int deleteReview(@Param("reviewId") Integer reviewId);

}
