package org.rbarnard.mindmaze.messaging.handlers;

import java.util.List;

import org.rbarnard.mindmaze.BroadcastService;
import org.rbarnard.mindmaze.Game;
import org.rbarnard.mindmaze.GameRegistry;
import org.rbarnard.mindmaze.SessionRegistry;
import org.rbarnard.mindmaze.messaging.GameInfoRequest;
import org.rbarnard.mindmaze.messaging.LobbyInfoResponse;
import org.rbarnard.mindmaze.messaging.Message;
import org.rbarnard.mindmaze.messaging.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import jakarta.websocket.Session;

public class GameInfoRequestHandler implements MessageHandler<GameInfoRequest> {
    private final GameRegistry gameRegistry;
    private final SessionRegistry sessionRegistry;
    private final BroadcastService broadcastService;
    private final ObjectMapper objectMapper;

    @Inject
    public GameInfoRequestHandler(GameRegistry gameRegistry, SessionRegistry sessionRegistry,
            BroadcastService broadcastService) {
        this.gameRegistry = gameRegistry;
        this.sessionRegistry = sessionRegistry;
        this.broadcastService = broadcastService;
        this.objectMapper = new ObjectMapper();
    }

    public void handle(GameInfoRequest gameInfoRequest, Session session) {
        Game game = gameRegistry.getGame(gameInfoRequest.getJoinCode());
        Message response = new Message();
        if (game.isRunning()) {
            // TODO: Send some response for spectating or saying to try later
        } else {
            List<Integer> playerIds = game.getConnectedSessions().entrySet().stream()
                    .map(entry -> entry.getValue().getId())
                    .toList();
            LobbyInfoResponse lobbyInfoResponse = new LobbyInfoResponse(playerIds, game.getShortId(),
                    game.getMapSize());
            response.setTypeId(MessageType.GAME_INFO_RESPONSE.getTypeId());
            try {
                response.setPayloadJson(objectMapper.writeValueAsString(lobbyInfoResponse));
            } catch (Exception e) {
                // TODO: Handle
            }
        }
        broadcastService.broadcastToPlayer(response, session);
    }
}
