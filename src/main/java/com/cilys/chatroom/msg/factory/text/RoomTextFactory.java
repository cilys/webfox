package com.cilys.chatroom.msg.factory.text;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.MsgTextFactory;

/**
 * 聊天室文字信息生成器
 */
public class RoomTextFactory extends MsgTextFactory {

    public RoomTextFactory() {
        super(MsgType.ROOM_TEXT);
    }

}
