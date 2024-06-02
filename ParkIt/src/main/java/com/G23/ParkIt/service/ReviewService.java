package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.Review;

import java.util.List;

public interface ReviewService {
    Review getReviewById(Integer reviewId);
    List<Review> getAllReviews();
    int insertReview(Review review);
    int updateReview(Review review);
    int deleteReview(Integer reviewId);
}
