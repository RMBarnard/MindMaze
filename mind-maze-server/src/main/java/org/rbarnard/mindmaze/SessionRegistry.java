package org.rbarnard.mindmaze;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

import jakarta.websocket.Session;

@Singleton
public class SessionRegistry {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void addSession(String sessionId, Session session) {
        sessions.put(sessionId, session);
    }

    public void dropSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public Collection<Session> getAllSessions() {
        return sessions.values();
    }
}
