package com.cilys.chatroom.msg;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息体
 */
public class MsgBean<T> implements Serializable {
    private int msgType = MsgType.ROOM_TEXT;        //消息类型，默认聊天室文字消息
    private String fromUser;    //发送者
    private String toUser;      //接收者
    private String msgTime;     //发送时间，格式化
    private long time;       //发送时间，时间戳
    private T msg;         //消息内容

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public static <T> MsgBean createMsgBean(int msgType, String fromUser, String toUser, T msg){
        MsgBean bean = new MsgBean();
        bean.setMsgType(msgType);
        bean.setFromUser(fromUser);
        bean.setToUser(toUser);
        long time = System.currentTimeMillis();
        bean.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bean.setMsgTime(sdf.format(new Date(time)));

        bean.setMsg(msg);

        return bean;
    }

    public static <T>String createMsg(int msgType, String fromUser, String toUser, T msg){
        return JSON.toJSONString(createMsgBean(msgType, fromUser, toUser, msg));
    }

}