package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.Review;
import com.G23.ParkIt.mapper.ReviewMapper;
import com.G23.ParkIt.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Override
    public Review getReviewById(Integer reviewId) {
        return reviewMapper.getReviewById(reviewId);
    }
    @Override
    public List<Review> getAllReviews() {
        return reviewMapper.getAllReviews();
    }
    @Override
    public int insertReview(Review review) {
        return reviewMapper.insertReview(review);
    }
    @Override
    public int updateReview(Review review) {
        return reviewMapper.updateReview(review);
    }
    @Override
    public int deleteReview(Integer reviewId) {
        return reviewMapper.deleteReview(reviewId);
    }
}
