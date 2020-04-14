package com.cilys.webfox.web.interceptor.room;

import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.BaseInterceptor;
import com.cilys.webfox.web.model.RoomModel;
import com.jfinal.aop.Invocation;

public class RoomNameExistInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String roomName = getParam(inv, SQLParam.ROOM_NAME, null);

        if (RoomModel.roomNameExist(roomName)){
            renderJson(inv, Param.C_ROOM_NAME_EXIST, null, null);
            return;
        }
        inv.invoke();
    }
}
