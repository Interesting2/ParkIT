package com.G23.ParkIt.entity;
import lombok.Data;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private Integer bookingId; 
    private Timestamp timestamp; 
    private Integer transactionPrice; 
    private Timestamp startTime; 
    private Timestamp endTime; 
    private String status; 
    private Integer listingId; 
    private Integer lessorId; 
}