package com.G23.ParkIt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Integer carId;
    private String make;
    private String model;
    private String registrationNumber;
    private String year;
    private String driverLicenseNumber;
    private String transmission;
}

