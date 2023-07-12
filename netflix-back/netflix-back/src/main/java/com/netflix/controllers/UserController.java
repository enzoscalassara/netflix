package com.netflix.controllers;

import com.netflix.auth.SessionManager;
import com.netflix.models.User;
import com.netflix.services.UserService;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public String signUp(String username, String password, int age) {
        User user = new User(0, username, password, age);
        userService.addUser(user);
        return SessionManager.createSession(user.getId());
    }

    public String signIn(String username, String password) {
        User user = userService.getUser(username, password);
        if (user != null) {
            return SessionManager.createSession(user.getId());
        }
        return null;
    }
}
