package com.cilys.chatroom.msg.factory.text;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.MsgTextFactory;

public class HeartPongFactory extends MsgTextFactory {
    public HeartPongFactory() {
        super(MsgType.HEART_PONG);
    }
}
