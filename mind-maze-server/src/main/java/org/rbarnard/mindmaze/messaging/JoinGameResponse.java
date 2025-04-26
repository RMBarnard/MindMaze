package org.rbarnard.mindmaze.messaging;

public class JoinGameResponse {
    private boolean success;
    private String joinCode;

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
