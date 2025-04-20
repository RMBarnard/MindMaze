package org.rbarnard.mindmaze;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import org.rbarnard.mindmaze.maze.Maze;
import org.rbarnard.mindmaze.maze.MazeLoader;
import org.rbarnard.mindmaze.messaging.JoinGameRequest;
import org.rbarnard.mindmaze.messaging.Message;
import org.rbarnard.mindmaze.messaging.MessageEncoder;
import org.rbarnard.mindmaze.messaging.MessageDecoder;
import org.rbarnard.mindmaze.messaging.MessageType;
import org.rbarnard.mindmaze.messaging.NewGameRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Session session;
    private static HashMap<String /* ShortId */, Game> games;
    private static Set<GameServer> gameServerEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String/* SessionId */, Player> users = new HashMap<>();

    @OnOpen
    public void onSessionOpen(Session session, @PathParam("userId") int userId) throws IOException, EncodeException {
        this.session = session;
        gameServerEndpoints.add(this);
        users.put(session.getId(), new Player());
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        switch (message.getTypeTag()) {
            case NEW_GAME:
                Game game = createNewGame(session, message);
                break;
            case JOIN_GAME:
                boolean gameJoined = joinGame(session, message);
                // TODO: Handle game joined or not joined
                break;
            case GET_STATS:
                break;
            case SEND_FRIEND_REQUEST:
                break;
            case UPDATE_SETTINGS:
                break;
        }
    }

    @OnClose
    public void onClose(Session session) {
        users.remove(session.getId());
        gameServerEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOG.error("There has been an error with session {}", session.getId(), throwable);
    }

    private static void broadcastToGame(Message message, Game game) throws IOException, EncodeException {
        gameServerEndpoints.stream()
                // TODO: This needs some work in order to just match on playerId or
                .filter(gameServer -> game.getPlayers().contains(users.get(gameServer.session.getId())))
                .forEach(endpoint -> {
                    synchronized (endpoint.session) {
                        try {
                            endpoint.session.getBasicRemote()
                                    .sendObject(message);
                        } catch (Exception e) {
                            LOG.error("Error sending message to game", e);
                        }
                    }
                });
    }

    private static void broadcastAll(Message message) throws IOException, EncodeException {
        gameServerEndpoints.forEach(endpoint -> {
            synchronized (endpoint.session) {
                try {
                    endpoint.session.getBasicRemote()
                            .sendObject(message);
                } catch (Exception e) {
                    LOG.error("Error sending message", e);
                }
            }
        });
    }

    private Game createNewGame(Session session, Message message) {
        try {
            ObjectMapper om = new ObjectMapper();
            NewGameRequest newGameRequest = om.readValue(message.getPayloadJson(), NewGameRequest.class);
            MazeLoader mazeLoader = new MazeLoader();
            Maze maze = mazeLoader.loadMaze(newGameRequest.getMapSize() + "/maze_1.txt");
            Game game = new Game(maze, users.get(session.getId()));
            games.put(game.getShortId(), game);
            return game;
        } catch (Exception e) {
            LOG.error("Unable to create new game", e);
            return null;
        }
    }

    private boolean joinGame(Session session, Message message) {
        try {
            ObjectMapper om = new ObjectMapper();
            JoinGameRequest joinGameRequest = om.readValue(message.getPayloadJson(), JoinGameRequest.class);
            Game gameToJoin = games.get(joinGameRequest.getJoinId());
            if (gameToJoin == null) {
                return false;
            }
            return gameToJoin.addPlayer(users.get(session.getId()));
        } catch (Exception e) {
            LOG.error("Unable to add player to game", e);
            return false;
        }
    }
}
