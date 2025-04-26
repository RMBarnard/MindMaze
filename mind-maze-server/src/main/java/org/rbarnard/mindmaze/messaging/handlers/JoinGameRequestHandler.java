package org.rbarnard.mindmaze.messaging.handlers;

import org.rbarnard.mindmaze.BroadcastService;
import org.rbarnard.mindmaze.Game;
import org.rbarnard.mindmaze.GameRegistry;
import org.rbarnard.mindmaze.Player;
import org.rbarnard.mindmaze.messaging.JoinGameRequest;
import org.rbarnard.mindmaze.messaging.JoinGameResponse;
import org.rbarnard.mindmaze.messaging.LobbyInfoResponse;
import org.rbarnard.mindmaze.messaging.Message;
import org.rbarnard.mindmaze.messaging.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import jakarta.websocket.Session;

public class JoinGameRequestHandler implements MessageHandler<JoinGameRequest> {
    private final GameRegistry gameRegistry;
    private final BroadcastService broadcastService;
    private final ObjectMapper objectMapper;

    @Inject
    public JoinGameRequestHandler(GameRegistry gameRegistry, BroadcastService broadcastService) {
        this.gameRegistry = gameRegistry;
        this.broadcastService = broadcastService;
        this.objectMapper = new ObjectMapper();
    }

    public void handle(JoinGameRequest joinGameRequest, Session session) {
        Game game = gameRegistry.getGame(joinGameRequest.getJoinId());
        boolean success = game.addPlayer(new Player(), session);
        Message playerResponse = new Message();
        Message lobbyResponse = new Message();
        try {
            JoinGameResponse joinGameResponse = new JoinGameResponse();
            joinGameResponse.setSuccess(success);
            joinGameResponse.setJoinCode(game.getShortId());
            playerResponse.setTypeId(MessageType.JOIN_GAME_RESPONSE.getTypeId());
            playerResponse.setPayloadJson(objectMapper.writeValueAsString(joinGameResponse));
            broadcastService.broadcastToPlayer(playerResponse, session);
            if (success) {
                LobbyInfoResponse lobbyInfoResponse = new LobbyInfoResponse(
                        game.getConnectedSessions().entrySet().stream().map(x -> x.getValue().getId()).toList(),
                        game.getShortId(), game.getMapSize());
                lobbyResponse.setTypeId(MessageType.LOBBY_INFO_RESPONSE.getTypeId());
                lobbyResponse.setPayloadJson(objectMapper.writeValueAsString(lobbyInfoResponse));
                broadcastService.broadcastToGame(lobbyResponse, game);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
