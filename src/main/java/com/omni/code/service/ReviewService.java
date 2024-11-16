package com.omni.code.service;

import com.omni.code.entity.Review;
import com.omni.code.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

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


    public void updateReview(Integer reviewId, Integer rating, String reviewText) {
        // Validate input
        if (reviewId == null || rating == null || rating < 1 || rating > 5 || reviewText == null || reviewText.isEmpty()) {
            throw new IllegalArgumentException("Invalid input for updating the review.");
        }

        // Optionally validate review existence
        Review existingReview = reviewMapper.findById(reviewId);
        if (existingReview == null) {
            throw new NoSuchElementException("Review not found with ID: " + reviewId);
        }

        // Update review in the database
        int rowsAffected = reviewMapper.updateReview(reviewId, rating, reviewText);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update review with ID: " + reviewId);
        }
    }
}
