package com.G23.ParkIt.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
    private Integer listingId; 
    private Integer hourlyRate; 
    private String status; 
    private Integer spotId; 
    private Integer lesseeId; 
    private String description;
    private String imageUrl;
}

