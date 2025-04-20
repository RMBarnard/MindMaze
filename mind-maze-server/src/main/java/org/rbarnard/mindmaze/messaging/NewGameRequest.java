package org.rbarnard.mindmaze.messaging;

public class NewGameRequest {
    private String mapSize;

    public String getMapSize() {
        return mapSize;
    }

    public void setMapSize(String mapSize) {
        this.mapSize = mapSize;
    }
}
