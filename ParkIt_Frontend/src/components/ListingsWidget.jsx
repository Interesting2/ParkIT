import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'; 

const ListingsWidget = () => {
  const [listings, setListings] = useState([]);
  
  useEffect(() => {
    // Fetch listings from API when component mounts
    fetchListings();
  }, []);

  const fetchListings = async () => {
    const token = localStorage.getItem('token'); 
    if (!token) {
        return; 
    }
    
    try {
          const response = await fetch('http://localhost:8080/parkit/listings/getListingsByUser', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        const data = await response.json();
        if(data.code === "200" && Array.isArray(data.data)) {
          console.log(data.data)
            setListings(data.data);
        } else {
            console.error('Unexpected response format:', data);
        }
    } catch (error) {
        console.error('Error fetching bookings:', error);
    }

  };

  return (
    <div className="listings-widget widget-container">
      <div className="widget-header">Your Listings</div>
      {listings.map((listing) => (
        <Link key={listing.id} to={`/listing/${listing.listingId}`} className="listing">
          <div>
                <span>Hourly Rate: ${listing.hourlyRate}</span><br />
                <span>Status: {listing.status}</span><br />
          </div>
        </Link>
      ))}
      <Link to="/create-listing">
        <button className="create-listing-btn">Create New Listing</button>
      </Link>
    </div>
  );
};

export default ListingsWidget;
