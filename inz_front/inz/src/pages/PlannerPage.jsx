// PlannerPage.tsx
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import moment from 'moment';
import styles from './PlannerPage.module.css'; // Zaimportuj lokalne style
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import CategoryForm from '../components/CategoryForm'; // Importuj CategoryForm
import TaskForm from '../components/TaskForm'; // Importuj TaskForm


const localizer = momentLocalizer(moment);

function PlannerPage() {
  const navigate = useNavigate();
  const [showCategoryForm, setShowCategoryForm] = useState(false);
  const [showTaskForm, setShowTaskForm] = useState(false);


  const handleLogout = () => {
    try {
      const token = Cookies.get('jwtToken');
  
      // Wyślij żądanie do serwera, aby zdezaktywować token (opcjonalne, w zależności od implementacji serwera)
      // ...
  
      Cookies.remove('jwtToken');
      navigate('/');
  
    } catch (error) {
      console.error('Error:', error.message);
    }
  };

  const handleAddCategoryClick = () => {
    setShowCategoryForm(true);
  };
  const handleAddTaskClick = () => {
    setShowTaskForm(true);
  };

  return (
    <div className={styles['planner-container']}>
      {/* Panel boczny zajmujący 15% szerokości strony */}
      <div className={styles['side-panel']}>
        {/* Tutaj możesz umieścić zawartość panelu bocznego */}
        <h2>PlannerApp</h2>
        <p>Treść panelu bocznego...</p>
        <div className={styles['action-buttons-container']}>
          <button className={styles['action-button']} onClick={handleAddCategoryClick}>Add Category</button>
          <button className={styles['action-button']} onClick={handleAddTaskClick}>Add Task</button>
          <button onClick={handleLogout} className={styles['logout-button']}>Logout</button>
        </div>
      </div>

      {/* Kalendarz zajmujący resztę dostępnej przestrzeni */}
      <div className={`${styles['full-page-calendar']} ${showCategoryForm && styles['calendar-disabled']}`}>
      <Calendar
        localizer={localizer}
        startAccessor="start"
        endAccessor="end"
        style={{ flex: 1, height: '100%' }}
      />
    </div>

      {/* Dodaj CategoryForm, jeśli showCategoryForm jest true */}
      {showCategoryForm && <CategoryForm onClose={() => setShowCategoryForm(false)} />}
      {showTaskForm && <TaskForm onClose={() => setShowTaskForm(false)} />}
    </div>
  );
}

export default PlannerPage;
