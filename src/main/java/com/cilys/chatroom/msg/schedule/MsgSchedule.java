package com.cilys.chatroom.msg.schedule;

import com.cilys.chatroom.msg.cache.MsgCacheQueue;
import com.cilys.chatroom.msg.send.MsgSendRunnable;
import com.cilys.chatroom.msg.send.MsgSendThreadPool;
import com.cilys.chatroom.websocket.WebSocketServer;

/**
 * 消息调度器，
 * 功能：
 * 1、负责把原始消息，放入到缓存队列里
 * 2、负责把消息从缓存队列里，调度到消息发送队列里
 */
public class MsgSchedule {

    public static void sendTask(){
        MsgSendRunnable runnable = new MsgSendRunnable();
        MsgSendThreadPool.put(runnable);
    }

    public static void putCache(String msg, WebSocketServer server){
        MsgCacheQueue.put(msg, server);
    }
}
