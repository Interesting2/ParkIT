import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import '../static/css/checkout.css';

const Checkout = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  // State Initialization
  const [listing, setListing] = useState({});
  const [carDetails, setCarDetails] = useState({
    make: '',
    model: '',
    year: '',
    transmission: '',
    registrationNumber: '',
    driverLicenseNumber: ''
  });

  const [paymentDetails, setPaymentDetails] = useState({
    cardNumber: '',
    cvv: '',
    expiry: ''
  });

  const [bookingPeriod, setBookingPeriod] = useState({
    start: '',
    end: ''
  });

  const [hours, setHours] = useState(0);
  const [subtotal, setSubtotal] = useState(0);

  useEffect(() => {
    // Fetch listing details by id
    // Here, replace with your actual API call
    setListing({
      rate: '20' // Example rate, replace with actual rate obtained from API call
    });
  }, [id]);

  useEffect(() => {
    if (bookingPeriod.start && bookingPeriod.end) {
      const start = new Date(bookingPeriod.start);
      const end = new Date(bookingPeriod.end);
      const duration = Math.abs(end - start) / 36e5; // convert to hours
      setHours(duration);
      setSubtotal(duration * parseFloat(listing.rate));
    }
  }, [bookingPeriod, listing.rate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    // Extracting listingId from the URL
    const listingId = parseInt(id);
  
    // Construct the booking object
    const booking = {
      timestamp: new Date(),  // Current datetime
      startTime: new Date(bookingPeriod.start),
      endTime: new Date(bookingPeriod.end),
      transactionPrice: subtotal,
      listingId: listingId,  // Add listing ID
      // ... You might also need user information, etc.
    };
  
    const requestData = {
      booking: booking,
      car: carDetails
    };
  
    try {
      const token = localStorage.getItem('token');
  
      const response = await fetch('http://localhost:8080/parkit/bookings/insertBooking', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(requestData)
      });
      
      const data = await response.json();
  
      if (data && data.message === "Booking inserted successfully.") {
        navigate(`/confirmation/${data.data}`);
      } else {
        // Handle error
      }
    } catch (error) {
      console.error("Error inserting booking:", error);
    }
  };
  
  

  return (
    <div className="checkout-page">
      <h2>Checkout</h2>
      <form onSubmit={handleSubmit} className="checkout-form">
        {/* Car Details Section */}
        <div className="section">
          <h3>Car Details</h3>
          {/* Individual input fields for car details */}
          <label>
            Make:
            <input type="text" value={carDetails.make} onChange={(e) => setCarDetails({ ...carDetails, make: e.target.value })} required />
          </label>
          <label>
            Model:
            <input type="text" value={carDetails.model} onChange={(e) => setCarDetails({ ...carDetails, model: e.target.value })} required />
          </label>
          <label>
            Year:
            <input type="number" min="1900" max={new Date().getFullYear()} value={carDetails.year} onChange={(e) => setCarDetails({ ...carDetails, year: e.target.value })} required />
          </label>
          <label>
            Transmission:
            <select value={carDetails.transmission} onChange={(e) => setCarDetails({ ...carDetails, transmission: e.target.value })} required>
              <option value="">Select</option>
              <option value="Automatic">Automatic</option>
              <option value="Manual">Manual</option>
            </select>
          </label>
          <label>
            Registration Number:
            <input type="text" value={carDetails.registrationNumber} onChange={(e) => setCarDetails({ ...carDetails, registrationNumber: e.target.value })} required />
          </label>
          <label>
            Driver License Number:
            <input type="text" value={carDetails.driverLicenseNumber} onChange={(e) => setCarDetails({ ...carDetails, driverLicenseNumber: e.target.value })} required />
          </label>
        </div>
        
        {/* Payment Details Section */}
        <div className="section">
          <h3>Payment Details</h3>
          <label>
            Card Number:
            <input type="text" value={paymentDetails.cardNumber} onChange={(e) => setPaymentDetails({ ...paymentDetails, cardNumber: e.target.value })} required />
          </label>
          <label>
            CVV:
            <input type="text" value={paymentDetails.cvv} onChange={(e) => setPaymentDetails({ ...paymentDetails, cvv: e.target.value })} required />
          </label>
          <label>
            Expiry:
            <input type="month" value={paymentDetails.expiry} onChange={(e) => setPaymentDetails({ ...paymentDetails, expiry: e.target.value })} required />
          </label>
        </div>
        
        {/* Booking Period and Calculation Section */}
        <div className="section">
          <h3>Booking Period</h3>
          <label>
            Start:
            <input type="datetime-local" value={bookingPeriod.start} onChange={(e) => setBookingPeriod({ ...bookingPeriod, start: e.target.value })} required />
          </label>
          <label>
            End:
            <input type="datetime-local" value={bookingPeriod.end} onChange={(e) => setBookingPeriod({ ...bookingPeriod, end: e.target.value })} required />
          </label>
          <p>Hours: {hours}</p>
          <p>Subtotal: ${subtotal.toFixed(2)}</p>
        </div>
        
        <button type="submit" className="checkout-button">Checkout</button>
      </form>
    </div>
  );
};

export default Checkout;
