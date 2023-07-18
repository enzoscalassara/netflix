package com.netflix.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    // Guarda todas as sessões ativas
    private static final Map<String, Session> sessions = new HashMap<>();
    // Tempo limite de cada sessão (em minutos)
    private static final int SESSION_TIMEOUT = 30;
    // Instância Singleton
    private static SessionManager instance;

    private SessionManager() {
    }

    // Classe para armazenar informações de sessão
    private static class Session {
        final int userId; // O ID do usuário para esta sessão
        long lastAccessTime; // Última vez que esta sessão foi acessada

        Session(int userId) {
            this.userId = userId;
            this.lastAccessTime = System.currentTimeMillis();
        }
    }

    // Retorna a única instância do singleton
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Cria uma nova sessão para um usuário e atribui uma ID de sessão única para ele
    public String createSession(int userId) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(userId));
        return sessionId;
    }

    // Valida uma sessão com base na ID
    public boolean validateSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        // Como o currenTime é resgatado no valor de ms, é necessário fazer uma conversão para minutos
        long minutesSinceLastAccess = (currentTime - session.lastAccessTime) / 1000 / 60;
        // Se o número de minutos passou do limite (30m), encerra a sessao removendo a ID do mapa
        if (minutesSinceLastAccess > SESSION_TIMEOUT) {
            invalidateSession(sessionId);
            return false;
        }

        // Toda vez que validateSession é chamado atualiza o último tempo de acesso da sessão
        session.lastAccessTime = currentTime;
        return true;
    }

    // Retorna o ID do usuário de uma sessão
    public int getUserId(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("Invalid session ID");
        }
        return session.userId;
    }

    // Invalida uma sessão, removendo-a do mapa de sessões
    public void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
