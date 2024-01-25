import React, { useState, useEffect } from 'react';
import styles from './TaskForm.module.css';
import Cookies from 'js-cookie';

interface TaskFormProps {
  onClose: () => void;
}

interface Category {
  name: string;
  user: string;
}

const TaskForm: React.FC<TaskFormProps> = ({ onClose }) => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>('');
  const [taskName, setTaskName] = useState('');
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
  
          const isCurrentCategoryValid = data.some((category: Category) => category.name === selectedCategory);
  
          if (!isCurrentCategoryValid && data.length > 0) {
            setSelectedCategory(data[0].name);
          }
        } else {
          console.error('Failed to fetch categories');
        }
      } catch (error) {
        console.error('Error: Failed to fetch categories');
      }
    };

    fetchCategories();
  }, [storedUserLogin, selectedCategory]);

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const value = e.target.value === '' ? '' : e.target.value;
    setSelectedCategory(value);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (selectedCategory === '' || taskName.trim() === '') {
      setError('Wypełnij wszystkie pola przed dodaniem zadania.');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/addTask', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: taskName,
          category: selectedCategory,
          user: storedUserLogin,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.error('Server error:', errorData);
        setError('Something went wrong');
      } else {
        onClose();
      }
    } catch (error) {
      console.error('Network error or unexpected error:', error);
      setError('Something went wrong');
    }
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.taskForm}>
        <h3>Add Task</h3>
        <form onSubmit={handleSubmit}>
          <label>
            Category:
            <select
              value={selectedCategory}
              onChange={handleCategoryChange}
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
            <input
              type="text"
              value={taskName}
              onChange={(e) => setTaskName(e.target.value)}
              className={styles.inputField}
            />
          </label>
          {error && <div className={styles.error}>{error}</div>}
          <button
            type="submit"
            className={styles.addButton}
            disabled={selectedCategory === '' || taskName.trim() === ''}
          >
            Add
          </button>
        </form>
        <button onClick={() => { onClose(); setError(''); }} className={styles.closeButton}>
          Close
        </button>
      </div>
    </div>
  );
};

export default TaskForm;
