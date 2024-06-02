package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.ParkingSpot;
import com.G23.ParkIt.mapper.ParkingSpotMapper;
import com.G23.ParkIt.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {
    @Autowired
    private ParkingSpotMapper parkingSpotMapper;
    @Override
    public ParkingSpot getParkingSpotById(Integer spotId) {
        return parkingSpotMapper.getParkingSpotById(spotId);
    }
    @Override
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotMapper.getAllParkingSpots();
    }
    @Override
    public void insertParkingSpot(ParkingSpot parkingSpot) {
        parkingSpotMapper.insertParkingSpot(parkingSpot);
    }
    @Override
    public void updateParkingSpot(ParkingSpot parkingSpot) {
        parkingSpotMapper.updateParkingSpot(parkingSpot);
    }
    @Override
    public void deleteParkingSpot(Integer spotId) {
        parkingSpotMapper.deleteParkingSpot(spotId);
    }
    @Override
    public ParkingSpot getParkingSpotByAddress(String address) {
        return parkingSpotMapper.getParkingSpotByAddress(address);
    }
}
