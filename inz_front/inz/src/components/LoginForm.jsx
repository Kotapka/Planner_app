import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../components/style.css';
import axios from 'axios';

function LoginForm() {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async () => {
    try {
      // Wysyłanie żądania do serwera, zastąp 'URL_DO_TWOJEGO_ENDPOINTA' właściwym adresem API
      const response = await axios.post('http://localhost:8080/api/login', {
        login: login,
        password: password,
      });

      // Przetwarzanie odpowiedzi, np. aktualizacja stanu w zależności od odpowiedzi serwera
      console.log('Response:', response.data);
    } catch (error) {
      // Obsługa błędów
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
                  <h2 className="fw-bold mb-2 text-uppercase">Login</h2>
                  <p className="text-white-50 mb-5">Please enter your login and password!</p>

                  <div className="form-outline form-white mb-4">
                    <input type="login" id="typeLoginX" className="form-control form-control-lg" value={login} onChange={(e) => setLogin(e.target.value)}/>
                    <label className="form-label" htmlFor="typeLoginX">
                      Login
                    </label>
                  </div>

                  <div className="form-outline form-white mb-4">
                    <input type="password" id="typePasswordX" className="form-control form-control-lg" value={password} onChange={(e) => setPassword(e.target.value)}/>
                    <label className="form-label" htmlFor="typePasswordX">
                      Password
                    </label>
                  </div>

                  <p className="small mb-5 pb-lg-2">
                    <a className="text-white-50" href="#!">
                      Forgot password?
                    </a>
                  </p>

                  <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleLogin}>
                    Login
                  </button>

                  <div className="d-flex justify-content-center text-center mt-4 pt-1">
                    <a href="#!" className="text-white">
                      <i className="fab fa-facebook-f fa-lg"></i>
                    </a>
                    <a href="#!" className="text-white">
                      <i className="fab fa-twitter fa-lg mx-4 px-2"></i>
                    </a>
                    <a href="#!" className="text-white">
                      <i className="fab fa-google fa-lg"></i>
                    </a>
                  </div>
                </div>

                <div>
                  <p className="mb-0">
                    Don't have an account? <a href="#!" className="text-white-50 fw-bold">Sign Up</a>
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

export default LoginForm;
