import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import '../static/css/SearchResults.css'; 

const SearchResults = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(true);
  const [query, setQuery] = useState('');
  const location = useLocation();

  // Haversine formula to calculate distance between two coordinates
  const calculateDistance = (lat1, lon1, lat2, lon2) => {
    const R = 6371; // Radius of the earth in km
    const dLat = (lat2 - lat1) * (Math.PI/180);
    const dLon = (lon2 - lon1) * (Math.PI/180);
    const a = Math.sin(dLat/2) * Math.sin(dLat/2) +
              Math.cos(lat1 * (Math.PI/180)) * Math.cos(lat2 * (Math.PI/180)) * 
              Math.sin(dLon/2) * Math.sin(dLon/2); 
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    const d = R * c; // Distance in km
    return d;
  }

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const searchQuery = params.get('address');
    setQuery(searchQuery || ''); 

    // Assuming you get user's current location's lat and lon from somewhere
    const currentUserLat = 0; // replace with actual user latitude
    const currentUserLon = 0; // replace with actual user longitude

    if(searchQuery) {
      fetch('http://localhost:8080/parkit/listings/getAllListings')
        .then(response => response.json())
        .then(async listings => {
          const withDistances = await Promise.all(listings.map(async listing => {
            const spot = await fetch(`http://localhost:8080/parkit/parkingSpots/getParkingSpot/${listing.spotId}`)
              .then(response => response.json());

            const distance = calculateDistance(currentUserLat, currentUserLon, spot.latitude, spot.longitude);
            
            return {
              ...listing,
              distance,
              image: '/path-to-image', // replace with actual path if available from backend
              availability: 'Time here', // replace with actual time if available from backend
              address: spot.address,
            };
          }));

          withDistances.sort((a, b) => a.distance - b.distance);

          setResults(withDistances);
          console.log(results);
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  }, [location.search]); // re-run effect if search query changes

  if(loading) return <div>Loading...</div>;

  return (
    <div className="search-results-page">
      <h2>Search Results for {query}</h2> 
      <ul className="results-list">
        {results.map(result => (
        <li key={result.id} className="result-item">
          <img src={result.image} alt={result.address} className="result-image"/>
          <div className="result-info">
            <p className="result-address">{result.address}</p>
            <p>{result.availability}</p>
          </div>
          <Link to={`/listing/${result.listingId}`} className="details-button">See Details</Link> {/* Modified this line */}
        </li>
        ))}
      </ul>
    </div>
  );
};

export default SearchResults;
