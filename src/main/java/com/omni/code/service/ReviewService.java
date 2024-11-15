package com.omni.code.service;

import com.omni.code.entity.Review;
import com.omni.code.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    // Submit a review for a product
    public void submitReview(Integer productId, String userId, Integer rating, String reviewText, String userName) {
        reviewMapper.submitReview(productId, userId, rating, reviewText,userName);
    }

    // Get all reviews for a product
    public List<Review> getReviewsByProductId(Integer productId) {
        return reviewMapper.getReviewsByProductId(productId);
    }
}
