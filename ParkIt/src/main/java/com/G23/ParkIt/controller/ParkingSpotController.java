package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.ParkingSpot;
import com.G23.ParkIt.service.ParkingSpotService;
import com.G23.ParkIt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/parkingSpots")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping("/getParkingSpot/{spotId}")
    public ParkingSpot getParkingSpotById(@PathVariable Integer spotId) {
        return parkingSpotService.getParkingSpotById(spotId);
    }


    @GetMapping("/getAllParkingSpots")
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }

    @PostMapping("/insertParkingSpot")
    public Result insertParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        parkingSpotService.insertParkingSpot(parkingSpot);
        return Result.success();
    }

    @PutMapping("/updateParkingSpot/{spotId}")
    public Result updateParkingSpot(@PathVariable Integer spotId, @RequestBody ParkingSpot parkingSpot) {
        ParkingSpot existingParkingSpot = parkingSpotService.getParkingSpotById(spotId);
        if (existingParkingSpot != null) {
            parkingSpotService.updateParkingSpot(parkingSpot);
            return Result.success();
        } else {
            return Result.sysError("ParkingSpot not found for the provided spotId");
        }
    }

    @DeleteMapping("/deleteParkingSpot/{spotId}")
    public Result deleteParkingSpot(@PathVariable Integer spotId) {
        ParkingSpot existingParkingSpot = parkingSpotService.getParkingSpotById(spotId);
        if (existingParkingSpot != null) {
            parkingSpotService.deleteParkingSpot(spotId);
            return Result.success();
        } else {
            return Result.sysError("ParkingSpot not found for the provided spotId");
        }
    }
}
