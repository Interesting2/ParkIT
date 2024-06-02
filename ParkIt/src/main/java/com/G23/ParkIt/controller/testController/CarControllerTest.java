package com.G23.ParkIt.controller.testController;

import com.G23.ParkIt.controller.CarController;
import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.service.CarService;
import com.G23.ParkIt.util.JwtUtil;
import com.G23.ParkIt.util.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CarControllerTest {
    @InjectMocks
    private CarController carController;
    @Mock
    private CarService carService;
    @Mock
    private JwtUtil jwtUtil;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetCarByIdSuccess() {
        Car mockCar = new Car(1, "Toyota", "Corolla", "ABC123", "2020", "DL12345", "Automatic");
        when(carService.getCarsById(1)).thenReturn(mockCar);
        Car result = carController.getCarById(1,"Bearer 123").getBody();
        assertEquals(mockCar, result);
    }

    @Test
    public void testGetAllCars() {
        Car mockCar = new Car(1, "Toyota", "Corolla", "ABC123", "2020", "DL12345", "Automatic");
        when(carService.getAllCars()).thenReturn(Collections.singletonList(mockCar));
        var results = carController.getAllCars();
        assertEquals(1, results.size());
        assertEquals(mockCar, results.get(0));
    }

    @Test
    public void testInsertCarSuccess() {
        Car mockCar = new Car(null, "Toyota", "Corolla", "ABC123", "2020", "DL12345", "Automatic");
        doNothing().when(carService).insertCar(mockCar);
        Result result = carController.insertCar(mockCar);
        assertEquals(Result.success("Car inserted successfully."), result);
        verify(carService, times(1)).insertCar(mockCar);
    }

    @Test
    public void testUpdateCarSuccess() {
        Car existingCar = new Car(1, "Toyota", "Corolla", "ABC123", "2020", "DL12345", "Automatic");
        doNothing().when(carService).updateCar(existingCar);
        Result result = carController.updateCar(existingCar);
        assertEquals(Result.success("Car updated successfully."), result);
        verify(carService, times(1)).updateCar(existingCar);
    }

    @Test
    public void testDeleteCarSuccess() {
        doNothing().when(carService).deleteCar(1);
        Result result = carController.deleteCar(1);
        assertEquals(Result.success("Car deleted successfully."), result);
        verify(carService, times(1)).deleteCar(1);
    }
}
