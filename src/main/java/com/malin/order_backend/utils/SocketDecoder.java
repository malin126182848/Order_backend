package com.malin.order_backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malin.order_backend.dataobj.SocketMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * websocket 消息解码器
 */
public class SocketDecoder implements Decoder.Text<SocketMessage> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SocketMessage decode(String s) {

        try {
            return objectMapper.readValue(s, SocketMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
