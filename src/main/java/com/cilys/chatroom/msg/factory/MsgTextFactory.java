package com.cilys.chatroom.msg.factory;

import com.cilys.chatroom.msg.MsgBean;

public class MsgTextFactory extends MsgAbstractFactory<Object> {
    private int msgType;

    public MsgTextFactory(int msgType){
        this.msgType = msgType;
    }

    @Override
    public String createMsg(String fromUser, String toUser, Object msg) {
        return MsgBean.createMsg(msgType, fromUser, toUser, msg);
    }
}
