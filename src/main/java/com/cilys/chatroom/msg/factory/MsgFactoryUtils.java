package com.cilys.chatroom.msg.factory;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.text.*;

public class MsgFactoryUtils {

    public static <T>String createMsg(int msgType, String fromUser, String toUser, T msg){
        return msgFactory(msgType).createMsg(fromUser, toUser, msg);
    }

    public static MsgAbstractFactory msgFactory(int msgType){
        if (msgType == MsgType.HEART_PING){
            return new HeartPingFactory();
        } else if (msgType == MsgType.HEART_PONG) {
            return new HeartPongFactory();
        } else if (msgType == MsgType.QRCODE_TEXT) {
            return new QRCodeTextFactor();
        } else if (msgType == MsgType.ROOM_TEXT){
            return new RoomTextFactory();
        } else if (msgType == MsgType.ROOM_TEXT_NOTIFY){
            return new RoomTextNotifyFactory();
        } else if (msgType == MsgType.FRIEND_TEXT){
            return new FriendTextFactory();
        } else {
            return new MsgTextFactory(msgType);
        }
    }


}
