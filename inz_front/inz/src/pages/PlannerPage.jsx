import React from 'react';
import { Link } from 'react-router-dom';

function PlannerPage(){
  return (
    <div>
      <h1>Strona Główna</h1>
      <Link to="/login">Przejdź do strony logowania</Link>
    </div>
  );
};

export default PlannerPage;