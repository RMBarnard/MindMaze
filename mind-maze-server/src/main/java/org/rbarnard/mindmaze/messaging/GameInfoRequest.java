package org.rbarnard.mindmaze.messaging;

public class GameInfoRequest {
    private String joinCode;

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public String getJoinCode() {
        return joinCode;
    }
}
