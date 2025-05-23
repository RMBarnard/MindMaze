package org.rbarnard.mindmaze.messaging;

import java.util.Arrays;

public enum MessageType {
    CONNECT(0, MessageDirection.SERVER_TO_CLIENT),
    NEW_GAME_REQUEST(1, MessageDirection.CLIENT_TO_SERVER),
    NEW_GAME_RESPONSE(2, MessageDirection.SERVER_TO_CLIENT),
    JOIN_GAME_REQUEST(3, MessageDirection.CLIENT_TO_SERVER),
    JOIN_GAME_RESPONSE(4, MessageDirection.SERVER_TO_CLIENT),
    GAME_LOBBY_UPDATE_RESPONSE(5, MessageDirection.SERVER_TO_CLIENT),
    GAME_START(6, MessageDirection.SERVER_TO_CLIENT),
    GAME_END(7, MessageDirection.SERVER_TO_CLIENT),
    LEAVE_GAME_REQUEST(8, MessageDirection.CLIENT_TO_SERVER),
    LEAVE_GAME_RESPONSE(9, MessageDirection.SERVER_TO_CLIENT),
    GAME_INFO_REQUEST(10, MessageDirection.CLIENT_TO_SERVER),
    GAME_INFO_RESPONSE(11, MessageDirection.SERVER_TO_CLIENT),
    LOBBY_INFO_RESPONSE(12, MessageDirection.SERVER_TO_CLIENT),
    ;

    private int typeId;

    private MessageDirection direction;

    private MessageType(int id, MessageDirection direction) {
        this.typeId = id;
        this.direction = direction;
    }

    public int getTypeId() {
        return typeId;
    }

    public MessageDirection getDirection() {
        return direction;
    }

    public static MessageType fromId(int id) {
        return Arrays.stream(values()).filter(x -> x.getTypeId() == id).findFirst().get();
    }
}
