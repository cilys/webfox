package com.cilys.webfox.web.interceptor.room;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.BaseInterceptor;
import com.cilys.webfox.web.model.RoomModel;
import com.jfinal.aop.Invocation;

public class RoomIdInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String roomId = getParam(inv, SQLParam.ROOM_ID);
        if (StrUtils.isEmpty(roomId)){
            renderJson(inv, Param.C_ROOM_ID_NULL, null, null);
            return;
        }
        if (RoomModel.queryById(roomId) == null){
            renderJson(inv, Param.C_ROOM_NOT_EXIST, null, null);
            return;
        }
        inv.invoke();
    }
}
