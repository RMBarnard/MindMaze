package org.rbarnard.mindmaze;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.rbarnard.mindmaze.maze.Maze;
import org.rbarnard.mindmaze.Player;
import org.rbarnard.mindmaze.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);
    private final UUID gameId;
    private final Maze maze;
    private Player host;
    private List<Player> players;
    private int turn;
    private boolean isRunning;
    private String shortId; // Join code. 6 character random string

    public Game(Maze maze, Player player) {
        this.gameId = UUID.randomUUID();
        this.maze = maze;
        this.host = player;
        this.players = new ArrayList<>();
        this.players.add(player);
        this.isRunning = false;
        this.shortId = generateJoinCode();
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getShortId() {
        return shortId;
    }

    public void start() {
        isRunning = true;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean addPlayer(Player player) {
        if (isRunning) {
            LOG.info("Cannot add player to game as it is already in progress.");
            return false;
        }
        players.add(player);
        return true;
    }

    private String generateJoinCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(6);
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
}
