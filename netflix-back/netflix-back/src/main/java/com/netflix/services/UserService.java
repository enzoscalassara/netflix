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
    private static List<User> users = new ArrayList<>(); // Lista de usuarios
    private static final ObjectMapper mapper = new ObjectMapper(); // Objeto que vai manipular o JSON
    private static UserService instance;
    // Caminho para o arquivo de usuários, criei um path especifico pra poder dar write enquanto o servidor roda
    private static final String userFilePath = "C:\\Users\\enzo_\\Desktop\\netflix\\netflix-back\\netflix-back\\users.txt";

    // tornar o construtor privado para impedir a criação direta de instâncias
    private UserService() {
        try {
            File file = new File(userFilePath);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8); // Le o arquivo de usuários
                if (!content.isEmpty()) {
                    // Se o arquivo não estiver vazio, faz a leitura do JSON para a lista de usuarios
                    users = mapper.readValue(content, new TypeReference<List<User>>() {});
                }
            } else {
                throw new IOException("users.txt not found in resources");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Criação da instancia do Singleton
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    // Método para pegar a idade do usuário, puxando pela a id de usuario
    public int getUserAge(int userId) {

        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getAge();
    }

    // Método para imprimir o conteúdo do arquivo de usuarios
    public void printUserFile() {
        try {
            String contents = new String(Files.readAllBytes(Paths.get(userFilePath)));
            System.out.println(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método pra adicionar um novo usuario
    public void addUser(User user) {
        int userId = users.size() + 1;
        user.setId(userId); // Atribui um novo ID ao usuário que é teoricamente unico (desde que nenhum usuario seja deletado do arquivo)
        users.add(user); // Adiciona o usuario na lista de usuarios
        try {
            // Reescreve a lista atualizada de usuarios no arquivo
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(userFilePath), users);
            printUserFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para obter um usuário pelo nome de usuário e senha
    public User getUser(String username, String password) {

        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
