import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../static/css/forgotpassword.css';

const ForgotPassword = () => {
  const [username, setUsername] = useState('');
  const [code, setCode] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [message, setMessage] = useState('');
  const [step, setStep] = useState(1); // 1 for username submission, 2 for verification code input

  const handleEmailSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/parkit/user/forgotPassword', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username })
      });

      const data = await response.text();
      setMessage(data);
      setStep(2);
    } catch (error) {
      console.log(error)
      setMessage('Error sending reset link.');
    }
  };

  const handleVerificationSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/parkit/user/resetPassword', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userName: username,
          code: code,
          newPass: newPassword
        })
      });

      const data = await response.text();
      setMessage(data || 'Password reset successfully!');
    } catch (error) {
      setMessage('Error during verification.');
    }
  };

  if (step === 1) {
    return (
      <div className="forgotpassword-container">
        <form onSubmit={handleEmailSubmit} className="forgotpassword-form">
          <h2>Reset Password</h2>

          <label className="form-label" htmlFor="username">Username</label>
          <input
            type="username"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          
          <button type="submit" className="forgotpassword-button">Send Reset Link</button>

          {/* Display message */}
          {message && <div className="message">{message}</div>}

          <div className="back-to-signin">
            <Link to="/signin">Back to Sign In</Link>
          </div>
        </form>
      </div>
    );
  } else if (step === 2) {
    return (
      <div className="forgotpassword-container">
        <form onSubmit={handleVerificationSubmit} className="forgotpassword-form">
          <h2>Enter Verification Code</h2>

          <label htmlFor="code">Verification Code</label>
          <input
            type="text"
            id="code"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            required
          />

          <label htmlFor="newPassword">New Password</label>
          <input
            type="password"
            id="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />

          <button type="submit" className="forgotpassword-button">Reset Password</button>

          {message && <div className="message">{message}</div>}
          
          <div className="back-to-signin">
            <Link to="/signin">Back to Sign In</Link>
          </div>
        </form>
      </div>
    );
  }
};

export default ForgotPassword;
