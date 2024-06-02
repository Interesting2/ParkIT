package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.entity.User;
import com.G23.ParkIt.service.CarService;
import com.G23.ParkIt.util.JwtUtil;
import com.G23.ParkIt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/getCar")
    public ResponseEntity<Car> getCarById(@PathVariable Integer carId, @RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract the token from "Bearer [token]".
            String username = jwtUtil.extractUsername(token);
            try {
                Car car = carService.getCarsById(carId);
                if(car != null) {
                    return new ResponseEntity<>(car, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            } catch(Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllCars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/insertCar")
    public Result insertCar(@RequestBody Car car) {
        try {
            carService.insertCar(car);
            return Result.success("Car inserted successfully.");
        } catch (Exception e) {
            return Result.sysError("Failed to insert car.");
        }
    }

    @PutMapping("/updateCar")
    public Result updateCar(@RequestBody Car car) {
        try {
            carService.updateCar(car);
            return Result.success("Car updated successfully.");
        } catch (Exception e) {
            return Result.sysError("Failed to update car.");
        }
    }

    @DeleteMapping("/deleteCar/{carId}")
    public Result deleteCar(@PathVariable Integer carId) {
        try {
            carService.deleteCar(carId);
            return Result.success("Car deleted successfully.");
        } catch (Exception e) {
            return Result.sysError("Failed to delete car.");
        }
    }
}
