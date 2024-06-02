import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../components/AuthContext';
import '../static/css/signin.css';
import axios from 'axios';

const SignIn = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);

  const navigate = useNavigate();

  const { signIn } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
        const response = await axios.post('http://localhost:8080/parkit/user/login', {
            username: username,
            password: password
        });

        if (response.data && response.data.token) {
            if (rememberMe) {
                localStorage.setItem('username', username);
            }
            signIn(response.data.token);
            navigate('/dashboard');
            alert("Login success!");
        } else {
            console.error("Token not found in response.");
        }
    } catch (error) {
        if (error.response && error.response.data) {
            console.error("Error logging in:", error.response.data);
            alert("Login Failed");
        } else {
            console.error("Error logging in:", error);
            alert("Login Failed");
        }
    }
  };

  return (
    <div className="signin-container">
      <form onSubmit={handleSubmit} className="signin-form">
        <h2>Sign In</h2>

        <label className="form-label" htmlFor="username">Username</label>
        <input
          type="username"
          id="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <label className="form-label" htmlFor="password">Password</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <div className="remember-me-container">
          <label className="remember-me-label" htmlFor="rememberMe">
            Remember Me
          </label>
          <input
            type="checkbox"
            id="rememberMe"
            className="remember-me-checkbox"
            checked={rememberMe}
            onChange={(e) => setRememberMe(e.target.checked)}
          />
          
        </div>


        <Link to="/forgot-password" className="forgot-password-link">Forgot Password?</Link>

        <button type="submit" className="signin-button">Sign In</button>
        <div className="alternate-auth">
          <p>Don't have an account? <Link to="/signup">Sign Up</Link></p>
        </div>
      </form>
    </div>
  );
};

export default SignIn;
