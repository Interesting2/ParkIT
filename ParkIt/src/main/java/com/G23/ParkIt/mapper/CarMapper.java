package com.G23.ParkIt.mapper;

import com.G23.ParkIt.entity.Car;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper {
    Car getCarsById(Integer carId);
    List<Car> getAllCars();
    int insertCar(Car car);
    int updateCar(Car car);
    int deleteCar(Integer carId);
    Car getCarByRego(String rego);
}
