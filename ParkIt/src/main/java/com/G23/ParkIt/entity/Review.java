package com.G23.ParkIt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Integer reviewId;
    private Integer userId;
    private Integer rating;
    private String comments;
    private Integer listingId;
}
