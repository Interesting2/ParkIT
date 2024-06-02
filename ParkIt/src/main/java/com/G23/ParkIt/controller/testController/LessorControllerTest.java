package com.G23.ParkIt.controller.testController;
import com.G23.ParkIt.controller.LessorController;
import com.G23.ParkIt.entity.Lessor;
import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.service.LessorService;
import com.G23.ParkIt.util.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LessorControllerTest {
    @InjectMocks
    private LessorController lessorController;
    @Mock
    private LessorService lessorService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetLessorByIdSuccess() {
        Lessor mockLessor = new Lessor(1, 101, 201);
        when(lessorService.getLessorById(1)).thenReturn(mockLessor);
        Lessor result = lessorController.getLessorById(1);
        assertEquals(mockLessor, result);
    }
    @Test
    public void testGetAllLessors() {
        Lessor mockLessor = new Lessor(1, 101, 201);
        when(lessorService.getAllLessors()).thenReturn(Collections.singletonList(mockLessor));
        var results = lessorController.getAllLessors();
        assertEquals(1, results.size());
        assertEquals(mockLessor, results.get(0));
    }
    @Test
    public void testInsertLessorSuccess() {
        Lessor mockLessor = new Lessor(null, 101, 201);
        when(lessorService.insertLessor(mockLessor)).thenReturn(1);
        Result result = lessorController.insertLessor(mockLessor);
        assertEquals(Result.success(), result);
        verify(lessorService, times(1)).insertLessor(mockLessor);
    }
    @Test
    public void testUpdateLessorSuccess() {
        Lessor existingLessor = new Lessor(1, 101, 201);
        when(lessorService.updateLessor(existingLessor)).thenReturn(1);
        Result result = lessorController.updateLessor(existingLessor);
        assertEquals(Result.success(), result);
        verify(lessorService, times(1)).updateLessor(existingLessor);
    }
    @Test
    public void testDeleteLessorSuccess() {
        when(lessorService.deleteLessor(1)).thenReturn(1);
        Result result = lessorController.deleteLessor(1);
        assertEquals(Result.success(), result);
        verify(lessorService, times(1)).deleteLessor(1);
    }
    @Test
    public void testGetCarsByUserIdSuccess() {
        Car mockCar = new Car(); // 假设Car有一个默认的构造方法，如果需要的话，请填写必要的属性
        when(lessorService.getCarsByUserId(201)).thenReturn(Collections.singletonList(mockCar));
        ResponseEntity<List<Car>> response = lessorController.getCarsByUserId(201);
        List<Car> results = response.getBody(); // 从ResponseEntity中获取数据
        assertNotNull(results); // 确保结果不为null
        assertEquals(1, results.size()); // 检查列表的大小
        assertEquals(mockCar, results.get(0)); // 检查返回的车辆是否与mockCar匹配
    }
    @Test
    public void testGetCarsByLessorIdSuccess() {
        Car mockCar = new Car(); // Assuming a default constructor for Car, fill in necessary properties if required
        when(lessorService.getCarsByLessorId(1)).thenReturn(Collections.singletonList(mockCar));
        var results = lessorController.getCarsByLessorId(1);
        assertEquals(1, results.size());
        assertEquals(mockCar, results.get(0));
    }
}
