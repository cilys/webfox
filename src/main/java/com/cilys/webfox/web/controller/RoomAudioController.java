package com.cilys.webfox.web.controller;

import com.cilys.chatroom.msg.MsgType;
import com.cilys.chatroom.websocket.SessionCacheMap;
import com.cilys.chatroom.websocket.SessionUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.interceptor.OptionMethodInterceptor;
import com.jfinal.aop.Before;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Before({OptionMethodInterceptor.class})
public class RoomAudioController extends BaseImagesController {

    //上传音频文件
    public void postAudio(){
        String roomNumber = getHeader("roomNumber");
//        String roomNumber = "0";

        try {
            File fs = new File("../" + roomNumber);

            if (!fs.exists()){
                fs.mkdirs();
            }
            Map<String, String> map = getImages("../" + roomNumber);

            SessionCacheMap.sendRoomMsg(MsgType.ROOM_AUDIO_LIST, "system", roomNumber, "加载录音文件列表", true);

            renderJsonSuccess(null);
        } catch (Exception e) {
            e.printStackTrace();
            renderJsonFailed(Param.C_FILE_UPLOAD_FAILED, null);
        }
    }

    public void getAudios(){
        String roomNumber = getParam("roomNumber");
        File fs = new File("../" + roomNumber);

        if (!fs.exists()){
            fs.mkdirs();
        }
        File[] files = fs.listFiles();
        List<Map<String, String>> ls = new ArrayList<>();
        for (File f : files){
            if (f.getName().endsWith(".mp3")) {
                Map<String, String> map = new HashMap<>();
                map.put("fileName", f.getName());
                ls.add(map);
            }
        }
        renderJsonSuccess(ls);
    }

    public void getAudio(){
        String roomNumber = getParam("roomNumber");
        String fileName = getParam("fileName");
        File f = new File("../" + roomNumber + "/" + fileName);
        renderFile(f);
    }

}
