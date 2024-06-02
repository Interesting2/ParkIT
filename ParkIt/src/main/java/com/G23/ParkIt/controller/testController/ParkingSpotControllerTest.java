package com.G23.ParkIt.controller.testController;

import com.G23.ParkIt.controller.ParkingSpotController;
import com.G23.ParkIt.entity.ParkingSpot;
import com.G23.ParkIt.service.ParkingSpotService;
import com.G23.ParkIt.util.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ParkingSpotControllerTest {
    @InjectMocks
    private ParkingSpotController parkingSpotController;
    @Mock
    private ParkingSpotService parkingSpotService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetParkingSpotByIdSuccess() {
        ParkingSpot mockSpot = new ParkingSpot(1, "123 Main St", 5, "Standard", 34.5, -100.5);
        when(parkingSpotService.getParkingSpotById(1)).thenReturn(mockSpot);

        ParkingSpot result = parkingSpotController.getParkingSpotById(1);

        assertEquals(mockSpot, result);
    }
    @Test
    public void testGetAllParkingSpots() {
        ParkingSpot mockSpot = new ParkingSpot(1, "123 Main St", 5, "Standard", 34.5, -100.5);
        when(parkingSpotService.getAllParkingSpots()).thenReturn(Collections.singletonList(mockSpot));

        var results = parkingSpotController.getAllParkingSpots();

        assertEquals(1, results.size());
        assertEquals(mockSpot, results.get(0));
    }
    @Test
    public void testInsertParkingSpotSuccess() {
        ParkingSpot mockSpot = new ParkingSpot(null, "123 Main St", 5, "Standard", 34.5, -100.5);
        Result expected = Result.success();

        Result result = parkingSpotController.insertParkingSpot(mockSpot);

        assertEquals(expected, result);
        verify(parkingSpotService, times(1)).insertParkingSpot(mockSpot);
    }
    @Test
    public void testUpdateParkingSpotSuccess() {
        ParkingSpot existingSpot = new ParkingSpot(1, "123 Main St", 5, "Standard", 34.5, -100.5);
        when(parkingSpotService.getParkingSpotById(1)).thenReturn(existingSpot);

        Result result = parkingSpotController.updateParkingSpot(1, existingSpot);

        assertEquals(Result.success(), result);
        verify(parkingSpotService, times(1)).updateParkingSpot(existingSpot);
    }
    @Test
    public void testDeleteParkingSpotSuccess() {
        ParkingSpot existingSpot = new ParkingSpot(1, "123 Main St", 5, "Standard", 34.5, -100.5);
        when(parkingSpotService.getParkingSpotById(1)).thenReturn(existingSpot);

        Result result = parkingSpotController.deleteParkingSpot(1);

        assertEquals(Result.success(), result);
        verify(parkingSpotService, times(1)).deleteParkingSpot(1);
    }
}
