package com.omni.code.controller;

import com.omni.code.entity.Review;
import com.omni.code.service.ProductService;
import com.omni.code.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
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
                             @RequestParam String reviewText) {
        reviewService.submitReview(productId, userId, rating, reviewText);
        // update Redis in asynchronous
        productService.updateTopFiveProductsInRedis();
    }

    // Endpoint to get all reviews for a product
    @GetMapping("/product/{productId}")
    public List<Review> getReviewsByProductId(@PathVariable Integer productId) {
        return reviewService.getReviewsByProductId(productId);
    }
}
