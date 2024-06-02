package com.G23.ParkIt.mapper;

import com.G23.ParkIt.entity.Lessor;
import com.G23.ParkIt.entity.Car;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LessorMapper {
    Lessor getLessorById(Integer lessorId);
    List<Lessor> getAllLessors();
    int insertLessor(Lessor lessor);
    int updateLessor(Lessor lessor);
    int deleteLessor(Integer lessorId);
    List<Car> getCarsByUserId(Integer userId);
    List<Car> getCarsByLessorId(Integer lessorId);
}
