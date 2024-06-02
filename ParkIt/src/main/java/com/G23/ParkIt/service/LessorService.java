package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.Lessor;
import com.G23.ParkIt.entity.Car;

import java.util.List;

public interface LessorService {
    Lessor getLessorById(Integer lessorId);
    List<Lessor> getAllLessors();
    int insertLessor(Lessor lessor);
    int updateLessor(Lessor lessor);
    int deleteLessor(Integer lessorId);
    public List<Car> getCarsByUserId(Integer userId);
    List<Car> getCarsByLessorId(Integer lessorId);
}
