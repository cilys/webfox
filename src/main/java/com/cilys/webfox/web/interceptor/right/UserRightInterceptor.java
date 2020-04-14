package com.cilys.webfox.web.interceptor.right;

import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.BaseInterceptor;
import com.cilys.webfox.web.model.UserRoleModel;
import com.jfinal.aop.Invocation;

/**
 * Created by admin on 2018/2/22.
 */
public class UserRightInterceptor extends BaseInterceptor {

    @Override
    public void intercept(Invocation inv) {
        String userId = getUserId(inv);

        String accessUrl = inv.getActionKey();

        UserRoleModel m = UserRoleModel.getUserRoleRight(userId, accessUrl);
        if (m == null){
            renderJson(inv, Param.C_RIGHT_LOW, createTokenByOs(inv), null);
            return;
        }
        if (SQLParam.STATUS_ENABLE.equals(m.get(SQLParam.STATUS))){
            inv.invoke();
        }else {
            renderJson(inv, Param.C_RIGHT_REFUSE, createTokenByOs(inv), null);
        }
    }
}
