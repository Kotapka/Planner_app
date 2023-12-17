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
import EditAssignedTaskForm from '../components/EdditAssignedTaskForm';

const localizer = momentLocalizer(moment);

function PlannerPage() {
  const navigate = useNavigate();
  const [showCategoryForm, setShowCategoryForm] = useState(false);
  const [showTaskForm, setShowTaskForm] = useState(false);
  const [showAssignedTaskForm, setAssignedShowTaskForm] = useState(false);
  const [showEdditAssignedTaskForm, setShowEdditAssignedTaskForm] = useState(false);
  const [assignedTasks, setAssignedTasks] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [selectedTaskId, setSelectedTaskId] = useState(null);
  const [tasksAndCategories, setTasksAndCategories] = useState([]);

  useEffect(() => {
    // Load assigned tasks and categories when the component mounts
    loadAssignedTasks();
    loadTasksAndCategories();
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
          active: true,
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
    const activeTasks = tasks.filter(task => task.active);

    return activeTasks.map((task) => ({
      id: task.id,
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

  const fetchTasks = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/user/getTasks', {
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
      console.error('Error fetching tasks:', error.message);
      // Handle error fetching data
    }
  };

  const loadTasksAndCategories = async () => {
    try {
      const tasks = await fetchTasks();
      setTasksAndCategories(tasks);
    } catch (error) {
      // Handle errors
    }
  };

  const handleLogout = () => {
    try {
      const token = Cookies.get('jwtToken');
      Cookies.remove('jwtToken');
      Cookies.remove('Login');
      console.log("About to navigate")
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

  const handleEventClick = (event) => {
    setShowEdditAssignedTaskForm(true);
    setSelectedTaskId(event.id);
  };

  return (
    <div className={styles['planner-container']}>
      <div className={styles['side-panel']}>
        <h2>PlannerApp</h2>
        <div>
          <ul>
            {tasksAndCategories.map((item, index) => (
              <li key={index}>
                Task: {item.name}, Category: {item.category}
              </li>
            ))}
          </ul>
        </div>
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
          onSelectEvent={handleEventClick}
        />
      </div>
      {showCategoryForm && <CategoryForm onClose={() => setShowCategoryForm(false)} />}
      {showTaskForm && <TaskForm onClose={() => setShowTaskForm(false)} />}
      {showEdditAssignedTaskForm && <EditAssignedTaskForm taskId={selectedTaskId} onClose={() => setShowEdditAssignedTaskForm(false)} />}
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