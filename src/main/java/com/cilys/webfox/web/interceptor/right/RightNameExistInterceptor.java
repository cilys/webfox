package com.cilys.webfox.web.interceptor.right;

import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.BaseInterceptor;
import com.cilys.webfox.web.model.RightModel;
import com.jfinal.aop.Invocation;

public class RightNameExistInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String rightName = getParam(inv, SQLParam.RIGHT_NAME, null);

        if (RightModel.rightNameIsExist(rightName)){
            renderJson(inv, Param.C_RIGHT_NAME_EXIST, null, null);
            return;
        }
        inv.invoke();
    }
}
