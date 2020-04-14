package com.cilys.webfox.web.interceptor.right;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.BaseInterceptor;
import com.cilys.webfox.web.model.RightModel;
import com.jfinal.aop.Invocation;

/**
 * Created by admin on 2018/2/23.
 * 请求参数中，权限id是否为空，是否存在该权限
 */
public class RightIdInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String rightId = getParam(inv, SQLParam.RIGHT_ID);
        if (StrUtils.isEmpty(rightId)){
            renderJson(inv, Param.C_RIGHT_ID_NULL, createTokenByOs(inv), null);
            return;
        }
        if (RightModel.rightIsExist(rightId)){
            inv.invoke();
        }else {
            renderJson(inv, Param.C_RIGHT_NOT_EXIST, createTokenByOs(inv), null);
            return;
        }
    }
}
