package com.cilys.chatroom.websocket;

import com.cilys.chatroom.checkInOut.CheckInOutQRCode;
import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.msg.factory.MsgDispatch;
import com.cilys.chatroom.utils.Utils;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 连接缓存集合
 */
public class SessionCacheMap {
    //线程安全的有序map，ConcurrentMap在遍历时如果插入元素，无法遍历到；而ConcurrentSkipListMap可以遍历到
    private static ConcurrentSkipListMap<String, WebSocketServer> sessionMap = new ConcurrentSkipListMap<>();

    public static boolean add(String userName, WebSocketServer server){
        return add(userName, server, true);
    }

    public static boolean add(String userName, WebSocketServer server, boolean closeLastSession){
        if (Utils.isEmpty(userName)){
            return false;
        }
        if (server == null){
            return false;
        }

        if (sessionMap == null){
            sessionMap = new ConcurrentSkipListMap<>();
        }
        WebSocketServer ws = sessionMap.getOrDefault(userName, null);
        if (ws == null) {

        } else {
            if (closeLastSession) {
                //关闭上一个连接
                ws.close();
            }
        }
        sessionMap.put(userName, server);
        return true;
    }

    public static boolean remove(String userName){
        if (Utils.isEmpty(userName)){
            return false;
        }
        sessionMap.remove(userName);
        return true;
    }

    //普通文本消息，把待发给聊天室的消息，发送到缓存队列里
    public static void sendRoomTextMsg(String fromUser, String roomNumber, String msg, boolean sendToMyself){
        sendRoomMsg(MsgType.ROOM_TEXT, fromUser, roomNumber, msg, sendToMyself);
    }

    public static void sendRoomTextNotifyMsg(String fromUser, String roomNumber, String msg){
        sendRoomMsg(MsgType.ROOM_TEXT_NOTIFY, fromUser, roomNumber, msg, true);
    }

    public static <T>void sendRoomMsg(int msgType, String fromUser, String roomNumber, T msg, boolean sendToMyself){
        if (Utils.isEmpty(fromUser, roomNumber)){
            return;
        }
        if (msg == null){
            return;
        }
        if (sessionMap == null || sessionMap.isEmpty()){
            return;
        }
        for (String user : sessionMap.keySet()){
            if (fromUser.equals(user)){
                if (sendToMyself){
                    MsgDispatch.dispatch(msgType, fromUser, user, msg, sessionMap.get(user));
                }
            }else {
                WebSocketServer server = sessionMap.get(user);
                if (server != null && roomNumber.equals(server.getRoomNumber())) {
                    MsgDispatch.dispatch(msgType, fromUser, user, msg, sessionMap.get(user));
                }
            }
        }
    }

    public static <T>void sendFriendMsg(int msgType, String fromUser, String toUser, T msg){
        if (Utils.isEmpty(fromUser, toUser)){
            return;
        }
        if (msg == null){
            return;
        }
        if (sessionMap == null || sessionMap.isEmpty()){
            return;
        }
        MsgDispatch.dispatch(msgType, fromUser, toUser, msg, sessionMap.get(toUser));
    }


    public final static long TIME_OUT = 15000;
    /** 心跳规则：
     * 1、心跳超时默认为15s
     * 2、由客户端主动发起心跳ping包，服务端回应心跳pong包
     * 3、服务端每隔 心跳超时 * 2 + 5，检测一次。对超过1次检测时间但未超过2次检测时间的客户端，主动发起ping包；超过2次检测时间的客户端，进行关闭
     */
    public static void checkTimeOut(){
        if (sessionMap == null || sessionMap.isEmpty()){
            return;
        }
        Iterator<String> it = sessionMap.keySet().iterator();
        while (it.hasNext()){
            String user = it.next();
            if (Utils.isEmpty(user)){
                it.remove();
            }else {
                WebSocketServer server = sessionMap.get(user);
                if (server != null) {
                    long diffTime = System.currentTimeMillis() - server.getLastReceiveMsg();
                    if (diffTime < TIME_OUT) {

                    } else if (diffTime > 2 * TIME_OUT + 5000) {
                        server.close();
                        return;
                    }else {
                        sendHeartPing(user, server);
                    }

                    if (WebSocketServer.ROOM_NUMBER_CHECK_IN_OUT.equals(server.getRoomNumber())){
                        CheckInOutQRCode(user, server);
                    }
                }
            }
        }
    }

    /**
     * 发送心跳包请求
     */
    public static void sendHeartPing(String userName, WebSocketServer server){
        MsgDispatch.dispatch(MsgType.HEART_PING, "system", userName, "HeartPing", server);
    }

    /**
     * 响应心跳包
     */
    public static void sendHeartPong(String userName, WebSocketServer server){
        MsgDispatch.dispatch(MsgType.HEART_PONG, "system", userName, "HeartPing", server);
    }

    private static void CheckInOutQRCode(String user, WebSocketServer server){
        if (Utils.isEmpty(user)){
            return;
        }
        if (server == null){
            return;
        }
        if (System.currentTimeMillis() - server.getLastPushCheckInOutQRCodeTime() > 60000){
            pushCheckInOutQRCode(user, server);
        }
    }
    public static void pushCheckInOutQRCode(String toUser, WebSocketServer server){
        if (Utils.isEmpty(toUser)){
            return;
        }
        if (server != null){
            MsgDispatch.dispatch(MsgType.QRCODE_TEXT, "system", toUser, CheckInOutQRCode.qrCode(), server);
            server.setLastPushCheckInOutQRCodeTime();
        }
    }

    public static List<Map<String, Object>> getRoomMembers(String roomNumber){
        if (Utils.isEmpty(roomNumber)){
            return null;
        }
        if (sessionMap == null || sessionMap.isEmpty()){
            return null;
        }
        List<Map<String, Object>> members = new ArrayList<>();
        Iterator<String> it = sessionMap.keySet().iterator();
        while (it.hasNext()){
            String user = it.next();
            if (Utils.isEmpty(user)){
                it.remove();
            }else {
                WebSocketServer server = sessionMap.get(user);
                if (server != null) {
                    if (roomNumber.equals(server.getRoomNumber())){
                        Map<String, Object> map = new HashMap<>();
                        map.put("user", user);
                        map.put("userStatus", server.getUserStatus());
                        members.add(map);
                    }
                }
            }
        }
        return members;
    }

    public static void updateUserStatus(String userName, String userStatus){
        if (Utils.isEmpty(userName, userStatus)){
            return;
        }
        if (sessionMap == null || sessionMap.isEmpty()){
            return ;
        }
        Iterator<String> it = sessionMap.keySet().iterator();
        while (it.hasNext()){
            String user = it.next();
            if (!userName.equals(user)){
                continue;
            }
            if (Utils.isEmpty(user)){
                it.remove();
            }else {
                WebSocketServer server = sessionMap.get(user);
                if (server != null) {
                    server.setUserStatus(userStatus);
                }
            }
        }
    }
}