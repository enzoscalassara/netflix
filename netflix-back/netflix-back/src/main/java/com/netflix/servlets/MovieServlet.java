package com.netflix.servlets;

import com.netflix.controllers.MovieController;
import com.netflix.services.UserService;
import com.netflix.auth.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/movies/*")
public class MovieServlet extends HttpServlet {
    private MovieController movieController = new MovieController();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("movieServlet doGet called");
        String sessionId = request.getRequestedSessionId();
        if (!SessionManager.validateSession(sessionId)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erro de session");
            return;
        }

        int userId = SessionManager.getUserId(sessionId);
        int userAge = userService.getUserAge(userId);
        boolean includeAdultContent = userAge >= 18;

        String pathInfo = request.getPathInfo();
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        String moviesJson = movieController.getMovies(pathInfo, includeAdultContent);

        response.setContentType("application/json");
        response.getWriter().write(moviesJson);
    }
}
