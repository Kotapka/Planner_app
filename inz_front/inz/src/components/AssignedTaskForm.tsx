import React, { useState, useEffect } from 'react';
import styles from './AssignedTaskForm.module.css';
import Cookies from 'js-cookie';

interface Category {
  name: string;
  user: string;
}

interface Task {
  name: string;
  category: string;
  user: string;
}

interface AssignedTaskFormProps {
  selectedDate: string;
  onClose: () => void;
}

const AssignedTaskForm: React.FC<AssignedTaskFormProps> = ({ selectedDate, onClose }) => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [tasks, setTasks] = useState<Task[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>('');
  const [selectedTask, setSelectedTask] = useState<string>('');
  const [startDate, setStartDate] = useState<string>('');
  const [endDate, setEndDate] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [error, setError] = useState<string>('');
  const storedUserLogin = Cookies.get('Login');

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/user/getCategories', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            login: storedUserLogin,
          }),
        });

        if (response.ok) {
          const data = await response.json();
          setCategories(data);
        } else {
          console.error('Failed to fetch categories');
        }
      } catch (error) {
        console.error('Error: Failed to fetch categories');
      }
    };

    fetchCategories();
  }, [storedUserLogin]);

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        if (selectedCategory !== '') {
          const response = await fetch('http://localhost:8080/api/user/getTasksByCategory', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              login: storedUserLogin,
              category: selectedCategory,
            }),
          });

          if (response.ok) {
            const data = await response.json();
            setTasks(data);
          } else {
            console.error('Failed to fetch tasks');
          }
        }
      } catch (error) {
        console.error('Error: Failed to fetch tasks');
      }
    };

    fetchTasks();
  }, [selectedCategory, storedUserLogin]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (selectedTask === '' || selectedCategory === '' || selectedDate === '' || startDate === '' || endDate === '') {
      setError('Wypełnij wszystkie pola przed dodaniem zadania.');
      return;
    }
    let newStartDate = new Date(selectedDate)
    // Pobierz składniki daty
    let year = newStartDate.getFullYear();
    let month = String(newStartDate.getMonth() + 1).padStart(2, '0');
    let day = String(newStartDate.getDate()).padStart(2, '0');

    // Utwórz sformatowaną datę
    let formattedStartDate = `${year}-${month}-${day}`;

    const startDateTime = (`${formattedStartDate}T${startDate}:00.000Z`);
    const endDateTime = (`${formattedStartDate}T${endDate}:00.000Z`);

    try {
      const response = await fetch('http://localhost:8080/api/saveAssignedTask', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          startDate: startDateTime,
          endDate: endDateTime,
          description,
          category: selectedCategory,
          user: storedUserLogin,
          task: selectedTask,
          active: true,
        }),
      });

      if (response.ok) {
        console.log('Task assigned successfully');
      } else {
        console.error('Failed to save assigned task');
      }
    } catch (error) {
      console.error('Error: Failed to save assigned task',error);
    }
    onClose();
    window.location.reload()
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.assignedTaskForm}>
        <h3>Assign Task</h3>
        <form onSubmit={handleSubmit}>
          <label>
            Category:
            <select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
              className={styles.selectField}
            >
              <option value="" disabled>Select a category</option>
              {categories.map((category) => (
                <option key={category.name} value={category.name}>
                  {category.name}
                </option>
              ))}
            </select>
          </label>
          <label>
            Task:
            <select
              value={selectedTask}
              onChange={(e) => setSelectedTask(e.target.value)}
              className={styles.selectField}
            >
              <option value="" disabled>Select a task</option>
              {tasks.map((task) => (
                <option key={task.name} value={task.name}>
                  {task.name}
                </option>
              ))}
            </select>
          </label>
          <label>
            Start Date:
            <input
              type="time"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              className={styles.inputField}
            />
          </label>
          <label>
            End Date:
            <input
              type="time"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              className={styles.inputField}
            />
          </label>
          <label>
            Description:
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className={styles.textareaField}
            />
          </label>
          {error && <div className={styles.error}>{error}</div>}
          <button
            type="submit"
            className={styles.addButton}
            disabled={selectedTask === '' || selectedCategory === '' || selectedDate === '' || startDate === '' || endDate === ''}
          >
            Assign
          </button>
        </form>
        <button onClick={() => { onClose(); setError(''); }} className={styles.closeButton}>Close</button>
      </div>
    </div>
  );
};

export default AssignedTaskForm;
