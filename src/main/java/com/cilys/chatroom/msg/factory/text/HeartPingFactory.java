package com.cilys.chatroom.msg.factory.text;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.MsgTextFactory;

public class HeartPingFactory extends MsgTextFactory {
    public HeartPingFactory() {
        super(MsgType.HEART_PING);
    }

}
