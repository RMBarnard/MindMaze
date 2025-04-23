package org.rbarnard.mindmaze;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.rbarnard.mindmaze.maze.Maze;
import org.rbarnard.mindmaze.Player;
import org.rbarnard.mindmaze.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.websocket.Session;

public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);
    private Map<Session, Player> connectedSessions;
    private final UUID gameId;
    private final Maze maze;
    private Player host;
    private int turn;
    private boolean isRunning;
    private String shortId; // Join code. 6 character random string

    public Game(Maze maze, Session session, Player player) {
        this.gameId = UUID.randomUUID();
        this.maze = maze;
        this.host = player;
        this.isRunning = false;
        this.shortId = generateJoinCode();
        this.connectedSessions = new ConcurrentHashMap<>();
        connectedSessions.put(session, player);
    }

    public Map<Session, Player> getConnectedSessions() {
        return connectedSessions;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getShortId() {
        return shortId;
    }

    public String getMapSize() {
        return this.maze.getSize();
    }

    public void start() {
        isRunning = true;
    }

    public boolean addPlayer(Player player, Session session) {
        if (isRunning) {
            LOG.info("Cannot add player to game as it is already in progress.");
            return false;
        }
        connectedSessions.put(session, player);
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

    public boolean isRunning() {
        return isRunning;
    }
}
