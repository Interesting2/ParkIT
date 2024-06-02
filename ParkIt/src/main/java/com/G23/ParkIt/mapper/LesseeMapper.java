package com.G23.ParkIt.mapper;

import com.G23.ParkIt.entity.Lessee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LesseeMapper {
    Lessee getLesseeById(Integer lesseeId);
    List<Lessee> getAllLessees();
    int insertLessee(Lessee lessee);
    int updateLessee(Lessee lessee);
    int deleteLessee(Integer lesseeId);
}
