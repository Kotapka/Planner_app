// Importuj odpowiednie pakiety
import React, { useState } from 'react';
import styles from './CategoryForm.module.css';

interface CategoryFormProps {
  onClose: () => void;
}

const CategoryForm: React.FC<CategoryFormProps> = ({ onClose }) => {
  const [categoryName, setCategoryName] = useState('');
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      // Wysyłanie żądania do serwera
      const response = await fetch('http://localhost:8080/api/addCategory', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name: categoryName }),
      });

      if (!response.ok) {
        // Jeżeli odpowiedź serwera nie jest OK, obsłuż błąd
        const errorData = await response.json();
        setError(errorData.message || 'Something went wrong');
      } else {
        // Jeżeli wszystko poszło pomyślnie, zamknij formularz
        onClose();
      }
    } catch (error) {
      // Obsługa błędów sieciowych lub innych nieoczekiwanych błędów
      setError('Something went wrong');
    }
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.categoryForm}>
        <h3>Add Category</h3>
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
          <button type="submit" className={styles.addButton}>
            Add
          </button>
        </form>
        <button onClick={onClose} className={styles.closeButton}>
          Close
        </button>
        {error && <p className={styles.error}>{error}</p>}
      </div>
    </div>
  );
};

export default CategoryForm;
