package com.cilys.chatroom.msg.factory;

import com.cilys.chatroom.msg.schedule.MsgSchedule;
import com.cilys.chatroom.utils.Utils;
import com.cilys.chatroom.websocket.WebSocketServer;

/**
 * 消息分发器，负责将消息转换成缓存队列里的消息对象，并将消息缓存到缓存队列里
 */
public class MsgDispatch {

    public static <T>void dispatch(int msgType, String fromUser, String toUser, T msg, WebSocketServer server){
        if (Utils.isEmpty(fromUser, toUser)){
            return;
        }
        if (msg == null){
            return;
        }
        if (server == null){
            return;
        }
        String jsonMsg = MsgFactoryUtils.createMsg(msgType, fromUser, toUser, msg);

        if (jsonMsg == null){
            return;
        }
        MsgSchedule.putCache(jsonMsg, server);
    }



}