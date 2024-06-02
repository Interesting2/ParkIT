package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.Lessor;
import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.mapper.LessorMapper;
import com.G23.ParkIt.service.LessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessorServiceImpl implements LessorService {
    @Autowired
    private LessorMapper lessorMapper;
    @Override
    public Lessor getLessorById(Integer lessorId) {
        return lessorMapper.getLessorById(lessorId);
    }
    @Override
    public List<Lessor> getAllLessors() {
        return lessorMapper.getAllLessors();
    }
    @Override
    public int insertLessor(Lessor lessor) {
        return lessorMapper.insertLessor(lessor);
    }
    @Override
    public int updateLessor(Lessor lessor) {
        return lessorMapper.updateLessor(lessor);
    }
    @Override
    public int deleteLessor(Integer lessorId) {
        return lessorMapper.deleteLessor(lessorId);
    }
    @Override
    public List<Car> getCarsByUserId(Integer userId) {
        return lessorMapper.getCarsByUserId(userId);
    }
    @Override
    public List<Car> getCarsByLessorId(Integer lessorId) {
        return lessorMapper.getCarsByLessorId(lessorId);
    }
}
