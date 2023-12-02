import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PlannerPage from './pages/PlannerPage';
import LoginPage from './pages/LoginPage';


function App() {
  return (
  <Router>
    <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/PlannerPage" element={<PlannerPage  />} />
    </Routes  >
  </Router>
  );
}

export default App;
