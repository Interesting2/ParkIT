import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { LoadScript } from '@react-google-maps/api';
import Navbar from './components/Navbar';
import LandingPage from './pages/LandingPage';
import SignIn from './pages/Signin';
import SignUp from './pages/Signup';
import ForgotPassword from './pages/ForgotPassword';
import Dashboard from './pages/Dashboard';
import SearchResults from './pages/SearchResults';
import Listing from './pages/Listing';
import Checkout from './pages/Checkout';
import BookingConfirmation from './pages/BookingConfirmation';
import { AuthProvider, useAuth } from './components/AuthContext';
import CreateListing from './components/CreateListing';
import Account from './pages/Account'

function AppRoutes() {
  const { isAuthenticated } = useAuth();

  return (
    <>
      <Navbar />
      <Routes>
        
        <Route path="/" element={isAuthenticated ? <Dashboard /> : <LandingPage />} />
        <Route path="/signin" element={isAuthenticated ? <Dashboard /> : <SignIn />} />
        <Route path="/signup" element={isAuthenticated ? <Dashboard /> : <SignUp />} />
        <Route path="/account" element={isAuthenticated ? <Account /> : <SignIn />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/dashboard" element={isAuthenticated ? <Dashboard /> : <Navigate to="/signin" />} />
        <Route path="/search" element={isAuthenticated ? <SearchResults /> : <Navigate to="/signin" />} />
        <Route path="/create-listing" element={isAuthenticated ? <CreateListing /> : <Navigate to="/signin" />} />
        <Route path="/listing/:id" element={isAuthenticated ? <Listing /> : <Navigate to="/signin" />} />
        <Route path="/checkout/:id" element={isAuthenticated ? <Checkout /> : <Navigate to="/signin" />} />
        <Route path="/confirmation/:id" element={isAuthenticated ? <BookingConfirmation /> : <Navigate to="/signin" />} />
      </Routes>
    </>
  );
}

function App() {
  return (
    <Router>
      <AuthProvider>
        <LoadScript googleMapsApiKey="AIzaSyD13REi0g2MIt52eB4glnDHB44OqLC9ND8" libraries={['places']}>
          <AppRoutes />
        </LoadScript>
      </AuthProvider>
    </Router>
  );
}

export default App;
