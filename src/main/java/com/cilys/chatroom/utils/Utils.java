package com.cilys.chatroom.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void log(String msg){
        System.out.println("【" + sdf.format(new Date()) + "】" + msg);
    }

    public static boolean isEmpty(String... strs){
        if (strs == null || strs.length < 1){
            return true;
        }
        for (String str : strs){
            if (str == null || str.trim().isEmpty()){
                return true;
            }
        }
        return false;
    }
}
