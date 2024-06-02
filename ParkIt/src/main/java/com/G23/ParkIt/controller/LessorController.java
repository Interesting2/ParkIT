package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.Lessor;
import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.service.LessorService;
import com.G23.ParkIt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessors")
public class LessorController {

    @Autowired
    private LessorService lessorService;

    @GetMapping("/getLessor/{id}")
    public Lessor getLessorById(@PathVariable("id") Integer id) {
        return lessorService.getLessorById(id);
    }

    @GetMapping("/getAllLessor")
    public List<Lessor> getAllLessors() {
        return lessorService.getAllLessors();
    }

    @PostMapping("/insertLessor")
    public Result insertLessor(@RequestBody Lessor lessor) {
        int result = lessorService.insertLessor(lessor);
        if (result > 0) {
            return Result.success();
        } else {
            return Result.sysError("Failed to insert Lessor");
        }
    }

    @PutMapping("/updateLessor")
    public Result updateLessor(@RequestBody Lessor lessor) {
        int result = lessorService.updateLessor(lessor);
        if (result > 0) {
            return Result.success();
        } else {
            return Result.sysError("Failed to update Lessor");
        }
    }

    @DeleteMapping("/deleteLessor/{lessorId}")
    public Result deleteLessor(@PathVariable("id") Integer id) {
        int result = lessorService.deleteLessor(id);
        if (result > 0) {
            return Result.success();
        } else {
            return Result.sysError("Failed to delete Lessor");
        }
    }

    @GetMapping("/getCarsByUserId/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable Integer userId) {
        List<Car> cars = lessorService.getCarsByUserId(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/geCarsByLessorId/{lessorId}")
    public List<Car> getCarsByLessorId(@PathVariable("lessorId") Integer lessorId) {
        return lessorService.getCarsByLessorId(lessorId);
    }
}
