package com.cilys.webfox.web.controller.sys.user;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.BaseController;
import com.cilys.webfox.web.interceptor.RoleIdInterceptor;
import com.cilys.webfox.web.interceptor.UserIdInterceptor;
import com.cilys.webfox.web.model.RoleModel;
import com.cilys.webfox.web.model.UserRoleModel;
import com.jfinal.aop.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/23.
 */
public class UserRoleController extends BaseController {

    @Before({UserIdInterceptor.class})
    public void getUserRoles(){
        String userId = getParam(SQLParam.USER_ID);
        renderJsonSuccess(UserRoleModel.getUserRolesByUserId(userId, true));
    }

    /**
     * 获取全部角色和用户的角色
     */
    @Before({UserIdInterceptor.class})
    public void getAllRolesAndUserRoles(){
        String userId = getParam(SQLParam.USER_ID);
        List<RoleModel> roles = RoleModel.getRoles();
        if (roles == null || roles.size() < 1){
            renderJsonSuccess(roles);
            return;
        }
        List<UserRoleModel> userRoles = UserRoleModel.getUserRolesByUserId(userId, false);
        if (userRoles == null || userRoles.size() < 1){
            renderJsonSuccess(roles);
            return;
        }


        Map<String, UserRoleModel> map = new HashMap<>();
        for (UserRoleModel urm : userRoles){
            String roleId = urm.getStr(SQLParam.ROLE_ID);
            if (!StrUtils.isEmpty(roleId)) {
                map.put(roleId, urm);
            }
        }
        for (RoleModel m : roles){
            String roleId = m.getStr(SQLParam.ROLE_ID);
            if (!StrUtils.isEmpty(roleId)){
                if (map.get(roleId) != null){
                    m.setUserId(map.get(roleId).getStr(SQLParam.USER_ID));
                }
            }
        }

        renderJsonSuccess(roles);
    }

    /**
     * 给用户分配角色
     */
    @Before({UserIdInterceptor.class, RoleIdInterceptor.class})
    public void addUserRole(){
        String userId = getParam(SQLParam.USER_ID);
        String roleId = getParam(SQLParam.ROLE_ID);

        if (UserRoleModel.insertUserRole(userId, roleId)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_USER_ROLE_ADD_FAILED, null);
        }
    }

    /**
     * 删除用户角色
     */
    @Before({UserIdInterceptor.class, RoleIdInterceptor.class})
    public void delUserRole(){
        String userId = getParam(SQLParam.USER_ID);
        String roleId = getParam(SQLParam.ROLE_ID);

        if (UserRoleModel.delByUserIdAndRoleId(userId, roleId)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_USER_ROLE_DEL_FAILED, null);
        }
    }
}
