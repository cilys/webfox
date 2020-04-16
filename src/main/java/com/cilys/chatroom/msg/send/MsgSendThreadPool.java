package com.cilys.chatroom.msg.send;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 发送消息的线程池，负责把队列里的消息，通过子线程发送出去
 */
public class MsgSendThreadPool {

    private static ExecutorService executor = new ThreadPoolExecutor(5, 5, 10L,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void put(Runnable task){
        if (task == null){
            return;
        }
        if (executor == null){
            executor = new ThreadPoolExecutor(5, 5, 10L,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        }
        executor.submit(task);
    }

    public static void shutdown(){
        if (executor != null){
            if (!executor.isShutdown()) {
                executor.shutdown();
            }
        }
    }

    public static void shuwdownNow(){
        if (executor != null){
            executor.shutdownNow();
        }
    }

}
