package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.Lessee;

import java.util.List;

public interface LesseeService {
    Lessee getLesseeById(Integer lesseeId);
    List<Lessee> getAllLessees();
    int insertLessee(Lessee lessee);
    int updateLessee(Lessee lessee);
    int deleteLessee(Integer lesseeId);
}
