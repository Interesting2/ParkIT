package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.Review;
import com.G23.ParkIt.service.ReviewService;
import com.G23.ParkIt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getReview/{id}")
    public Review getReviewById(@PathVariable("id") Integer id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/getAllReviews")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping("/insertReview")
    public Result insertReview(@RequestBody Review review) {
        int result = reviewService.insertReview(review);
        if (result > 0) {
            return Result.success("Review inserted successfully");
        } else {
            return Result.sysError("Failed to insert Review");
        }
    }

    @PutMapping("/updateReview")
    public Result updateReview(@RequestBody Review review) {
        int result = reviewService.updateReview(review);
        if (result > 0) {
            return Result.success("Review updated successfully");
        } else {
            return Result.sysError("Failed to update Review");
        }
    }

    @DeleteMapping("/deleteReview/{id}")
    public Result deleteReview(@PathVariable("id") Integer id) {
        int result = reviewService.deleteReview(id);
        if (result > 0) {
            return Result.success("Review deleted successfully");
        } else {
            return Result.sysError("Failed to delete Review");
        }
    }
}
