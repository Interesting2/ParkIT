package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.ParkingSpot;

import java.util.List;

public interface ParkingSpotService {
    ParkingSpot getParkingSpotById(Integer spotId);
    List<ParkingSpot> getAllParkingSpots();
    void insertParkingSpot(ParkingSpot parkingSpot);
    void updateParkingSpot(ParkingSpot parkingSpot);
    void deleteParkingSpot(Integer spotId);
    ParkingSpot getParkingSpotByAddress(String address);
}
