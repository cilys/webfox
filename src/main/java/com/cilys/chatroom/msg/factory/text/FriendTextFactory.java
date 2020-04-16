package com.cilys.chatroom.msg.factory.text;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.MsgTextFactory;

/**
 * 单聊文字信息生成器
 */
public class FriendTextFactory extends MsgTextFactory {
    public FriendTextFactory() {
        super(MsgType.FRIEND_TEXT);
    }
}
