// TaskForm.tsx
import React, { useState } from 'react';
import styles from './TaskForm.module.css';

interface TaskFormProps {
  onClose: () => void;
}

const TaskForm: React.FC<TaskFormProps> = ({ onClose }) => {
  const [categoryName, setCategoryName] = useState('');
  const [taskName, setTaskName] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    // Sprawdź, czy pola są niepuste przed akceptacją formularza
    if (categoryName.trim() === '' || taskName.trim() === '') {
      alert('Wypełnij oba pola przed dodaniem zadania.'); // Możesz dostosować komunikat
      return;
    }

    // Dodaj kod obsługujący przesłanie formularza (np. wysłanie danych do serwera)

    // Zamknij formularz po przesłaniu
    onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.taskForm}>
        <h3>Add Task</h3>
        <form onSubmit={handleSubmit}>
          <label>
            Category:
            <input
              type="text"
              value={categoryName}
              onChange={(e) => setCategoryName(e.target.value)}
              className={styles.inputField}
            />
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
          <button type="submit" className={styles.addButton} disabled={categoryName.trim() === '' || taskName.trim() === ''}>
            Add
          </button>
        </form>
        <button onClick={onClose} className={styles.closeButton}>Close</button>
      </div>
    </div>
  );
};

export default TaskForm;
