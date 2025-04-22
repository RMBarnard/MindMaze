package org.rbarnard.mindmaze.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    public Message decode(String jsonMessage) throws DecodeException {
        try {
            LOG.info("Decoding message " + jsonMessage);
            ObjectMapper objectMapper = new ObjectMapper();
            Message message = objectMapper.readValue(jsonMessage, Message.class);
            return message;
        } catch (Exception e) {
            throw new DecodeException(jsonMessage, "Unable to decode message", e);
        }
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        LOG.info("willDecode check: {}", jsonMessage);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue(jsonMessage, Message.class);
            return true;
        } catch (Exception e) {
            LOG.error("Unable to decode.", e);
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
