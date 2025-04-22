package org.rbarnard.mindmaze.messaging;

import java.io.Serializable;

public class Message implements Serializable {
    private int typeId;
    private String payloadJson;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }
}
