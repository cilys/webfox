package com.cilys.webfox.web.interceptor;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.model.RoleModel;
import com.jfinal.aop.Invocation;

/**
 * Created by admin on 2018/2/23.
 * 请求参数中，roleId是否null，是否存在该用户
 */
public class RoleIdInterceptor extends BaseInterceptor {

    @Override
    public void intercept(Invocation inv) {
        String roleId = getParam(inv, SQLParam.ROLE_ID);
        if (StrUtils.isEmpty(roleId)){
            renderJson(inv, Param.C_ROLE_ID_NULL, createTokenByOs(inv), null);
            return;
        }
        if (RoleModel.roleIsExist(roleId)){
            inv.invoke();
        }else {
            renderJson(inv, Param.C_ROLE_NOT_EXIST, createTokenByOs(inv), null);
        }
    }
}
