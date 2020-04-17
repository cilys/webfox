package com.cilys.chatroom.websocket;

import com.cilys.chatroom.utils.Utils;

/**
 * 心跳包相关工具类
 *
 * 心跳规则：
 * 1、心跳超时默认为15秒
 * 2、由客户端主动发起心跳ping包，服务端回应心跳pong包
 * 3、服务端每隔 心跳超时 * 2 + 5，检测一次。对超过1次检测时间但未超过2次检测时间的客户端，主动发起ping包；超过2次检测时间的客户端，进行关闭
 */

public class LoopRunnable implements Runnable {
    private long lastCheckTimeOutTime;


    @Override
    public void run() {
//        Utils.log("定时器到了...");
        if (System.currentTimeMillis() - lastCheckTimeOutTime < SessionCacheMap.TIME_OUT){
//            Utils.log("时间太短，无需检测超时...");
        } else {
            lastCheckTimeOutTime = System.currentTimeMillis();
//            Utils.log("开始检测超时sesseion...");
            SessionCacheMap.checkTimeOut();
        }
    }
}
