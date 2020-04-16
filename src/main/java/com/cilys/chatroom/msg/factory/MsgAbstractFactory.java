package com.cilys.chatroom.msg.factory;

public abstract class MsgAbstractFactory<T> {

    public abstract String createMsg(String fromUser, String toUser, T msg);

}
