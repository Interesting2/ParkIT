package com.G23.ParkIt.mapper;

import com.G23.ParkIt.entity.Booking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookingMapper {
    Booking getBookingsById(Integer bookingId);
    List<Booking> getAllBookings();
    int insertBooking(Booking booking);
    int updateBooking(Booking booking);
    int deleteBooking(Integer bookingId);
    List<Booking> getBookingsByUserId(Integer userId);
}
