import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../static/css/navbar.css';
import { useAuth } from './AuthContext';

function Navbar() {
  const navigate = useNavigate();

  const { isAuthenticated, signOut } = useAuth();

  const handleSignOut = () => {
    signOut();
    navigate('/');
  };

  return (
    <nav className="navbar">
      <Link to="/">
        <img src="/logo.png" alt="Logo" className="logo" />
      </Link>
      <div className="navbar-buttons">
        { isAuthenticated ? (
          <>
            <Link to="/dashboard" className="button">Dashboard</Link>
            <Link to="/account" className="button">Account</Link>
            <button onClick={handleSignOut} className="button">Sign Out</button> 
          </>
        ) : (
          <>
            <Link to="/signin" className="button">Sign In</Link>
            <Link to="/signup" className="button">Sign Up</Link>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
