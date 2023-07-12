package com.netflix.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.controllers.UserController;
import com.netflix.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;

@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("SignupServlet doPost called");

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String data = buffer.toString();


        ObjectMapper mapper = new ObjectMapper();
        User requestBody = mapper.readValue(data, User.class);

        String username = requestBody.getUsername();
        String password = requestBody.getPassword();
        int age = requestBody.getAge();

        System.out.println(username);

        UserController userController = new UserController();
        String sessionId = userController.signUp(username, password, age);


        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(sessionId));
    }
}
