// Statistics.tsx

import React, { useState, useEffect } from 'react';
import styles from './Statistics.module.css';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import Box from '@mui/material/Box';
import { DataGrid } from '@mui/x-data-grid';
import GenerateStatistics from '../components/GenerateStatistics';
import axios from 'axios';

function Statistics() {
  const [apiData, setApiData] = useState([]);
  const navigate = useNavigate();
  const [statisticsShowForm, setStatisticsShowForm] = useState(false);

  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'category', headerName: 'Category', width: 150 },
    { field: 'task', headerName: 'Task', width: 150 },
    { field: 'timespend', headerName: 'Time Spend(min)', width: 150 },
  ];

  const handleLogout = () => {
    try {
      const token = Cookies.get('jwtToken');
      Cookies.remove('jwtToken');
      Cookies.remove('Login');
      console.log('About to navigate');
      navigate('/');
    } catch (error) {
      console.error('Error:', error.message);
    }
  };

  const handleGenerateStatistics = () => {
    setStatisticsShowForm(true);
  };

  const handlePlannerPage = () => {
    try {
      console.log('Navigating to PlannerPage');
      navigate('/PlannerPage');
    } catch (error) {
      console.error(error.message);
    }
  };

  const handleGenerate = async (startDate, endDate) => {
    try {
      const token = Cookies.get('jwtToken');
      const response = await axios.post(
        'http://localhost:8080/api/getStatistics',
        {
          user: Cookies.get('Login'),
          startDate,
          endDate,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
  
      if (Array.isArray(response.data)) {
        setApiData(
          response.data.map((row, index) => ({
            id: index + 1,
            category: row.category,
            task: row.task,
            timespend: row.duration,
          })))
      } else {
        console.error('Error generating statistics: Response data is not an array.');
      }
  
      setStatisticsShowForm(false);
    } catch (error) {
      console.error('Error generating statistics:', error.message);
    }
  };

  const handleDownloadCSV = () => {
    if (apiData.length > 0) {
      const csvContent = "data:text/csv;charset=utf-8," +
        "id,category,task,timespend\n" +
        apiData.map(row => `${row.id},${row.category},${row.task},${row.timespend}`).join("\n");
  
      const encodedUri = encodeURI(csvContent);
      const link = document.createElement("a");
      link.setAttribute("href", encodedUri);
      link.setAttribute("download", "statistics.csv");
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } else {
      console.error("No data to download");
    }
  };

  return (
    <div className={styles['statistics-container']}>
      <div className={styles['side-panel']}>
        <h2>PlannerApp</h2>
        <div className={styles['logo']}>{/* Dodaj logo, jeśli masz odpowiednią grafikę */}</div>
        <div className={styles['action-buttons-container']}>
          <button onClick={handleGenerateStatistics} className={styles['logout-button']}>
            Generate Statistics
          </button>
          <button onClick={handleDownloadCSV} className={styles['logout-button']}>
           Download CSV
          </button>
          {statisticsShowForm && <GenerateStatistics onClose={() => setStatisticsShowForm(false)} onGenerate={handleGenerate} />}
          <button onClick={handlePlannerPage} className={styles['logout-button']}>
            Planner
          </button>
          <button onClick={handleLogout} className={styles['logout-button']}>
            Logout
          </button>
        </div>
      </div>
      <Box className={styles['data-grid-container']} sx={{ height: '100%', width: '100%' }}>
        <DataGrid rows={apiData} columns={columns} pageSize={5} disableRowSelectionOnClick />
      </Box>
      <div>{statisticsShowForm && <GenerateStatistics onClose={() => setStatisticsShowForm(false)} onGenerate={handleGenerate} />}</div>
    </div>
  );
}

export default Statistics;
