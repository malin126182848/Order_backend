package com.malin.order_backend.dataobj;

import lombok.Data;

/**
 * 聊天信息封装类
 */
@Data
public class SocketMessage {

    /**
     * 消息发送者
     */
    private String from;
    /**
     * 消息接收者
     */
    private String to;
    /**
     * 消息内容
     */
    private String content;
}