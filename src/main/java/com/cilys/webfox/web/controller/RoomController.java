package com.cilys.webfox.web.controller;

import com.cily.utils.base.time.TimeUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.room.RoomIdInterceptor;
import com.cilys.webfox.web.interceptor.room.RoomNameExistInterceptor;
import com.cilys.webfox.web.interceptor.room.RoomNameNullInterceptor;
import com.cilys.webfox.web.model.RoomModel;
import com.jfinal.aop.Before;

public class RoomController extends BaseController {

    @Before({RoomNameNullInterceptor.class, RoomNameExistInterceptor.class})
    public void addRoom(){
        String roomName = getParam(SQLParam.ROOM_NAME);
        String createUserId = getParam(SQLParam.USER_ID);
        String timeOut = getParam(SQLParam.TIME_OUT, getOutTime());
        String status = getParam(SQLParam.STATUS, SQLParam.STATUS_ENABLE);

        if (RoomModel.roomNameExist(roomName)){
            renderJsonFailed(Param.C_ROOM_NAME_EXIST, null);
            return;
        }
        if (RoomModel.insert(roomName, createUserId, timeOut, status)){
            renderJsonSuccess(null);
            return;
        }else {
            renderJsonFailed(Param.C_ROOM_ADD_FAILED, null);
        }
    }

    public void getRooms(){
        String status = getParam(SQLParam.STATUS);
        renderJsonSuccess(RoomModel.queryAll(getPageNumber(), getPageSize(), status));
    }

    @Before({RoomIdInterceptor.class})
    public void updateRoomInfo(){
        String roomId = getParam(SQLParam.ROOM_ID);
        String roomName = getParam(SQLParam.ROOM_NAME);
        String createUserId = getParam(SQLParam.USER_ID);
        String status = getParam(SQLParam.STATUS);
        String timeOut = getParam(SQLParam.TIME_OUT);
        if (RoomModel.updateRoomInfo(roomId, roomName, createUserId, timeOut, status)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_ROOM_UPDATE_FAILED, null);
        }
    }

    @Before({RoomIdInterceptor.class})
    public void delRoom(){
        String roomId = getParam(SQLParam.ROOM_ID);
        if (RoomModel.del(roomId)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_ROOM_DEL_FAILED, null);
        }
    }

    private String getOutTime(){
        long currentTime = System.currentTimeMillis();
        long timeOut = currentTime + 365 * 24 * 60 * 60 * 1000;
        return TimeUtils.milToStr(timeOut, null);
    }

}
