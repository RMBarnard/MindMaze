package org.rbarnard.mindmaze.messaging;

import java.util.List;

public class LobbyInfoResponse {
    List<Integer> joinedPlayerIds;
    private final String joinCode;
    private final String mapSize;

    public LobbyInfoResponse(List<Integer> joinedPlayerIds, String joinCode, String mapSize) {
        this.joinedPlayerIds = joinedPlayerIds;
        this.joinCode = joinCode;
        this.mapSize = mapSize;
    }

    public List<Integer> getJoinedPlayerIds() {
        return joinedPlayerIds;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public String getMapSize() {
        return this.mapSize;
    }
}
