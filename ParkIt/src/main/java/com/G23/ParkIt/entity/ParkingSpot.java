package com.G23.ParkIt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpot {
    private Integer spotId;
    private String address;
    private Integer spotNum;
    private String spotType;
    private Double latitude;
    private Double longitude;

}


