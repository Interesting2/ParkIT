import React from 'react';
import SearchWidget from '../components/SearchWidget';
import BookingsWidget from '../components/BookingsWidget';
import ListingsWidget from '../components/ListingsWidget';
import '../static/css/dashboard.css'

const Dashboard = () => {
  return (
    <div className="dashboard-container">
      <SearchWidget />
      <div className="side-widgets">
        <BookingsWidget />
        <ListingsWidget />
      </div>
    </div>
  );
};

export default Dashboard;
