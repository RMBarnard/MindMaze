package org.rbarnard.mindmaze.messaging.handlers;

import org.rbarnard.mindmaze.BroadcastService;
import org.rbarnard.mindmaze.Game;
import org.rbarnard.mindmaze.GameRegistry;
import org.rbarnard.mindmaze.Player;
import org.rbarnard.mindmaze.SessionRegistry;
import org.rbarnard.mindmaze.maze.Maze;
import org.rbarnard.mindmaze.maze.MazeLoader;
import org.rbarnard.mindmaze.messaging.Message;
import org.rbarnard.mindmaze.messaging.MessageType;
import org.rbarnard.mindmaze.messaging.NewGameRequest;
import org.rbarnard.mindmaze.messaging.NewGameResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import jakarta.websocket.Session;

public class NewGameRequestHandler implements MessageHandler<NewGameRequest> {
    private static final Logger LOG = LoggerFactory.getLogger(NewGameRequestHandler.class);
    private final GameRegistry gameRegistry;
    private final SessionRegistry sessionRegistry;
    private final BroadcastService broadcastService;
    private final ObjectMapper om;

    @Inject
    public NewGameRequestHandler(GameRegistry gameRegistry, SessionRegistry sessionRegistry,
            BroadcastService broadcastService) {
        this.gameRegistry = gameRegistry;
        this.sessionRegistry = sessionRegistry;
        this.broadcastService = broadcastService;
        this.om = new ObjectMapper();
    }

    public void handle(NewGameRequest newGameRequest, Session session) {
        Message response = new Message();
        response.setTypeId(MessageType.NEW_GAME_RESPONSE.getTypeId());
        NewGameResponse payload = new NewGameResponse();
        NewGameRequest request = (NewGameRequest) newGameRequest;
        String mapSize = request.getMapSize();
        MazeLoader mazeLoader = new MazeLoader();
        try {
            Maze maze = mazeLoader.loadMaze(mapSize + "/maze_1.txt");
            Game game = new Game(maze, session, new Player()); // TODO: PlayerRegistry
            gameRegistry.addGame(game.getShortId(), game);
            payload.setJoinCode(game.getShortId());
            payload.setSuccess(true);
        } catch (Exception e) {
            payload.setSuccess(false);
            LOG.error("Error handling new game request ", e);
        }
        try {
            response.setPayloadJson(om.writeValueAsString(payload));
            broadcastService.broadcastToPlayer(response, session);
        } catch (Exception e) {
            LOG.error("Unable to send message back to client ", e);
        }
    }
}
