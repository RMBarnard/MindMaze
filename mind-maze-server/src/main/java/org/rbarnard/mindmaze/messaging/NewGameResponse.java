package org.rbarnard.mindmaze.messaging;

public class NewGameResponse {
    private boolean success;
    private String joinCode;

    public boolean getSuccess() {
        return success;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }
}
