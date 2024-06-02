package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.Car;

import java.util.List;

public interface CarService {
    Car getCarsById(Integer carId);
    List<Car> getAllCars();
    void insertCar(Car car);
    void updateCar(Car car);
    void deleteCar(Integer carId);
    Car getCarByRego(String rego);
}
