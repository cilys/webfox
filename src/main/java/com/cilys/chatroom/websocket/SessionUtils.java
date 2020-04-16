package com.cilys.chatroom.websocket;

import com.cilys.chatroom.utils.Utils;

/**
 * 连接处理工具
 * 功能：
 * 1、把单聊消息，发送给对方（暂未实现，后续实现单聊功能）
 * 2、把聊天室的消息，发送给对应的用户
 */
public class SessionUtils {

    /**
     * 处理聊天室的消息
     */
    public static void doChatRoomMsg(String fromUser, String roomNumber, String msg){
        if (Utils.isEmpty(fromUser, roomNumber)){
            return;
        }
        if (msg == null){
            return;
        }

    }

}
