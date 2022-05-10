package com.uav.ops.config.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket/{type}/{deviceId}")
@Slf4j
public class WebSocketServer {
    private Session session;
    private String deviceId;
    private String type;
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * concurrent包的线程安全set，用来存放每个客户端对应的MyWebSocket对象
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 为了保存在线用户信息，在方法中新建一个list存储一下【实际项目依据复杂度，可以存储到数据库或者缓存】
     */
    private final static List<Session> SESSIONS = Collections.synchronizedList(new ArrayList<>());

    /**
     * 建立连接
     *
     * @param session
     * @param deviceId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId") String deviceId, @PathParam("type") String type) {
        this.session = session;
        this.deviceId = deviceId;
        this.type = type;
        webSocketSet.add(this);
        SESSIONS.add(session);
        if (webSocketMap.containsKey(type + "-" + deviceId)) {
            webSocketMap.remove(type + "-" + deviceId);
            webSocketMap.put(type + "-" + deviceId, this);
        } else {
            webSocketMap.put(type + "-" + deviceId, this);
            addOnlineCount();
        }
        log.info("[{}连接ID:{}] 建立连接, 当前连接数:{}", this.type, this.deviceId, webSocketMap.size());
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        if (webSocketMap.containsKey(type + "-" + deviceId)) {
            webSocketMap.remove(type + "-" + deviceId);
            subOnlineCount();
        }
        log.info("[{}连接ID:{}] 断开连接, 当前连接数:{}", type, deviceId, webSocketMap.size());
    }

    /**
     * 发送错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("[{}连接ID:{}] 错误原因:{}", this.type, this.deviceId, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 收到消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("[{}连接ID:{}] 收到消息:{}", this.type, this.deviceId, message);
    }

    /**
     * 发送消息
     *
     * @param message
     * @param deviceId
     */
    public void sendMessage(String message, String deviceId, String type) {
        WebSocketServer webSocketServer = webSocketMap.get(type + "-" + deviceId);
        if (webSocketServer != null) {
            try {
                webSocketServer.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("[{}连接ID:{}] 发送消息失败, 消息:{}", this.type, this.deviceId, message, e);
            }
        }
    }

    /**
     * 群发消息
     *
     * @param message
     */
    public void sendMassMessage(String message) {
        try {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                    log.info("[{}连接ID:{}] 发送消息:{}", this.type, session.getRequestParameterMap().get("deviceId"), message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前连接数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 当前连接数加一
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 当前连接数减一
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}