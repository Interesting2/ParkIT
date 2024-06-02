package com.G23.ParkIt.mapper;

import com.G23.ParkIt.entity.ParkingSpot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParkingSpotMapper {
    ParkingSpot getParkingSpotById(Integer spotId);
    List<ParkingSpot> getAllParkingSpots();
    void insertParkingSpot(ParkingSpot parkingSpot);
    void updateParkingSpot(ParkingSpot parkingSpot);
    void deleteParkingSpot(Integer spotId);
    ParkingSpot getParkingSpotByAddress(String address);
}
