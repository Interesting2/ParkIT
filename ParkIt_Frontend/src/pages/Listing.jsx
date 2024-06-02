import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import '../static/css/listing.css';

const Listing = () => {
  const { id } = useParams();

  const [listing, setListing] = useState(null);
  const [spot, setSpot] = useState(null);
  const [loading, setLoading] = useState(true);
  const [reviews, setReviews] = useState([]);
  const [preview, setPreview] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/parkit/listings/${id}`)
      .then(response => response.json())
      .then(data => {
        console.log(data)
        setListing(data);
        return fetch(`http://localhost:8080/parkit/parkingSpots/getParkingSpot/${data.spotId}`);
      })
      .then(response => response.json())
      .then(spotData => {
        setSpot(spotData);
      })
      .catch(error => console.error('Error fetching data:', error))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div>Loading...</div>;

  return (
    <div className="listing-page">
      <h2>{spot.address}</h2>
      <p>Rate: ${listing.hourlyRate}/hr</p>
      <p>Status: {listing.status}</p>
      {/* <p>Lessee ID: {listing.lesseeId}</p> */}
      
      {/* If you have image paths in the future, you can uncomment this */}
      {/* <img src={listing.image} alt={listing.description} className="listing-image" /> */}
      
      <Link to={`/checkout/${listing.listingId}`} className="book-button">Book Now</Link>

      <br></br>
      <br></br>
      <br></br>
      {/* Preview section */}
      <div className="preview-section">
        <h3>Preview Image</h3>
            <div className="preview">
              <img
                src={`http://localhost:8080${listing.imageUrl}`}
                alt="Preview image"
                className="my-image"
              />
            </div>
      </div>

      {/* Reviews section */}
      <div className="reviews-section">
        <h3>Reviews</h3>
        {reviews.length === 0 ? (
          <p>No reviews yet.</p>
        ) : (
          reviews.map((review, index) => (
            <div key={index} className="review">
              <div className="review-header">
                <strong>{review.username}</strong>
                <span className="review-rating">{'★'.repeat(review.rating)}</span>
              </div>
              <p className="review-comment">{review.comment}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default Listing;
