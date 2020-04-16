package com.cilys.chatroom.msg.factory.text;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.MsgTextFactory;

public class QRCodeTextFactor extends MsgTextFactory {
    public QRCodeTextFactor() {
        super(MsgType.QRCODE_TEXT);
    }
}
