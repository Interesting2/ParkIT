package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.mapper.CarMapper;
import com.G23.ParkIt.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Override
    public Car getCarsById(Integer carId) {
        return carMapper.getCarsById(carId);
    }
    @Override
    public List<Car> getAllCars() {
        return carMapper.getAllCars();
    }
    @Override
    public void insertCar(Car car) {
        carMapper.insertCar(car);
    }
    @Override
    public void updateCar(Car car) {
        carMapper.updateCar(car);
    }
    @Override
    public void deleteCar(Integer carId) {
        carMapper.deleteCar(carId);
    }
    @Override
    public Car getCarByRego(String rego) {
        return carMapper.getCarByRego(rego);
    }
}
