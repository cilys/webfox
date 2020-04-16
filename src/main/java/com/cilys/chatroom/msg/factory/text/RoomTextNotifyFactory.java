package com.cilys.chatroom.msg.factory.text;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.MsgTextFactory;

/**
 * 聊天室，文字类通知
 */
public class RoomTextNotifyFactory extends MsgTextFactory {
    public RoomTextNotifyFactory() {
        super(MsgType.ROOM_TEXT_NOTIFY);
    }
}
