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

        // Cria um StringBuilder para ler o corpo da requisição
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        // Lê linha por linha do corpo da requisição e adiciona ao StringBuilder
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        // Converte o conteúdo para uma string
        String data = buffer.toString();

        // Faz o parsing do corpo da requisição para um objeto User
        ObjectMapper mapper = new ObjectMapper();
        User requestBody = mapper.readValue(data, User.class);

        // Pega o nome de usuário e a senha da requisição
        String username = requestBody.getUsername();
        String password = requestBody.getPassword();

        UserController userController = new UserController();
        // Guarda o id da sessão em uma string
        String sessionId = userController.signIn(username, password);

        // Se o id não for nulo devolve o id da sessao
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
