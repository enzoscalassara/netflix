package com.netflix.servlets;

import com.netflix.controllers.MovieController;
import com.netflix.services.ServiceFactory;
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
    private MovieController movieController;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.movieController = new MovieController();
        this.userService = ServiceFactory.createUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("movieServlet doGet called");

        String sessionId = request.getHeader("X-Session-Id");
        //System.out.println(sessionId);

        if (sessionId == null || !SessionManager.getInstance().validateSession(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Erro de session\"}");
            return;
        }

        int userId = SessionManager.getInstance().getUserId(sessionId);
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
