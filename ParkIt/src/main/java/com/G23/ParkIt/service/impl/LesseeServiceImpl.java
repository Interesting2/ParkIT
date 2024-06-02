package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.Lessee;
import com.G23.ParkIt.mapper.LesseeMapper;
import com.G23.ParkIt.service.LesseeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LesseeServiceImpl implements LesseeService {
    @Autowired
    private LesseeMapper lesseeMapper;
    @Override
    public Lessee getLesseeById(Integer lesseeId) {
        return lesseeMapper.getLesseeById(lesseeId);
    }
    @Override
    public List<Lessee> getAllLessees() {
        return lesseeMapper.getAllLessees();
    }
    @Override
    public int insertLessee(Lessee lessee) {
        return lesseeMapper.insertLessee(lessee);
    }
    @Override
    public int updateLessee(Lessee lessee) {
        return lesseeMapper.updateLessee(lessee);
    }
    @Override
    public int deleteLessee(Integer lesseeId) {
        return lesseeMapper.deleteLessee(lesseeId);
    }
}
