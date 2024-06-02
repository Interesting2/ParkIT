import React, { useState } from 'react';
import '../static/css/signup.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function SignUp() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    verificationCode: '',
    phoneNumber: ''
  });

  const [isRegistered, setIsRegistered] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      console.error("Passwords do not match!");
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/parkit/user/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 
          username: formData.username,
          password: formData.password,
          contactEmail: formData.email,
          contactPhoneNumber: formData.phoneNumber
         })
      });

      const data = await response.text();
      console.log(data);

      if (response.ok) {
        alert("Signed Up Successfully");
        setIsRegistered(true)
      } else {
        alert("Signed Up Failed");
      }


    } catch (error) {
      console.log(error)

    }
  };
 

  const handleVerification = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/parkit/user/registerVerify', {
        userName: formData.username,
        code: formData.verificationCode
      });

      console.log(response.data);
      if (response.data === "Registration completed! ") {
        navigate('/dashboard')
      }
    } catch (error) {
      console.error("Error verifying:", error.response.data);
    }
  };

  return (
    <div className="signup-container">
      <form className="signup-form" onSubmit={isRegistered ? (e) => handleVerification(e) : handleSubmit}>
        <h2>{isRegistered ? "Verify Account" : "Sign Up"}</h2>

        {!isRegistered && (
          <>
            <label className="form-label">Username:
              <input type="text" name="username" value={formData.username} onChange={handleChange} required />
            </label>
            <label className="form-label">Email:
              <input type="email" name="email" value={formData.email} onChange={handleChange} required />
            </label>
            <label className="form-label">Contact Number:
              <input type="tel" name="contact" value={formData.contactNumber} onChange={handleChange} required />
            </label>
            <label className="form-label">Password:
              <input type="password" name="password" value={formData.password} onChange={handleChange} required />
            </label>
            <label className="form-label">Confirm Password:
              <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} required />
            </label>
            <button className="signup-button" type="submit">Sign Up</button>
          </>
        )}

        {isRegistered && (
          <>
            <label className="form-label">Verification Code:
              <input type="text" name="verificationCode" value={formData.verificationCode} onChange={handleChange} required />
            </label>
            <button className="verify-button" type="submit">Verify</button>
          </>
        )}
        
      </form>
    </div>
  );
}

export default SignUp;
