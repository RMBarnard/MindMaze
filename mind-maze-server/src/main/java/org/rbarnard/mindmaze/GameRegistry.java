package org.rbarnard.mindmaze;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class GameRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(GameRegistry.class);
    private final Map<String, Game> games = new ConcurrentHashMap<>();

    public Game getGame(String joinCode) {
        return games.get(joinCode);
    }

    public void addGame(String joinCode, Game game) {
        games.put(joinCode, game);
        LOG.info("New game added");
    }

    public void removeGame(String joinCode) {
        games.remove(joinCode);
    }

    public Collection<Game> getAllGames() {
        return games.values();
    }
}
