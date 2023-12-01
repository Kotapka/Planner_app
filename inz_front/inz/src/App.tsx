import React from 'react';
import './App.css';
import Navbar from './components/Navbar'
import LoginFrom from './components/LoginForm';

function App() {
  return (
    <div className="App">
      <Navbar />
      <LoginFrom/>
    </div>
  );
}

export default App;
