package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.Booking;
import com.G23.ParkIt.entity.BookingRequestDTO;
import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.entity.Lessor;
import com.G23.ParkIt.service.BookingService;
import com.G23.ParkIt.service.CarService;
import com.G23.ParkIt.service.LessorService;
import com.G23.ParkIt.service.UserService;
import com.G23.ParkIt.util.JwtUtil;
import com.G23.ParkIt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

// import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private CarService carService;
    @Autowired
    private LessorService lessorService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable Integer bookingId) {
        return bookingService.getBookingsById(bookingId);
    }

    @GetMapping("/getAllBookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/insertBooking")
    public Result insertBooking(@RequestBody BookingRequestDTO bookingRequest, @RequestHeader("Authorization") String authHeader) {
        try {
            Car carDetails = bookingRequest.getCar();
            Booking booking = bookingRequest.getBooking();

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Extract the token from "Bearer [token]".
                String username = jwtUtil.extractUsername(token);

                Integer user_id = userService.getUserId(username).getUserId();
                
                // Ensure the same car doesnt already exist in the system
                Integer car_id = null;
                Car existing_car = carService.getCarByRego(carDetails.getRegistrationNumber());
                if (existing_car == null) {
                    carService.insertCar(carDetails);
                    car_id = carDetails.getCarId();
                } else {
                    car_id = existing_car.getCarId();
                }

                // Ensure there is no identical lessor entry
                Lessor newLessor = new Lessor(null, car_id, user_id);
                lessorService.insertLessor(newLessor);
                booking.setLessorId(newLessor.getLessorId());

            }
            else {
                return Result.sysError("Failed to insert booking.");
            }
            booking.setStatus("Confirmed");
            
            bookingService.insertBooking(booking);
            return Result.success("Booking inserted successfully.", booking.getBookingId());
        } catch (Exception e) {
            return Result.sysError("Failed to insert booking.");
        }
    }

    @PutMapping("/updateBooking/{bookingId}")
    public Result updateBooking(@RequestBody Booking booking) {
        try {
            bookingService.updateBooking(booking);
            return Result.success("Booking updated successfully.");
        } catch (Exception e) {
            return Result.sysError("Failed to update booking.");
        }
    }

    @DeleteMapping("/deleteBooking/{bookingId}")
    public Result deleteBooking(@PathVariable Integer bookingId) {
        try {
            bookingService.deleteBooking(bookingId);
            return Result.success("Booking deleted successfully.");
        } catch (Exception e) {
            return Result.sysError("Failed to delete booking.");
        }
    }

    @GetMapping("/getBookingsByUser")
    public Result getBookingsByUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7); // Extract the token.
                String username = jwtUtil.extractUsername(token);
                if (username == null) {
                    return Result.sysError("Invalid token. Username extraction failed.");
                }
                
                Integer user_id = userService.getUserId(username).getUserId();
                if (user_id == null) {
                    return Result.sysError("User not found.");
                }

                List<Booking> bookings = bookingService.getBookingsByUserId(user_id);
                return Result.success("Bookings fetched successfully.", bookings);

            } catch (Exception e) {
                return Result.sysError("An error occurred while fetching bookings: " + e.getMessage());
            }
        } else {
            return Result.sysError("Authorization token missing or invalid.");
        }
    }
}
