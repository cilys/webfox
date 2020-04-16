package com.cilys.chatroom.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cilys.chatroom.msg.MsgBean;
import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.utils.Utils;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * websocket连接器
 */
@ServerEndpoint(value = "/room/{roomNumber}/{userName}")
public class WebSocketServer {
    private Session session;        //全局Session
    private long lastReceiveMsg;    //上一次收到消息
    private String roomNumber;      //聊天室号码
    private String userName;        //用户名称
    private String userStatus = "0";      //用户状态，0默认正常状态、1正在答题
    private long lastPushCheckInOutQRCodeTime;  //上一次推送二维码（扫码打卡）的时间
    public final static String ROOM_NUMBER_CHECK_IN_OUT = "checkInOut"; //扫码的唯一房间号码

    public void setLastPushCheckInOutQRCodeTime() {
        this.lastPushCheckInOutQRCodeTime = System.currentTimeMillis();
    }

    public long getLastPushCheckInOutQRCodeTime() {
        return lastPushCheckInOutQRCodeTime;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public long getLastReceiveMsg() {
        return lastReceiveMsg;
    }

    @OnOpen
    public void OnOpen(Session session, @PathParam("roomNumber") String roomNumber,
                       @PathParam("userName") String userName){
        this.session = session;
        this.roomNumber = roomNumber;
        this.lastReceiveMsg = System.currentTimeMillis();
        this.userName = userName;

        Utils.log("roomNumber = " + roomNumber + "<--->userName = " + userName + "<--->进入了聊天室...");

        //给当前聊天室内的所有人推一条消息，某个用户加入了聊天室

        SessionCacheMap.add(userName, this);

        SessionCacheMap.sendRoomTextNotifyMsg(userName, roomNumber, userName + "已加入聊天室...");

        SessionCacheMap.sendRoomMsg(MsgType.ROOM_MEMBER_LIST_RESPONSE, "system", roomNumber, SessionCacheMap.getRoomMembers(roomNumber), false);

        //如果上二维码扫码打卡，登录后给该网页推送一个二维码
        if (ROOM_NUMBER_CHECK_IN_OUT.equals(roomNumber)){
            SessionCacheMap.pushCheckInOutQRCode(userName, this);
        }

    }

    @OnMessage
    public void OnMsg(String msg, @PathParam("roomNumber")String roomNumber, @PathParam("userName") String userName){
        this.lastReceiveMsg = System.currentTimeMillis();

        Utils.log("收到聊天室：" + roomNumber + "<--->userName = " + userName + "<--->发送的消息：" + msg);
        //给同聊天室内的所有人，推送该消息
//        SessionCacheMap.sendRoomMsg(MsgType.ROOM_TEXT, userName, roomNumber, msg, false);
        //json解析消息，并做相应的消息处理
        MsgBean<String> msgBean = JSON.parseObject(msg, new TypeReference<MsgBean<String>>(){}.getType());
        if (msgBean.getMsgType() == MsgType.HEART_PING){
            SessionCacheMap.sendHeartPong(userName, this);
        } else if (msgBean.getMsgType() == MsgType.HEART_PONG){

        } else if (msgBean.getMsgType() == MsgType.ROOM_TEXT){
            SessionCacheMap.sendRoomMsg(MsgType.ROOM_TEXT, userName, roomNumber, msgBean.getMsg(), true);
        } else if (msgBean.getMsgType() == MsgType.ROOM_TEXT_NOTIFY){
            SessionCacheMap.sendRoomMsg(MsgType.ROOM_TEXT_NOTIFY, userName, roomNumber, msgBean.getMsg(), true);
        } else if (msgBean.getMsgType() == MsgType.ROOM_MEMBER_LIST_REQUEST){
            SessionCacheMap.sendFriendMsg(MsgType.ROOM_MEMBER_LIST_RESPONSE, "system", userName, SessionCacheMap.getRoomMembers(roomNumber));
        } else if (msgBean.getMsgType() == MsgType.OPTION_USER_STATUS){
            String s = msgBean.getMsg();
            Map<String, String> map = JSON.parseObject(s, new TypeReference<Map<String, String>>(){}.getType());
//            Map<String, Object> m = JSON.parseObject(s,  new TypeReference<Map<String, Object>>(){}.getType());

            SessionCacheMap.updateUserStatus(map.get("userName"), map.get("userStatus"));

            SessionCacheMap.sendRoomMsg(MsgType.ROOM_MEMBER_LIST_RESPONSE, userName, roomNumber,
                    SessionCacheMap.getRoomMembers(roomNumber), true);
        } else if (msgBean.getMsgType() == MsgType.WHITE_BOARD_DRAW
                    || msgBean.getMsgType() == MsgType.WHITE_BOARD_CLEAR_SCREEN
                ){
            SessionCacheMap.sendRoomMsg(msgBean.getMsgType(), userName, roomNumber, msgBean.getMsg(), false);
        }
    }

    @OnClose
    public void OnClose(Session session, @PathParam("roomNumber")String roomNumber, @PathParam("userName") String userName){
        //退出群聊
        Utils.log("userName = " + userName + "<--->退出聊天室：" + roomNumber);

        SessionCacheMap.remove(userName);

        SessionCacheMap.sendRoomMsg(MsgType.ROOM_MEMBER_LIST_RESPONSE, "system", roomNumber, SessionCacheMap.getRoomMembers(roomNumber), false);
    }

    @OnError
    public void OnError(Throwable e, @PathParam("roomNumber")String roomNumber, @PathParam("userName")String userName) {
        e.printStackTrace();

        Utils.log("userName = " + userName + "<--->OnError 聊天室：" + roomNumber);
    }

    public void close(){
        if (this.session != null && this.session.isOpen()){
            try {
                this.session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isClosed(){

        Utils.log("this.isOpen() = " + this.session.isOpen());
        return this == null || this.session == null || (!this.session.isOpen());
    }

    public boolean sendMsg(String msg){
        if (this.session != null && this.session.isOpen()){
            try {
                synchronized (session) {
                    this.session.getBasicRemote().sendText(msg);

                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}