package org.rbarnard.mindmaze;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.rbarnard.mindmaze.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;

@Singleton
public class BroadcastService {
    private static final Logger LOG = LoggerFactory.getLogger(BroadcastService.class);
    private final GameRegistry gameRegistry;
    private final SessionRegistry sessionRegistry;

    @Inject
    public BroadcastService(GameRegistry gameRegistry, SessionRegistry sessionRegistry) {
        this.gameRegistry = gameRegistry;
        this.sessionRegistry = sessionRegistry;
    }

    public void broadcastToGame(Message message, Game game) {
        game.getConnectedSessions().keySet().forEach(session -> broadcastToPlayer(message, session));
    }

    public void broadcastAll(Message message) {
        sessionRegistry.getAllSessions().forEach(session -> broadcastToPlayer(message, session));
    }

    public void broadcastToPlayer(Message message, Session session) {
        synchronized (session) {
            try {
                session.getBasicRemote().sendObject(message);
                LOG.info("Message sent");
            } catch (Exception e) {
                LOG.error("Error sending message to player", e);
            }
        }
    }
}
