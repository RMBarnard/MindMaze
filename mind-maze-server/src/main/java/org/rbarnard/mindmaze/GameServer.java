package org.rbarnard.mindmaze;

import java.io.IOException;

import org.rbarnard.mindmaze.messaging.Message;
import org.rbarnard.mindmaze.messaging.MessageEncoder;
import org.rbarnard.mindmaze.messaging.MessageHandlerRegistry;
import org.rbarnard.mindmaze.messaging.MessageDecoder;
import org.rbarnard.mindmaze.messaging.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/game/{userId}", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class GameServer {
    private static final Logger LOG = LoggerFactory.getLogger(GameServer.class);
    private final MessageHandlerRegistry messageHandlerRegistry = InjectorProvider.getInjector()
            .getInstance(MessageHandlerRegistry.class);
    private final SessionRegistry sessionRegistry = InjectorProvider.getInjector().getInstance(SessionRegistry.class);
    private final BroadcastService broadcastService = InjectorProvider.getInjector()
            .getInstance(BroadcastService.class);

    @OnOpen
    public void onSessionOpen(Session session, @PathParam("userId") int userId) throws IOException, EncodeException {
        LOG.info("New session from user {}", userId);
        Message message = new Message();
        sessionRegistry.addSession(session.getId(), session);
        message.setTypeId(MessageType.CONNECT.getTypeId());
        message.setPayloadJson("Hello World");
        broadcastService.broadcastToPlayer(message, session);
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        boolean messageHandled;
        LOG.info("Received message" + message);
        try {
            messageHandlerRegistry.handleMessage(message, session);
            messageHandled = true;
        } catch (Exception e) {
            LOG.error("Unable to handle message {}", message, e);
            messageHandled = false;
        }
        LOG.debug("Message handled status for message {}: {}", message, messageHandled);
    }

    @OnClose
    public void onClose(Session session) {
        sessionRegistry.dropSession(session.getId());
        LOG.info("Closed session");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOG.error("There has been an error with session {}", session.getId(), throwable);
    }
}
