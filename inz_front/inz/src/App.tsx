import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import PlannerPage from './pages/PlannerPage';
import LoginPage from './pages/LoginPage';
import Cookies from 'js-cookie';
import Statistics from './pages/Statistics'

const isAuthenticated = () => {
  const jwtToken = Cookies.get('jwtToken');
  return !!jwtToken;
};

function App() {
  return (
  <Router>
    <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route
          path="/PlannerPage"
          element={isAuthenticated() ? <PlannerPage /> : <Navigate to="/" />}
        />
        <Route path="/Statistics" element={isAuthenticated() ? <Statistics /> : <Navigate to="/" />} />
    </Routes  >
  </Router>
  );
}

export default App;
