import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import { loginUser } from '../api'; // Import the loginUser function

const logo = "https://upload.wikimedia.org/wikipedia/commons/6/67/NewNetflixLogo.png";

function Login({ onLogin }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        const user = await loginUser(email, password);

        if(user.success){
            onLogin(true);
            navigate('/home');
        } else {
            console.log(user.message);
        }
    };

    return (
        <div className="container">
            <div className="logo">
                <img className="logo" src={logo} alt="Netflix" />
            </div>

            <form className="login-form" onSubmit={handleFormSubmit}>
                <input
                    type="text"
                    placeholder="Username"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">Login</button>
                <button type="button" onClick={() => navigate('/signup')}>Signup</button>
            </form>
        </div>
    );
}

export default Login;
