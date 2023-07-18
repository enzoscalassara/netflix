const SERVER_URL = "http://localhost:8080/netflix-back"

export const categories = [
    {
        name: "trending",
        title: "Em Alta",
        path: "/movies/trending",
        isLarge: true,
    },
    {
        name: "netflixOriginals",
        title: "Originais Netflix",
        path: "/movies/netflixOriginals",
        isLarge: false,
    },
    {
        name: "topRated",
        title: "Populares",
        path: "/movies/topRated",
        isLarge: false,
    },
    {
        name: "comedy",
        title: "Comédias",
        path: "/movies/comedy",
        isLarge: false,
    },
    {
        name: "romances",
        title: "Romances",
        path: "/movies/romances",
        isLarge: false,
    },
    {
        name: "documentaries",
        title: "Documentários",
        path: "/movies/documentaries",
        isLarge: false,
    }
]

export const getMovies = async (path) => {
    try {
        const sessionId = localStorage.getItem('sessionId');
        console.log('sessionId:', sessionId);

        let url = SERVER_URL + path;
        console.log('url:', url);

        const response = await fetch(url, {
            credentials: 'include',
            headers: {
                'X-Session-Id': sessionId,
            }
        });

        console.log('response:', response);

        return await response.json();

    } catch (error) {
        console.log("error getMovies: ", error)
    }
}


export const loginUser = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/netflix-back/auth/signin', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('sessionId', data);  // Store sessionId
            return { success: true };
        } else {
            return { success: false, message: "Invalid username or password" };
        }

    } catch (error) {
        console.error('Login error:', error);
        return { success: false, message: error.toString() };
    }
}

export const signupUser = async (user) => {
    try {
        const response = await fetch('http://localhost:8080/netflix-back/auth/signup', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('sessionId', data);  // Store sessionId
            return { success: true };
        } else {
            return { success: false, message: "Signup failed" };
        }

    } catch (error) {
        console.error('Signup error:', error);
        return { success: false, message: error.toString() };
    }
}