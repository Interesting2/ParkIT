package com.G23.ParkIt.mapper;

import com.G23.ParkIt.entity.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    Review getReviewById(Integer reviewId);
    List<Review> getAllReviews();
    int insertReview(Review review);
    int updateReview(Review review);
    int deleteReview(Integer reviewId);
}
