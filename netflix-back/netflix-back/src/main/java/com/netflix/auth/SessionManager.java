package com.netflix.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, Session> sessions = new HashMap<>();
    private static final int SESSION_TIMEOUT = 30;  // Minutos

    private static class Session {
        final int userId;
        long lastAccessTime;

        Session(int userId) {
            this.userId = userId;
            this.lastAccessTime = System.currentTimeMillis();
        }
    }

    public static String createSession(int userId) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(userId));
        return sessionId;
    }

    public static boolean validateSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        long minutesSinceLastAccess = (currentTime - session.lastAccessTime) / 1000 / 60;
        if (minutesSinceLastAccess > SESSION_TIMEOUT) {
            invalidateSession(sessionId);
            return false;
        }

        // Update the last access time
        session.lastAccessTime = currentTime;
        return true;
    }

    public static int getUserId(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("Invalid session ID");
        }
        return session.userId;
    }

    public static void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
