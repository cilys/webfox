package com.cilys.chatroom.msg;

import com.cilys.chatroom.websocket.WebSocketServer;

/**
 * 缓存队列里的消息对象
 */
public class MsgCacheBean implements Comparable<MsgCacheBean> {
    private final static int DEFAULT_PRIORITY = 50;
    private int msgPriority = DEFAULT_PRIORITY;
    private String msg;
    private WebSocketServer webSocketServer;

    public void setMsgPriority(int msgPriority) {
        this.msgPriority = msgPriority;
    }

    public int getMsgPriority() {
        return msgPriority;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public WebSocketServer getWebSocketServer() {
        return webSocketServer;
    }

    public void setWebSocketServer(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Override
    public int compareTo(MsgCacheBean o) {
        return this.getMsgPriority() > o.getMsgPriority() ? 1 : (this.getMsgPriority() < o.getMsgPriority() ? -1 : 0);
    }

    public static MsgCacheBean createMsgBean(String msg, WebSocketServer server){
        return createMsgBean(DEFAULT_PRIORITY, msg, server);
    }

    public static MsgCacheBean createMsgBean(int msgPriority, String msg, WebSocketServer server){
        MsgCacheBean bean = new MsgCacheBean();
        bean.setMsgPriority(msgPriority < 0 ? DEFAULT_PRIORITY : msgPriority);
        bean.setMsg(msg);
        bean.setWebSocketServer(server);

        return bean;
    }
}
