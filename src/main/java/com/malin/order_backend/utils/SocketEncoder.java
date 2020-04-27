package com.malin.order_backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malin.order_backend.dataobj.SocketMessage;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * socket 消息编码器
 */
public class SocketEncoder implements Encoder.Text<SocketMessage> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(SocketMessage socketMessage) {
        try {
            return objectMapper.writeValueAsString(socketMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
