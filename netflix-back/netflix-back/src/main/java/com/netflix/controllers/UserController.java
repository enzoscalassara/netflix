// UserController.java
package com.netflix.controllers;

import com.netflix.auth.SessionManager;
import com.netflix.models.User;
import com.netflix.services.UserService;
import com.netflix.services.ServiceFactory;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = ServiceFactory.createUserService();
    }

    public String signUp(String username, String password, int age) {
        User user = new User(0, username, password, age);
        userService.addUser(user);
        return SessionManager.getInstance().createSession(user.getId());
    }

    public String signIn(String username, String password) {
        User user = userService.getUser(username, password);
        if (user != null) {
            return SessionManager.getInstance().createSession(user.getId());
        }
        return null;
    }
}
