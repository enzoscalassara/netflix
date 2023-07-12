import React, { useState } from 'react';
import './Signup.css';
import { signupUser } from '../api'; // Import the signupUser function

function Signup({ onSignup }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [age, setAge] = useState('');

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        const user = {
            username,
            password,
            age
        };

        const response = await signupUser(user);

        if(response.success){
            setUsername('');
            setPassword('');
            setAge('');
            if (typeof onSignup === 'function') {
                onSignup();
            }
        } else {
            console.error(response.message);
        }
    };

    return (
        <div className="container">
            <h2>Sign Up</h2>
            <form className="signup-form" onSubmit={handleFormSubmit}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <input
                    type="number"
                    placeholder="Age"
                    value={age}
                    onChange={(e) => setAge(e.target.value)}
                />
                <button type="submit" >Create Account</button>
            </form>
        </div>
    );
}

export default Signup;
