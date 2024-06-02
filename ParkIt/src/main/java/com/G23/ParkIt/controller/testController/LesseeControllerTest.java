package com.G23.ParkIt.controller.testController;

import com.G23.ParkIt.controller.LesseeController;
import com.G23.ParkIt.entity.Lessee;
import com.G23.ParkIt.service.LesseeService;
import com.G23.ParkIt.util.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LesseeControllerTest {
    @InjectMocks
    private LesseeController lesseeController;
    @Mock
    private LesseeService lesseeService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetLesseeByIdSuccess() {
        Lessee mockLessee = new Lessee(1, 101, 201);
        when(lesseeService.getLesseeById(1)).thenReturn(mockLessee);
        Lessee result = lesseeController.getLesseeById(1);
        assertEquals(mockLessee, result);
    }
    @Test
    public void testGetAllLessees() {
        Lessee mockLessee = new Lessee(1, 101, 201);
        when(lesseeService.getAllLessees()).thenReturn(Collections.singletonList(mockLessee));
        var results = lesseeController.getAllLessees();
        assertEquals(1, results.size());
        assertEquals(mockLessee, results.get(0));
    }
    @Test
    public void testInsertLesseeSuccess() {
        Lessee mockLessee = new Lessee(null, 101, 201);
        when(lesseeService.insertLessee(mockLessee)).thenReturn(1);
        Result result = lesseeController.insertLessee(mockLessee);
        assertEquals(Result.success("Lessee inserted successfully"), result);
        verify(lesseeService, times(1)).insertLessee(mockLessee);
    }
    @Test
    public void testUpdateLesseeSuccess() {
        Lessee existingLessee = new Lessee(1, 101, 201);
        when(lesseeService.updateLessee(existingLessee)).thenReturn(1);
        Result result = lesseeController.updateLessee(existingLessee);
        assertEquals(Result.success("Lessee updated successfully"), result);
        verify(lesseeService, times(1)).updateLessee(existingLessee);
    }
    @Test
    public void testDeleteLesseeSuccess() {
        when(lesseeService.deleteLessee(1)).thenReturn(1);
        Result result = lesseeController.deleteLessee(1);
        assertEquals(Result.success("Lessee deleted successfully"), result);
        verify(lesseeService, times(1)).deleteLessee(1);
    }
}

