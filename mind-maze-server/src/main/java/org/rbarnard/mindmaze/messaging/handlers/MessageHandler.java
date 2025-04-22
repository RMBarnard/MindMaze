package org.rbarnard.mindmaze.messaging.handlers;

import jakarta.websocket.Session;

public interface MessageHandler<T> {
    void handle(T payload, Session session);
}
