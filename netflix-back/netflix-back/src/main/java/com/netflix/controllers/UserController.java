// UserController.java
package com.netflix.controllers;

import com.netflix.auth.SessionManager;
import com.netflix.models.User;
import com.netflix.services.UserService;
import com.netflix.services.ServiceFactory;

public class UserController {
    // Serviço para gerenciamento de usuários
    private UserService userService;

    // Inicializa por meio da Factory
    public UserController() {
        this.userService = ServiceFactory.createUserService();
    }

    // Método que cria um usuário e registra no arquivo users(dentro do userService) e depois retorna o id da sessão
    public String signUp(String username, String password, int age) {
        User user = new User(0, username, password, age);
        userService.addUser(user);
        return SessionManager.getInstance().createSession(user.getId());
    }

    // Método que valida um usuário e retorna uma sessão caso o usuário exista
    public String signIn(String username, String password) {
        User user = userService.getUser(username, password);
        if (user != null) {
            return SessionManager.getInstance().createSession(user.getId());
        }
        return null;
    }
}
