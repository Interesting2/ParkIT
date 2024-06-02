package com.G23.ParkIt.controller.testController;

import com.G23.ParkIt.controller.BookingController;
import com.G23.ParkIt.entity.Booking;
import com.G23.ParkIt.entity.BookingRequestDTO;
import com.G23.ParkIt.entity.Car;
import com.G23.ParkIt.entity.Lessor;
import com.G23.ParkIt.entity.User;
import com.G23.ParkIt.service.BookingService;
import com.G23.ParkIt.service.CarService;
import com.G23.ParkIt.service.LessorService;
import com.G23.ParkIt.service.UserService;
import com.G23.ParkIt.util.JwtUtil;
import com.G23.ParkIt.util.Result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;
import java.util.List;
public class BookingControllerTest {
    @InjectMocks
    private BookingController bookingController;
    @Mock
    private BookingService bookingService;
    @Mock
    private CarService carService;
    @Mock
    private LessorService lessorService;
    @Mock
    private UserService userService;
    @Mock
    private JwtUtil jwtUtil;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testShowAllBookings() {
        // Setup
        Booking booking1 = new Booking();
        booking1.setBookingId(1);
        Booking booking2 = new Booking();
        booking2.setBookingId(2);
        when(bookingService.getAllBookings()).thenReturn(List.of(booking1, booking2));
        // Execute
        List<Booking> bookings = bookingController.getAllBookings();
        // Assert
        assertEquals(2, bookings.size());
        assertEquals(1, bookings.get(0).getBookingId());
        assertEquals(2, bookings.get(1).getBookingId());
    }
    @Test
    public void testGetBookingsByUserSuccess() {
        String token = "Bearer sometoken";
        when(jwtUtil.extractUsername(any())).thenReturn("dee");
        when(userService.getUserById(42)).thenReturn(
                new User(42, "123456", "dee", 10001, "dee@163.com", "732315", 1)
        );
        when(bookingService.getBookingsByUserId(1)).thenReturn(Collections.singletonList(new Booking()));
        Result result = bookingController.getBookingsByUser("sometoken");
        assertEquals("Authorization token missing or invalid.", result.getMessage());
    }
    @Test
    public void testUpdateBookingSuccess() {
        Booking booking = new Booking();
        // set any necessary properties on the booking object
        Result result = bookingController.updateBooking(booking);
        assertEquals("Success", result.getMessage());
    }

}

