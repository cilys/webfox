package com.cilys.chatroom.listener;

import com.cilys.chatroom.msg.send.MsgSendThreadPool;
import com.cilys.chatroom.threadpools.LoopTaskThreadPool;
import com.cilys.chatroom.utils.Utils;
import com.cilys.chatroom.websocket.LoopRunnable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Utils.log("contextInitialized");

        LoopTaskThreadPool.startTimer(new LoopRunnable());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Utils.log("contextDestroyed");
        MsgSendThreadPool.shuwdownNow();
        LoopTaskThreadPool.stopTimer();
    }
}
