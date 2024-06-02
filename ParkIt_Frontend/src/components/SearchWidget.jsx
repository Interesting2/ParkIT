import React, { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleMap, Marker, StandaloneSearchBox } from '@react-google-maps/api';
import { Button } from 'react-bootstrap';

const SearchWidget = () => {
  const [address, setAddress] = useState('');
  const [location, setLocation] = useState({ lat: -33.8886, lng: 151.1873 }); // Default to USYD

  const navigate = useNavigate();
  const searchBox = useRef(null);

  const handleSearchClick = () => {
    navigate(`/search?address=${address}`);
  };

  const handleAddressChange = (e) => {
    setAddress(e.target.value);
  }

  const onLoad = (ref) => {
    searchBox.current = ref;
  }

  const onPlacesChanged = () => {
    const place = searchBox.current.getPlaces()[0];
    console.log(place)
    const location = place.geometry.location;
    setAddress(place.formatted_address);
    setLocation({ lat: location.lat(), lng: location.lng() });
  }

  return (
    <div className="widget-container search-widget">
      <h2 className="widget-header">Search Parking</h2>
      <StandaloneSearchBox onLoad={onLoad} onPlacesChanged={onPlacesChanged}>
        <input
          type="text"
          value={address}
          onChange={handleAddressChange}
          placeholder="Search address..."
          className="search-input"
        />
      </StandaloneSearchBox>
      <Button onClick={handleSearchClick} className="search-button">Search</Button>
      <GoogleMap
        mapContainerStyle={{ width: '100%', height: '400px' }}
        center={location}
        zoom={10}
      >
        <Marker position={location} />
      </GoogleMap>
    </div>
  );
};

export default SearchWidget;
