package com.netflix.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.controllers.UserController;
import com.netflix.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/auth/signin")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("signin doPost");

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String data = buffer.toString();

        // parse JSON body of the request
        ObjectMapper mapper = new ObjectMapper();
        User requestBody = mapper.readValue(data, User.class);

        String username = requestBody.getUsername();
        String password = requestBody.getPassword();

        UserController userController = new UserController();
        String sessionId = userController.signIn(username, password);

        if (sessionId != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", sessionId);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(sessionId));
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        }
    }
}
