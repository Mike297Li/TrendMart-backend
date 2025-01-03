package com.omni.code.controller;

import com.omni.code.entity.Review;
import com.omni.code.service.ProductService;
import com.omni.code.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://209.126.0.170" })
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    // Endpoint to submit a review
    @PostMapping("/submit")
    public void submitReview(@RequestParam Integer productId,
                             @RequestParam String userId,
                             @RequestParam Integer rating,
                             @RequestParam String reviewText,
                             @RequestParam String userName) {
        reviewService.submitReview(productId, userId, rating, reviewText,userName);
        // update Redis in asynchronous
        productService.updateTopFiveProductsInRedis();
    }

    // Endpoint to get all reviews for a product
    @GetMapping("/product/{productId}")
    public List<Review> getReviewsByProductId(@PathVariable Integer productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @PutMapping("/update")
    public void updateReview(@RequestParam Integer reviewId,
                             @RequestParam Integer rating,
                             @RequestParam String reviewText) {
        reviewService.updateReview(reviewId, rating, reviewText);
        // update Redis asynchronously
        productService.updateTopFiveProductsInRedis();
    }

    @DeleteMapping("/delete")
    public void deleteReview(@RequestParam Integer reviewId) {
        reviewService.deleteReview(reviewId);
        // Update Redis asynchronously
        productService.updateTopFiveProductsInRedis();
    }
}
