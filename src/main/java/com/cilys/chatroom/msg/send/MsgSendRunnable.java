package com.cilys.chatroom.msg.send;

import com.cilys.chatroom.msg.MsgCacheBean;
import com.cilys.chatroom.msg.cache.MsgCacheQueue;
import com.cilys.chatroom.utils.Utils;

/**
 * 发送消息的任务
 */
public class MsgSendRunnable implements Runnable {

    @Override
    public void run() {
        MsgCacheBean msgBean = MsgCacheQueue.poll();
        if (msgBean == null){
            return ;
        }
        if (Utils.isEmpty(msgBean.getMsg())){
            return ;
        }
        if (msgBean.getWebSocketServer() == null){
            return ;
        }

        //该连接已关闭，消息默认直接丢弃
        if (msgBean.getWebSocketServer().isClosed()){
            return ;
        }
        Utils.log("发送信息的线程id：" + Thread.currentThread().getId() + "\t消息对象：" + msgBean + "\t消息内容：" + msgBean.getMsg());
        boolean result = msgBean.getWebSocketServer().sendMsg(msgBean.getMsg());
        Utils.log("发送信息的线程id：" + Thread.currentThread().getId() + "\t消息对象：" + msgBean + "\t发送结果：" + result);

        return;
    }
}
