package com.cilys.webfox.web.interceptor;

import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.utils.RootUserIdUtils;
import com.jfinal.aop.Invocation;

/**
 * Created by 123 on 2018/5/5.
 * 是否是超管
 */
public class SysUserInterceptor extends BaseInterceptor {
    @Override
    public void intercept(Invocation inv) {
        String userId = getUserId(inv);

        if (RootUserIdUtils.isRootUser(userId)) {
            inv.invoke();
        } else {
            renderJson(inv, Param.C_RIGHT_LOW, createTokenByOs(inv), null);
        }
    }
}