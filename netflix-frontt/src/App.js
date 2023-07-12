import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import Home from "./pages/Home";
import Login from './pages/Login';
import Signup from './pages/Signup';

/*
function App() {
    const [isAuthenticated, setAuthenticated] = React.useState(false);

    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route
                        path="/"
                        element={isAuthenticated ? <Home /> : <Navigate to="/login" replace />}
                    />
                    <Route path="/login" element={<Login />} />
                    <Route path="/signup" element={<Signup />} />
                </Routes>
            </div>
        </Router>
    );
}/*
function App() {
    const [isAuthenticated, setAuthenticated] = React.useState(false);

    const handleLogin = (status) => {
        setAuthenticated(status);
    };

    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route
                        path="/"
                        element={isAuthenticated ? <Navigate to="/home" replace /> : <Navigate to="/login" replace />}
                    />
                    <Route path="/home" element={isAuthenticated ? <Home /> : <Navigate to="/login" replace />} />
                    <Route path="/login" element={<Login onLogin={handleLogin} />} />
                    <Route path="/signup" element={<Signup />} />
                </Routes>
            </div>
        </Router>
    );
}*/

function App() {
    const [isAuthenticated, setAuthenticated] = React.useState(false);

    const handleLogin = (status) => {
        setAuthenticated(status);
    };

    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route
                        path="/"
                        element={<Navigate to={isAuthenticated ? "/home" : "/login"} replace />}
                    />
                    <Route path="/home"
                           element={isAuthenticated ? <Home /> : <Navigate to="/login" replace />}
                    />
                    <Route path="/login"
                           element={isAuthenticated ? <Navigate to="/home" replace /> : <Login onLogin={handleLogin} />}
                    />
                    <Route path="/signup"
                           element={isAuthenticated ? <Navigate to="/home" replace /> : <Signup />}
                    />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
