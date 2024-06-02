import React from 'react';
import '../static/css/landingpage.css';

function LandingPage() {
  return (
    <div className="landing-page">
      <div className="content-container">
        <div className="intro-text">
          <p className="title">ParkIt</p>
          <p className="subtitle">Australia’s First Peer Based Parking Solution</p>
          <p className="cta"><a href='/signup'>Create A Free Account To Start</a></p>
        </div>
      </div>
    </div>
  );
}


export default LandingPage;
