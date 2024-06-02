import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Button, Form, Input } from 'antd';
import { useNavigate } from 'react-router-dom';
import styles from './CreateListing.module.css';
import PlacesAutocomplete, {
  geocodeByAddress,
  getLatLng,
} from 'react-places-autocomplete';

const CreateListing = () => {
  const nav = useNavigate();
  const [userLocation, setUserLocation] = useState(null);
  const [formData, setFormData] = useState({
      listing: {
          spotId: 1,
          lesseeId: 1,
          hourlyRate: 0,
          description: '',
          status: '',
      },
      parkingSpot: {
          spotId: null,
          address: '',
          spotNum: null,
          spotType: '',
          latitude: null,
          longitude: null,
      }
  });

  const handleChange = (e) => {
      const [mainProperty, subProperty] = e.target.name.split('.');
      if (subProperty == 'hourlyRate') {
        if (e.target.value < 0) {
          e.target.value = 0;
        } 
      }
      setFormData(prevState => ({
          ...prevState,
          [mainProperty]: {
              ...prevState[mainProperty],
              [subProperty]: e.target.value
          }
      }));
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setFormData({ ...formData, image: file });
  };



  const handleCreateListing = async () => {
    const token = localStorage.getItem('token');
    if (!token) {
        return;
    }
  
    const apiUrl = 'http://localhost:8080/parkit/listings/insertListing';
  
    let formDataObj = new FormData();
    formDataObj.append("listing", JSON.stringify(formData.listing));
    formDataObj.append("parkingSpot", JSON.stringify(formData.parkingSpot));
    
    // Add the image
    if (formData.image) {
      formDataObj.append("parkingSpotImage", formData.image);
    }
  
    const requestOptions = {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`
        },
        body: formDataObj
    };

    try {
        const response = await fetch(apiUrl, requestOptions);
        const data = await response.json();
        console.log(data); // you'll see the result message here
        
        if(response.ok) {
            // Handle success, e.g., reset the form or notify the user
            setFormData({
              spotId: 1,
              lesseeId: 1,
              hourlyRate: 0,
              description: '',
              status: '',
            });
        } else {
            // Handle error, e.g., notify the user
            console.error(data.message);
        }
    } catch (error) {
        console.error('There was an error:', error);
    }
  };

  const handleAddressChange = (address) => {
    setFormData(prevState => ({
        ...prevState,
        parkingSpot: {
            ...prevState.parkingSpot,
            address
        }
    }));
  };

  const handleAddressSelect = (address) => {
    geocodeByAddress(address)
        .then(results => getLatLng(results[0]))
        .then(({ lat, lng }) => {
            setFormData(prevState => ({
                ...prevState,
                parkingSpot: {
                    ...prevState.parkingSpot,
                    address,
                    latitude: lat,
                    longitude: lng
                }
            }));
        })
        .catch(error => console.error('Error fetching lat/lng', error));
  };

  
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form submitted with data:', formData);
    handleCreateListing();  // call the function to make the API call
    nav('/dashboard');
  };

  useEffect(() => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
            setUserLocation({
                lat: position.coords.latitude,
                lng: position.coords.longitude
            });
        });
    }
  }, []);


  return (
    <div className={styles.createListingContainer}>
        <AnimatePresence>
            <motion.div className={styles.modal} initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}>
                <motion.div className={styles['modal-content']} initial={{ y: -50, opacity: 0 }} animate={{ y: 0, opacity: 1 }} exit={{ y: -50, opacity: 0 }}>
                    <h2 className={styles.title}>Create Parking Spot Listing</h2>
                    <form onSubmit={handleSubmit}>
                        {/* ParkingSpot Input Fields */}
                        <label htmlFor="parkingSpot.address">Location Details (Address)</label>
                        <PlacesAutocomplete
                            value={formData.parkingSpot.address}
                            onChange={handleAddressChange}
                            onSelect={handleAddressSelect}
                            searchOptions={
                              userLocation && {
                                location: new google.maps.LatLng(userLocation.lat, userLocation.lng),
                                radius: 2000 
                            }}
                          >

                            {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
                                <div>
                                    <input
                                        {...getInputProps({
                                            placeholder: 'Search Places ...',
                                            className: styles.input,
                                        })}
                                    />
                                    <div>
                                        {loading && <div>Loading...</div>}
                                        {suggestions.map(suggestion => (
                                            <div
                                                {...getSuggestionItemProps(suggestion, {
                                                    className: styles.suggestionItem,
                                                })}
                                            >
                                                {suggestion.description}
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}
                        </PlacesAutocomplete>

                        <label htmlFor="parkingSpot.spotType">Spot Type (e.g., Covered, Garage)</label>
                        <input type="text" name="parkingSpot.spotType" value={formData.parkingSpot.spotType} onChange={handleChange} required className={styles.input} />

                        <label htmlFor="parkingSpot.latitude">Latitude</label>
                        <input type="number" step="0.0001" name="parkingSpot.latitude" readOnly value={formData.parkingSpot.latitude} className={styles.input} />

                        <label htmlFor="parkingSpot.longitude">Longitude</label>
                        <input type="number" step="0.0001" name="parkingSpot.longitude" readOnly value={formData.parkingSpot.longitude} className={styles.input} />

                        {/* Listing Input Fields */}
                        <label htmlFor="listing.hourlyRate">Hourly Rate ($/hour)</label>
                        <input type="number" name="listing.hourlyRate" value={formData.listing.hourlyRate} onChange={handleChange} required className={styles.input} />

                        <label htmlFor="listing.description">Description</label>
                        <textarea name="listing.description" value={formData.listing.description} onChange={handleChange} rows="4" required className={styles.input}></textarea>

                        <label htmlFor="listing.status">Availability</label>
                        <select name="listing.status" value={formData.listing.status} onChange={handleChange} required className={styles.input}>
                            <option value="">Select Availability</option>
                            <option value="Available">Available</option>
                            <option value="Unavailable">Unavailable</option>
                        </select>

                        {/* File input for parking spot image if you wish to add that */}
                        <label htmlFor="parkingSpotImage">Upload Parking Spot Image</label>
                        <input type="file" name="parkingSpotImage" accept="image/*" onChange={handleImageChange} className={styles.input} />

                        <button type="submit" className={styles.button}>Submit</button>
                    </form>
                </motion.div>
            </motion.div>
        </AnimatePresence>
    </div>
  );
};


export default CreateListing;
