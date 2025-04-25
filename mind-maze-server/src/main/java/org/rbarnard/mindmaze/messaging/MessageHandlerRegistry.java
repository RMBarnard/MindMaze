package org.rbarnard.mindmaze.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rbarnard.mindmaze.messaging.handlers.GameInfoRequestHandler;
import org.rbarnard.mindmaze.messaging.handlers.JoinGameRequestHandler;
import org.rbarnard.mindmaze.messaging.handlers.MessageHandler;
import org.rbarnard.mindmaze.messaging.handlers.NewGameRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import jakarta.websocket.Session;

public class MessageHandlerRegistry {
    private final ObjectMapper mapper;
    private final Map<MessageType, HandlerEntry<?>> handlers;

    @Inject
    public MessageHandlerRegistry(NewGameRequestHandler newGameRequestHandler,
            GameInfoRequestHandler gameInfoRequestHandler, JoinGameRequestHandler joinGameRequestHandler) {
        mapper = new ObjectMapper();
        handlers = new ConcurrentHashMap<>();
        register(MessageType.NEW_GAME_REQUEST, NewGameRequest.class, newGameRequestHandler);
        register(MessageType.GAME_INFO_REQUEST, GameInfoRequest.class, gameInfoRequestHandler);
        register(MessageType.JOIN_GAME_REQUEST, JoinGameRequest.class, joinGameRequestHandler);
    }

    private <T> void register(MessageType type, Class<T> payloadClass, MessageHandler<T> handler) {
        if (type.getDirection() != MessageDirection.CLIENT_TO_SERVER) {
            throw new IllegalArgumentException("Tried to register a client-to-server handler for " + type
                    + " but direction is " + type.getDirection());
        }
        handlers.put(type, new HandlerEntry<>(payloadClass, handler));
    }

    @SuppressWarnings("unchecked")
    public void handleMessage(Message message, Session session) throws Exception {
        MessageType type = MessageType.fromId(message.getTypeId());

        HandlerEntry<?> entry = handlers.get(type);
        if (entry == null) {
            throw new IllegalArgumentException("No handler registered for message type: " + type);
        }

        Object payload = mapper.readValue(message.getPayloadJson(), entry.payloadClass);
        ((MessageHandler<Object>) entry.handler).handle(payload, session);
    }

    private static class HandlerEntry<T> {
        final Class<T> payloadClass;
        final MessageHandler<T> handler;

        HandlerEntry(Class<T> payloadClass, MessageHandler<T> handler) {
            this.payloadClass = payloadClass;
            this.handler = handler;
        }
    }
}
