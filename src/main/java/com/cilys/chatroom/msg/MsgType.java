package com.cilys.chatroom.msg;

/**
 * 消息类型
 * 规则：
 * 1、100以内，websocket服务使用
 *      98心跳请求
 *      99心跳回应
 *
 * 2、100 ～ 999备用
 * 3、1000以上为业务消息
 */
public interface MsgType {
    int HEART_PING = 98;
    int HEART_PONG = 99;
    int ROOM_TEXT = 1001;
    int ROOM_TEXT_NOTIFY = 1002;

    int FRIEND_TEXT = 1021;

    int ROOM_MEMBER_LIST_REQUEST = 1003;    //请求获取聊天室的群成员
    int ROOM_MEMBER_LIST_RESPONSE = 1004;    //响应获取聊天室的群成员


    int QRCODE_TEXT = 1101;  //二维码文本

    int OPTION_USER_STATUS = 1201;  //操作用户的状态

    int WHITE_BOARD_DRAW = 1301;    //白板绘图，远程同步给其它用户
    int WHITE_BOARD_CLEAR_SCREEN = 1302;    //白板绘图，清屏，远程同步给其它用户

    int ROOM_AUDIO_LIST = 1310;     //通知获取音频列表
}
