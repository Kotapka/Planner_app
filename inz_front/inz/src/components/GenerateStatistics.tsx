import React, { useState } from 'react';
import styles from './GenerateStatistics.module.css';

interface GenerateStatisticsProps {
  onClose: () => void;
  onGenerate: (startDate: string, endDate: string) => void;
}

const GenerateStatistics: React.FC<GenerateStatisticsProps> = ({ onClose, onGenerate }) => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onGenerate(startDate, endDate);
    onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.generateStatisticsForm}>
        <h3>Generate Statistics</h3>
        <form onSubmit={handleSubmit}>
          <label>
            Start Date:
            <input
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              className={styles.inputField}
            />
          </label>
          <label>
            End Date:
            <input
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              className={styles.inputField}
            />
          </label>
          <button type="submit" className={styles.addButton}>
            Generate
          </button>
        </form>
        <button onClick={onClose} className={styles.closeButton}>
          Close
        </button>
      </div>
    </div>
  );
};

export default GenerateStatistics;
