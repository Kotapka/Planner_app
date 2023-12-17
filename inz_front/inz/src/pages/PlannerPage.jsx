// PlannerPage.tsx
import React, { useState, useEffect } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import moment from 'moment';
import styles from './PlannerPage.module.css';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import CategoryForm from '../components/CategoryForm';
import TaskForm from '../components/TaskForm';
import AssignedTaskForm from '../components/AssignedTaskForm';

const localizer = momentLocalizer(moment);

function PlannerPage() {
  const navigate = useNavigate();
  const [showCategoryForm, setShowCategoryForm] = useState(false);
  const [showTaskForm, setShowTaskForm] = useState(false);
  const [showAssignedTaskForm, setAssignedShowTaskForm] = useState(false);
  const [assignedTasks, setAssignedTasks] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);

  useEffect(() => {
    // Load assigned tasks when component mounts
    loadAssignedTasks();
  }, []);

  const fetchAssignedTasks = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/user/getAssignedTask', {  
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          login: Cookies.get('Login'),
        }),
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      return data;
    } catch (error) {
      console.error('Error fetching assigned tasks:', error.message);
      // Handle error fetching data
    }
  };

  const convertToCalendarEvents = (tasks) => {
    return tasks.map((task) => ({
      start: new Date(task.startDate),
      end: new Date(task.endDate),
      title: task.task,
    }));
  };

  const loadAssignedTasks = async () => {
    try {
      const tasks = await fetchAssignedTasks();
      const calendarEvents = convertToCalendarEvents(tasks);
      setAssignedTasks(calendarEvents);
    } catch (error) {
      // Handle errors
    }
  };

  const handleLogout = () => {
    try {
      const token = Cookies.get('jwtToken');
      // Send a request to the server to deactivate the token (optional, depending on the server implementation)
      // ...
      Cookies.remove('jwtToken');
      Cookies.remove('Logout');
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

  const handleDateClick = (event) => {
    const clickedDate = event.start;
    setSelectedDate(clickedDate);
    setAssignedShowTaskForm(true);
  };

  return (
    <div className={styles['planner-container']}>
      <div className={styles['side-panel']}>
        <h2>PlannerApp</h2>
        <p>Treść panelu bocznego...</p>
        <div className={styles['action-buttons-container']}>
          <button className={styles['action-button']} onClick={handleAddCategoryClick}>
            Add Category
          </button>
          <button className={styles['action-button']} onClick={handleAddTaskClick}>
            Add Task
          </button>
          <button onClick={handleLogout} className={styles['logout-button']}>
            Logout
          </button>
        </div>
      </div>
      <div className={`${styles['full-page-calendar']} ${showCategoryForm && styles['calendar-disabled']}`}>
        <Calendar
          localizer={localizer}
          startAccessor="start"
          endAccessor="end"
          style={{ flex: 1, height: '100%' }}
          selectable={true}
          onSelectSlot={handleDateClick}
          events={assignedTasks}
        />
      </div>
      {showCategoryForm && <CategoryForm onClose={() => setShowCategoryForm(false)} />}
      {showTaskForm && <TaskForm onClose={() => setShowTaskForm(false)} />}
      {showAssignedTaskForm && selectedDate && (
        <AssignedTaskForm
          selectedDate={selectedDate}
          onClose={() => {
            setAssignedShowTaskForm(false);
            setSelectedDate(null);
          }}
        />
      )}
    </div>
  );
}

export default PlannerPage;
