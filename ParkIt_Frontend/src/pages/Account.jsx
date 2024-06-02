
import { useEffect, React, useState } from 'react'
import ProfileCard from '../components/ProfileCard';
import ProfileForm from '../components/ProfileForm';
import { motion, AnimatePresence } from 'framer-motion';

const Account = () => {

    const [user, setUser] = useState({});
    const [vehicles, setVehicles] = useState([{
        id: 1,
        name: "Vehicle 1",
        model: "Tesla Model S",
        regoNumber: "ABC123456",
        transmission: "Automatic",
        year: "2001",
      },
      {
        id: 2,
        name: "Vehicle 2",
        model: "Honda Civic",
        regoNumber: "XYZ7890",
        transmission: "Manual",
        year: "2015",
      },]);
    const getUserAccount = async () => {

        const token = localStorage.getItem('token'); 
            if (!token) {
                return; 
            }
            
            try {
                const response = await fetch('http://localhost:8080/parkit/user/getUserById', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });
                const data = await response.json();
                // console.log(data)
                if(response.status === 200) {
                    setUser(data);
                    console.log(user)
                } else {
                    console.error('Unexpected response format:', data);
                }
            } catch (error) {
                console.error('Error fetching bookings:', error);
            }
        // axios.get('http://localhost:8080/parkit/user/show', {
        //     withCredentials: true,
        // })
        // .then((response) => {
        //     // Handle the successful response here
        //     console.log('Data:', response.data);
        // })
        // .catch((error) => {
        //     // Handle any errors that occur during the request
        //     console.error('Error:', error);
        // });

    }

    const getVehicleInfo = async () => {
        const token = localStorage.getItem('token'); 
        if (!token) {
            return; 
        }
        
        try {
            const response = await fetch('http://localhost:8080/parkit/user/getCarsByUserId', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            const data = await response.json();
            // console.log(data)
            if(response.status === 200) {
                setVehicles(data);
                console.log(vehicles)
            } else {
                console.error('Unexpected response format:', data);
            }
        } catch (error) {
            console.error('Error fetching bookings:', error);
        } 
    }

    const getParkingSpotInfo = () => {
        
    }

    useEffect(() => {
        getUserAccount();
    }, [])

  return (
    <>
    <AnimatePresence>
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
        >
            <motion.div
            initial={{ y: -50, opacity: 0 }}
            animate={{ y: 0, opacity: 1 }}
            exit={{ y: -50, opacity: 0 }}
            >
            

            <h2 className="text-3xl font-semibold text-gray-800 mt-4">My Account</h2>

                        {/* Location Details: Street address, city, state, ZIP code.
            Parking Type: Covered, uncovered, garage, driveway, etc.
            Pricing: Daily, weekly, monthly rates.
            Availability: Days and times when the spot is available.
            Description: Any additional details about the parking spot.
            Photos: Allow users to upload pictures of the spot.
            Contact Information: Email, phone number (if needed). */}
          
            <form onSubmit={()=>{}}>
              <div className="flex flex-wrap flex-row">
                <div className="w-full lg:w-8/12 px-4">
                  <ProfileForm user={user} vehicles={vehicles} />
                  
    
                </div>
                <div className="w-full lg:w-4/12 px-4">
                  <ProfileCard user={user} count={vehicles.length}/>
                </div>

                
              </div>

            

            </form>
            
            </motion.div>
          </motion.div>
      </AnimatePresence>
    
    </>
  )
}

export default Account