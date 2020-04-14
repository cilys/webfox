package com.cilys.webfox.web.interceptor.room;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.BaseInterceptor;
import com.jfinal.aop.Invocation;

public class RoomNameNullInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String roomName = getParam(inv, SQLParam.ROOM_NAME, null);
        if (StrUtils.isEmpty(roomName)){
            renderJson(inv, Param.C_ROOM_NAME_NULL, null, null);
            return;
        }

        inv.invoke();
    }
}
