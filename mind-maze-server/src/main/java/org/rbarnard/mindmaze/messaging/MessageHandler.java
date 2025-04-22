package org.rbarnard.mindmaze.messaging;

import jakarta.websocket.Session;

public interface MessageHandler<T> {
    void handle(T payload, Session session);
}
