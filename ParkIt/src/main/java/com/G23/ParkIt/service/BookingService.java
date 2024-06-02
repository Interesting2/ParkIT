package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking getBookingsById(Integer bookingId);
    List<Booking> getAllBookings();
    void insertBooking(Booking booking);
    void updateBooking(Booking booking);
    void deleteBooking(Integer bookingId);
    List<Booking> getBookingsByUserId(Integer userId);
}
