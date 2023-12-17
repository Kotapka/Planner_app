// AssignedTaskForm.tsx
import React, { useState, useEffect } from 'react';
import styles from './AssignedTaskForm.module.css';
import Cookies from 'js-cookie';

interface Category {
  id: number;
  name: string;
  user: string;
}

interface AssignedTaskFormProps {
  onClose: () => void;
}

const AssignedTaskForm: React.FC<AssignedTaskFormProps> = ({ onClose }) => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<number | null>(null);
  const [startDate, setStartDate] = useState<string>('');
  const [endDate, setEndDate] = useState<string>('');
  const [error, setError] = useState<string>('');
  const storedUserLogin = Cookies.get('Login');

  useEffect(() => {
    // Wywołaj endpoint /api/user/getCategories, gdy komponent jest zamontowany
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
  }, []); // Pusta zależność oznacza, że useEffect zostanie uruchomiony tylko raz po zamontowaniu komponentu

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    // Sprawdź, czy wybrano kategorię przed akceptacją formularza
    if (selectedCategory === null || startDate === '' || endDate === '') {
      setError('Wypełnij wszystkie pola przed dodaniem zadania.'); // Ustaw błąd, jeśli nie wszystkie pola są wypełnione
      return;
    }

    // Sprawdź, czy startDate nie jest ustawione na później niż endDate
    const startDateTime = new Date(`2000-01-01T${startDate}`);
    const endDateTime = new Date(`2000-01-01T${endDate}`);

    if (startDateTime.getTime() > endDateTime.getTime()) {
      setError('Data rozpoczęcia nie może być późniejsza niż data zakończenia.'); // Ustaw błąd, jeśli startDate > endDate
      return;
    }

    // Dodaj kod obsługujący przesłanie formularza (np. wysłanie danych do serwera)

    // Zamknij formularz po przesłaniu
    onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.assignedTaskForm}>
        <h3>Assign Task</h3>
        <form onSubmit={handleSubmit}>
          <label>
            Category:
            <select
              value={selectedCategory !== null ? selectedCategory : ''}
              onChange={(e) => setSelectedCategory(Number(e.target.value))}
              className={styles.selectField}
            >
              <option value="" disabled>Select a category</option>
              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.name}
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
          {error && <div className={styles.error}>{error}</div>}
          <button type="submit" className={styles.addButton} disabled={selectedCategory === null || startDate === '' || endDate === ''}>
            Assign
          </button>
        </form>
        <button onClick={() => { onClose(); setError(''); }} className={styles.closeButton}>Close</button>
      </div>
    </div>
  );
};

export default AssignedTaskForm;
