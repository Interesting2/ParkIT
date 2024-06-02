package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.Lessee;
import com.G23.ParkIt.service.LesseeService;
import com.G23.ParkIt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessees")
public class LesseeController {

    @Autowired
    private LesseeService lesseeService;

    @GetMapping("/getLessee/{id}")
    public Lessee getLesseeById(@PathVariable("id") Integer id) {
        return lesseeService.getLesseeById(id);
    }

    @GetMapping("/getAllLessee")
    public List<Lessee> getAllLessees() {
        return lesseeService.getAllLessees();
    }

    @PostMapping("/insertLessee")
    public Result insertLessee(@RequestBody Lessee lessee) {
        int result = lesseeService.insertLessee(lessee);
        if (result > 0) {
            return Result.success("Lessee inserted successfully");
        } else {
            return Result.sysError("Failed to insert Lessee");
        }
    }

    @PutMapping("/updateLessee")
    public Result updateLessee(@RequestBody Lessee lessee) {
        int result = lesseeService.updateLessee(lessee);
        if (result > 0) {
            return Result.success("Lessee updated successfully");
        } else {
            return Result.sysError("Failed to update Lessee");
        }
    }

    @DeleteMapping("/deleteLessee/{id}")
    public Result deleteLessee(@PathVariable("id") Integer id) {
        int result = lesseeService.deleteLessee(id);
        if (result > 0) {
            return Result.success("Lessee deleted successfully");
        } else {
            return Result.sysError("Failed to delete Lessee");
        }
    }
}
