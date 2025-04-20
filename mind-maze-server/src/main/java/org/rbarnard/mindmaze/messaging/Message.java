package org.rbarnard.mindmaze.messaging;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType typeTag;
    private String payloadJson;

    public MessageType getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(MessageType typeTag) {
        this.typeTag = typeTag;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }
}
