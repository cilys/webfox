package com.cilys.chatroom.msg.cache;

import com.cilys.chatroom.msg.MsgCacheBean;
import com.cilys.chatroom.msg.schedule.MsgSchedule;
import com.cilys.chatroom.websocket.WebSocketServer;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 消息缓存队列，负责把消息先缓存起来
 */
public class MsgCacheQueue {

    private static PriorityBlockingQueue<MsgCacheBean> msgQueue = new PriorityBlockingQueue<>();

    public static void put(String msg, WebSocketServer server){
        if (msgQueue == null){
            msgQueue = new PriorityBlockingQueue<>();
        }
        msgQueue.put(MsgCacheBean.createMsgBean(msg, server));

        MsgSchedule.sendTask();
    }

    public static MsgCacheBean take(){
        if (msgQueue == null){
            return null;
        }
        try {
            return msgQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MsgCacheBean poll(){
        if (msgQueue == null){
            return null;
        }
        return msgQueue.poll();
    }

    public static MsgCacheBean poll(long timeout, TimeUnit unit){
        if (msgQueue == null){
            return null;
        }
        try {
            return msgQueue.poll(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
