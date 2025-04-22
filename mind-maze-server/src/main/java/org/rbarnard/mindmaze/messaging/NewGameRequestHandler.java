package org.rbarnard.mindmaze.messaging;

import java.io.IOException;

import org.rbarnard.mindmaze.Game;
import org.rbarnard.mindmaze.GameRegistry;
import org.rbarnard.mindmaze.GameServer;
import org.rbarnard.mindmaze.Player;
import org.rbarnard.mindmaze.SessionRegistry;
import org.rbarnard.mindmaze.maze.Maze;
import org.rbarnard.mindmaze.maze.MazeLoader;

import com.google.inject.Inject;

import jakarta.websocket.Session;

public class NewGameRequestHandler implements MessageHandler<NewGameRequest> {

    private final GameRegistry gameRegistry;
    private final SessionRegistry sessionRegistry;

    @Inject
    public NewGameRequestHandler(GameRegistry gameRegistry, SessionRegistry sessionRegistry) {
        this.gameRegistry = gameRegistry;
        this.sessionRegistry = sessionRegistry;
    }

    public void handle(NewGameRequest newGameRequest, Session session) {
        NewGameRequest request = (NewGameRequest) newGameRequest;
        String mapSize = request.getMapSize();
        MazeLoader mazeLoader = new MazeLoader();
        try {
            Maze maze = mazeLoader.loadMaze(mapSize + "/maze_1.txt");
            Game game = new Game(maze, session, new Player()); // TODO: PlayerRegistry
            gameRegistry.addGame(game.getShortId(), game);
        } catch (Exception e) {
            // TODO Handle this
        }
    }
}
