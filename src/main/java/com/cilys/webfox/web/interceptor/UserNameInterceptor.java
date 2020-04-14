package com.cilys.webfox.web.interceptor;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.jfinal.aop.Invocation;

/**
 * Created by admin on 2018/2/5.
 */
public class UserNameInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String userName = getParam(inv, SQLParam.USER_NAME, null);

        if (StrUtils.isEmpty(userName)){
//            renderJson(inv, Param.C_USER_NAME_NULL, createTokenByOs(inv),null);
            inv.invoke();
            return;
        }else {
            if (!userName.matches("[(A-Z)|(0-9)|(a-z)|(-)|(_)]{2,32}")){
                renderJson(inv, Param.C_USER_NAME_ILLAGLE, createTokenByOs(inv), null);
                return;
            }
            inv.invoke();
        }
    }
}
