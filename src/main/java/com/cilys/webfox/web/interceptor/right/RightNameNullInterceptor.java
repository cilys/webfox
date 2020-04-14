package com.cilys.webfox.web.interceptor.right;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.BaseInterceptor;
import com.jfinal.aop.Invocation;

public class RightNameNullInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String rightName = getParam(inv, SQLParam.RIGHT_NAME, null);
        if (StrUtils.isEmpty(rightName)){
            renderJson(inv, Param.C_RIGHT_NAME_NULL, null, null);
            return;
        }
        inv.invoke();
    }
}
