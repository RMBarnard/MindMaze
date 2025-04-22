package org.rbarnard.mindmaze.messaging;

import jakarta.websocket.Session;

public class JoinGameRequestHandler<JoinGameRequest> implements MessageHandler<JoinGameRequest> {
    public void handle(JoinGameRequest joinGameRequest, Session session) {

    }
}
