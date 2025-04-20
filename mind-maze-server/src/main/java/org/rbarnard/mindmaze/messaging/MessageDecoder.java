package org.rbarnard.mindmaze.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String jsonMessage) throws DecodeException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Message message = objectMapper.readValue(jsonMessage, Message.class);
            return message;
        } catch (Exception e) {
            throw new DecodeException(jsonMessage, "Unable to decode message", e);
        }
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue(jsonMessage, Message.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }
}
