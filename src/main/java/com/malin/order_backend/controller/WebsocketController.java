package com.malin.order_backend.controller;

import com.malin.order_backend.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
class WebsocketController {

    @Autowired
    private WebSocketService webSocket;

    @RequestMapping("/sendAllUser")
    public void sendAllUser() {
        webSocket.sendAllMessage("TableOnline");//websocket推送数据
    }

    /**
     * 广播，服务器主动推给连接的客户端
     */
    @RequestMapping("/sendTopic")
        public void sendTopic() {
    }


}



