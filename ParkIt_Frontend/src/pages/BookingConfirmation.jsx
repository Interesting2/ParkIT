import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../static/css/bookingconfirmation.css';

const BookingConfirmation = () => {
    const { id } = useParams();
    const [booking, setBooking] = useState(null);
    const [loading, setLoading] = useState(true);

    const navigate = useNavigate();
    const returnPrevious = () => {
        navigate(-1);
    }
    useEffect(() => {
        // Fetch the booking details by id
        fetch(`http://localhost:8080/parkit/bookings/${id}`)
            .then(response => response.json())
            .then(data => {
                setBooking({
                    bookingId: data.bookingId,
                    startTime: data.startTime,
                    endTime: data.endTime,
                    transactionPrice: data.transactionPrice,
                    status: data.status,
                    listingId: data.listingId,
                    lessorId: data.lessorId
                    // Add other fields if required
                });
            })
            .catch(error => console.error('Error fetching booking:', error))
            .finally(() => setLoading(false));
    }, [id]);
    
  
    if (loading) return <div>Loading...</div>;
    if (!booking) return <div>No booking details available</div>;
  
    return (
        <div className="confirmation-page">
            <button className="back-btn" onClick={returnPrevious}>Back</button>
            <h2 className="confirmation-title">Booking Confirmation Details</h2>
            <p>Booking ID: {booking.bookingId}</p>
            <p>Transaction Price: ${booking.transactionPrice}</p>
            <p>Booking Start Time: {new Date(booking.startTime).toLocaleString()}</p>
            <p>Booking End Time: {new Date(booking.endTime).toLocaleString()}</p>
            <p>Status: {booking.status}</p>
            <p>Lessor ID: {booking.lessorId}</p>
        </div>
    );
};
  
export default BookingConfirmation;
