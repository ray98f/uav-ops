package com.uav.ops.config.component;

import com.alibaba.fastjson.JSONObject;
import com.uav.ops.config.repository.UavEsRepository;
import com.uav.ops.entity.Uav;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
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

    private String clientId = "";
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

    private static UavEsRepository repository;

    @Autowired
    public void setService(UavEsRepository repository){
        WebSocketServer.repository = repository;
    }


    /**
     * 建立连接
     *
     * @param session
     * @param deviceId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("type") String type, @PathParam("deviceId") String deviceId) {
        this.session = session;
        this.type = type;
        this.deviceId = deviceId;
        this.clientId = type + ":" + deviceId;
        webSocketSet.add(this);
        SESSIONS.add(session);
        if (webSocketMap.containsKey(this.clientId)) {
            sendMessage("[" + this.clientId + "连接] 建立连接失败,已建立连接", this.clientId);
        } else {
            webSocketMap.put(this.clientId, this);
            addOnlineCount();
            log.info("[{}连接] 建立连接, 当前连接数:{}", this.clientId, webSocketMap.size());
        }
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        if (webSocketMap.containsKey(this.clientId)) {
            webSocketMap.remove(this.clientId);
            subOnlineCount();
        }
        log.info("[{}连接] 断开连接, 当前连接数:{}", this.clientId, webSocketMap.size());
    }

    /**
     * 发送错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("[{}连接] 错误原因:{}", this.clientId, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 收到消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("[{}连接] 收到消息:{}", this.clientId, message);
        if ("app".equals(this.type)) {
            sendMassMessage(message);
            repository.save(JSONObject.parseObject(message, Uav.class));
        }
    }

    public void send(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message
     * @param clientId
     */
    public void sendMessage(String message, String clientId) {
        WebSocketServer webSocketServer = webSocketMap.get(clientId);
        if (webSocketServer != null) {
            try {
                send(webSocketServer.session, message);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("[{}连接] 发送消息失败，消息:{}", this.clientId, message);
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
            for (WebSocketServer item : webSocketSet) {
                if (item.session.isOpen() && item.clientId.equals(("app".equals(this.type) ? "pc" : "app")
                        + ":" + this.deviceId)) {
                    send(item.session, message);
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