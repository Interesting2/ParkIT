package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.Booking;
import com.G23.ParkIt.mapper.BookingMapper;
import com.G23.ParkIt.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingMapper bookingMapper;
    @Override
    public Booking getBookingsById(Integer bookingId) {
        return bookingMapper.getBookingsById(bookingId);
    }
    @Override
    public List<Booking> getAllBookings() {
        return bookingMapper.getAllBookings();
    }
    @Override
    public void insertBooking(Booking booking) {
        bookingMapper.insertBooking(booking);
    }
    @Override
    public void updateBooking(Booking booking) {
        bookingMapper.updateBooking(booking);
    }
    @Override
    public void deleteBooking(Integer bookingId) {
        bookingMapper.deleteBooking(bookingId);
    }
    @Override
    public List<Booking> getBookingsByUserId(Integer userId) {
        return bookingMapper.getBookingsByUserId(userId);
    }
    
}
