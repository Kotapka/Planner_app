import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../components/style.css';
import axios from 'axios';
import Cookies from 'js-cookie';
import { request, setAuthHeader } from '../axios_helper';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [showLoginForm, setShowLoginForm] = useState(true);

  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/login', {
        login: login,
        password: password,
      });

      console.log('Response:', response.data);
      if (response.data) {
        const token = response.data.token;
        setAuthHeader(token);
        Cookies.set('jwtToken', token);
        Cookies.set('Login',login)
        console.log('About to navigate');
        navigate('/PlannerPage');
      }
    } catch (error) {
      console.error('Error:', error.message);
    }
  };

  const handleSignUpClick = () => {
    setShowLoginForm(!showLoginForm);
  };

  const handleSignUp = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/register', {
        name: name,
        surname: surname,
        login: login,
        password: password,
      });

      console.log('Response:', response.data);
      if (response.data) {
        const token = response.data.token;
        setAuthHeader(token);
        Cookies.set('jwtToken', token);
        Cookies.set('Login',login)
        console.log('About to navigate');
        navigate('/PlannerPage');
      }
    } catch (error) {
      console.error('Error:', error.message);
    }
  };

  return (
    <section className="gradient-custom">
      <div className="container py-5 h-100">
        <div className="row d-flex justify-content-center align-items-center h-100">
          <div className="col-12 col-md-8 col-lg-6 col-xl-5">
            <div className="card bg-dark text-white" style={{ borderRadius: '1rem' }}>
              <div className="card-body p-5 text-center">
                <div className="mb-md-5 mt-md-4 pb-5">
                  <h2 className="fw-bold mb-2 text-uppercase">{showLoginForm ? 'Login' : 'Sign Up'}</h2>
                  <p className="text-white-50 mb-5">
                    {showLoginForm ? 'Please enter your login and password!' : 'Create an account!'}
                  </p>
                  {showLoginForm ? (
                    <div>
                      <div className="form-outline form-white mb-4">
                        <input
                          type="login"
                          id="typeLoginX"
                          className="form-control form-control-lg"
                          value={login}
                          onChange={(e) => setLogin(e.target.value)}
                        />
                        <label className="form-label" htmlFor="typeLoginX">
                          Login
                        </label>
                      </div>        
  
                      <div className="form-outline form-white mb-4">
                        <input
                          type="password"
                          id="typePasswordX"
                          className="form-control form-control-lg"
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                        />
                        <label className="form-label" htmlFor="typePasswordX">
                          Password
                        </label>
                      </div>

                      <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleLogin}>
                      Login
                      </button>
                      
                    </div>
                    
                  ) : (
                    <div>
                      <div className="form-outline form-white mb-4">
                        <input
                          type="text"
                          id="typeName"
                          className="form-control form-control-lg"
                          value={name}
                          onChange={(e) => setName(e.target.value)}
                        />
                        <label className="form-label" htmlFor="typeName">
                          Name
                        </label>
                      </div>
  
                      <div className="form-outline form-white mb-4">
                        <input
                          type="text"
                          id="typeSurname"
                          className="form-control form-control-lg"
                          value={surname}
                          onChange={(e) => setSurname(e.target.value)}
                        />
                        <label className="form-label" htmlFor="typeSurname">
                          Surname
                        </label>
                      </div>

                      <div className="form-outline form-white mb-4">
                        <input
                          type="login"
                          id="typeLoginX"
                          className="form-control form-control-lg"
                          value={login}
                          onChange={(e) => setLogin(e.target.value)}
                        />
                        <label className="form-label" htmlFor="typeLoginX">
                          Login
                        </label>
                      </div>
  
                      <div className="form-outline form-white mb-4">
                        <input
                          type="password"
                          id="typePassword"
                          className="form-control form-control-lg"
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                        />
                        <label className="form-label" htmlFor="typePassword">
                          Password
                        </label>
                      </div>
  
                      <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleSignUp}>
                        Sign Up
                      </button>
                    </div>
                  )}
  
                  <p className="mb-0">
                    {showLoginForm ? "Don't have an account?" : 'Already have an account?'}
                    <a href="#!" className="text-white-50 fw-bold" onClick={handleSignUpClick}>
                      {showLoginForm ? ' Sign Up' : ' Log In'}
                    </a>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
  
}

export default LoginPage;
