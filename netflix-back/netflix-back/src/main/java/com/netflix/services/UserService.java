package com.netflix.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.models.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static List<User> users = new ArrayList<>();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static UserService instance;
    private static final String userFilePath = "C:\\Users\\enzo_\\Desktop\\netflix\\netflix-back\\netflix-back\\users.txt";

    // tornar o construtor privado para impedir a criação direta de instâncias
    private UserService() {
        try {
            File file = new File(userFilePath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                if (!content.isEmpty()) {
                    users = mapper.readValue(content, new TypeReference<List<User>>() {});
                }
            } else {
                throw new IOException("users.txt not found in resources");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // método estático para obter a única instância de UserService
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public int getUserAge(int userId) {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getAge();
    }

    public void printUserFile() {
        try {
            String contents = new String(Files.readAllBytes(Paths.get(userFilePath)));
            System.out.println(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        int userId = users.size() + 1;
        user.setId(userId);
        users.add(user);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(userFilePath), users);
            printUserFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
