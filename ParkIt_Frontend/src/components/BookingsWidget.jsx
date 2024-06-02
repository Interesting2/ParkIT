import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'; 

const BookingsWidget = () => {
  const [bookings, setBookings] = useState([]);
  
  useEffect(() => {
    fetchBookings();
  }, []);

  const fetchBookings = async () => {
    const token = localStorage.getItem('token'); 
    if (!token) {
        return; 
    }
    
    try {
        const response = await fetch('http://localhost:8080/parkit/bookings/getBookingsByUser', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        const data = await response.json();
        if(data.code === "200" && Array.isArray(data.data)) {
            setBookings(data.data);
        } else {
            console.error('Unexpected response format:', data);
        }
    } catch (error) {
        console.error('Error fetching bookings:', error);
    }
  };

  return (
    <div className="bookings-widget widget-container">
      <div className="widget-header">Your Bookings</div>
      {bookings.map((booking) => (
        <div key={booking.bookingId} className="booking-item">
          <div className="booking-info">
            <span>Date: {booking.timestamp}</span>
            <span>Status: {booking.status}</span>
            <span>Listing ID: {booking.listingId}</span>
          </div>
          <Link to={`/confirmation/${booking.bookingId}`} className="details-link">See Details</Link>
        </div>
      ))}
    </div>
  );
};

export default BookingsWidget;
