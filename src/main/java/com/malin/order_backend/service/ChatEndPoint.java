package com.malin.order_backend.service;

import com.malin.order_backend.dataobj.SocketMessage;
import com.malin.order_backend.utils.SocketEncoder;
import com.malin.order_backend.utils.SocketDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *  websocket 服务端站点
 */
@Slf4j
@Component
@ServerEndpoint(value = "/chat/{username}",
        encoders = {SocketEncoder.class},
        decoders = {SocketDecoder.class})
public class ChatEndPoint {

    private Session session;

    private static Set<ChatEndPoint> chatEndPoints = new CopyOnWriteArraySet<>();

    private static Map<String, String> users = new HashMap<>();

    /**
     * 打开 socket 连接
     *
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        log.debug("Websocket is opening ");

        this.session = session;
        chatEndPoints.add(this);
        users.put(session.getId(), username);
        log.debug("username: {}", username);

        SocketMessage message = new SocketMessage();
        message.setFrom(username);
        message.setContent("Connected !!!, online user: " + users.size());
        broadcast(message);
        log.debug("online user: {}", users.size());
    }

    /**
     * 发送信息
     *
     */
    @OnMessage
    public void onMessage(Session session, SocketMessage message) {
        message.setFrom(users.getOrDefault(session.getId(), ""));
        log.debug("message: {}", message.toString());

        broadcast(message);
        log.debug("online user: {}", users.size());
    }

    /**
     * 关闭 socket 连接
     *
     */
    @OnClose
    public void onClose(Session session) {
        log.debug("Socket is closing .");
        log.debug("username: {}", users.getOrDefault(session.getId(), ""));

        chatEndPoints.remove(this);
        users.remove(session.getId());

        SocketMessage message = new SocketMessage();
        message.setFrom(users.getOrDefault(session.getId(), ""));
        message.setContent("Socket disconnected .");
        broadcast(message);
        log.debug("online user: {}", users.size());
    }

    /**
     * 异常
     *
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("Socket exception", throwable);
        log.debug("online user: {}", users.size());
    }

    /**
     * 信息发送
     *
     */
    private static void broadcast(SocketMessage message) {
        for (ChatEndPoint chatEndPoint : chatEndPoints) {
            synchronized (chatEndPoint) {
                try {
                    chatEndPoint.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
