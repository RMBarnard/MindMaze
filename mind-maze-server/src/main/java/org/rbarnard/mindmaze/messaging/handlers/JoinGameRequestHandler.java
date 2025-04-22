package org.rbarnard.mindmaze.messaging.handlers;

import org.rbarnard.mindmaze.messaging.JoinGameRequest;

import jakarta.websocket.Session;

public class JoinGameRequestHandler implements MessageHandler<JoinGameRequest> {
    public void handle(JoinGameRequest joinGameRequest, Session session) {

    }
}
