package com.cilys.chatroom.threadpools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoopTaskThreadPool {
    private static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    public static void startTimer(Runnable task){
        if (task == null){
            throw new NullPointerException("The timer tast is null..");
        }
        if (timer == null){
            timer = Executors.newSingleThreadScheduledExecutor();
        }
        timer.scheduleWithFixedDelay(task, 5, 5, TimeUnit.SECONDS);
    }

    public static void stopTimer(){
        if (timer != null){
            timer.shutdownNow();
        }
    }
}
