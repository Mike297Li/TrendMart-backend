package com.omni.code.mapper;

import com.omni.code.entity.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ReviewMapper {
    // SQL query to insert a review into the 'reviews' table
    @Insert("INSERT INTO reviews (product_id, user_id, rating, review_text, created_at) " +
            "VALUES (#{productId}, #{userId}, #{rating}, #{reviewText}, NOW())")
    void submitReview(@Param("productId") Integer productId,
                      @Param("userId") String userId,
                      @Param("rating") Integer rating,
                      @Param("reviewText") String reviewText);

    // SQL query to fetch reviews by product_id
    @Select("SELECT review_id AS reviewId, product_id AS productId, user_id AS userId, " +
            "rating, review_text AS reviewText, created_at AS createdAt " +
            "FROM reviews WHERE product_id = #{productId}")
    List<Review> getReviewsByProductId(@Param("productId") Integer productId);
}
