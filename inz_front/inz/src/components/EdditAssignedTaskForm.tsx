import React, { useState, useEffect } from 'react';
import styles from './EditAssignedTaskForm.module.css';
import Cookies from 'js-cookie';
import moment from 'moment';

interface TaskUpdateData {
  id: number;
  startDate: string;
  endDate: string;
  description: string;
  category: string;
  task: string;
  login: string | undefined;
}

interface EditAssignedTaskFormProps {
  onClose: () => void;
  onUpdate: (updatedData: TaskUpdateData) => void;
  taskId: number;
  selectedDate: string;
  taskDetails: {
    startDate: string;
    endDate: string;
    description: string;
    category: string;
    task: string;
  } | null;
}

interface Category {
  name: string;
  user: string;
}

interface Task {
  name: string;
  category: string;
  user: string;
}

const EditAssignedTaskForm: React.FC<EditAssignedTaskFormProps> = ({
  onClose,
  taskId,
  selectedDate,
  taskDetails,
}) => {
  const [error, setError] = useState<string>('');
  const [editedStartDate, setEditedStartDate] = useState<string>('');
  const [editedEndDate, setEditedEndDate] = useState<string>('');
  const [editedDescription, setEditedDescription] = useState<string>('');
  const [editedCategory, setEditedCategory] = useState<string>('');
  const [editedTask, setEditedTask] = useState<string>('');
  const [categories, setCategories] = useState<Category[]>([]);
  const [tasks, setTasks] = useState<Task[]>([]);

  useEffect(() => {
    console.log(taskDetails)
    if (taskDetails) {
      setEditedStartDate(moment(taskDetails.startDate).format('HH:mm'));
      setEditedEndDate(moment(taskDetails.endDate).format('HH:mm'));
      setEditedDescription(taskDetails.description);
      setEditedCategory(taskDetails.category);
      setEditedTask(taskDetails.task);
    }
  }, [taskDetails]);

  useEffect(() => {

  }, [selectedDate]);

  const handleUpdate = async () => {

    let newStartDate = new Date(selectedDate)
    let year = newStartDate.getFullYear();
    let month = String(newStartDate.getMonth() + 1).padStart(2, '0');
    let day = String(newStartDate.getDate()).padStart(2, '0');
    let formattedStartDate = `${year}-${month}-${day}`;

    const startDateTime = (`${formattedStartDate}T${editedStartDate}:00.000Z`);
    const endDateTime = (`${formattedStartDate}T${editedEndDate}:00.000Z`);

    const updatedData: TaskUpdateData = {
      id: taskId,
      startDate: startDateTime,
      endDate: endDateTime,
      description: editedDescription,
      category: editedCategory,
      task: editedTask,
      login: Cookies.get('Login')
    };
    try {
      const response = await fetch('http://localhost:8080/api/editAssignedTask', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData),
      });

      if (response.ok) {
        console.log('Task updated successfully');
        onClose();
        window.location.reload();
      } else {
        console.error('Failed to update assigned task');
        setError('Something went wrong1');
      }
    } catch (error) {
      console.error('Error: Failed to update assigned task', error);
      setError('Something went wrong2');
    }
  };

  const handleDelete = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/deleteAssignedTask', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(taskId),
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      onClose();
      window.location.reload();
    } catch (error) {
      console.error('Error deleting task:', error);
      setError('Something went wrong');
    }
  };

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/user/getCategories', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            login: Cookies.get('Login'),
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

    const fetchTasks = async () => {
      try {
        if (editedCategory !== '') {
          const response = await fetch('http://localhost:8080/api/user/getTasksByCategory', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              login: Cookies.get('Login'),
              category: editedCategory,
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

    fetchCategories();
    fetchTasks();
  }, [editedCategory]);

  return (
  <div className={styles.overlay}>
    <div className={styles.taskForm}>
      <h3>Edit Task</h3>
      <form onSubmit={handleUpdate}>
        <label>
          Category:
          <select
            value={editedCategory}
            onChange={(e) => setEditedCategory(e.target.value)}
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
            value={editedTask}
            onChange={(e) => setEditedTask(e.target.value)}
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
            value={editedStartDate}
            onChange={(e) => setEditedStartDate(e.target.value)}
            className={styles.inputField}
          />
        </label>
        <label>
          End Date:
          <input
            type="time"
            value={editedEndDate}
            onChange={(e) => setEditedEndDate(e.target.value)}
            className={styles.inputField}
          />
        </label>
        <label>
          Description:
          <textarea
            value={editedDescription}
            onChange={(e) => setEditedDescription(e.target.value)}
            className={styles.textareaField}
          />
        </label>
        {error && <div className={styles.error}>{error}</div>}
        <button
          type="submit"
          className={styles.saveButton}
          disabled={!editedTask || !editedCategory || !editedStartDate || !editedEndDate}
        >
          Save Changes
        </button>
      </form>
      <button onClick={handleDelete} className={styles.deleteButton}>
        Delete
      </button>
      <button onClick={onClose} className={styles.closeButton}>
        Close
      </button>
    </div>
  </div>
);
};

export default EditAssignedTaskForm;