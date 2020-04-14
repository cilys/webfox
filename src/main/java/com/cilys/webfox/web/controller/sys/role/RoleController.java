package com.cilys.webfox.web.controller.sys.role;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.BaseController;
import com.cilys.webfox.web.model.RoleModel;

/**
 * Created by admin on 2018/2/22.
 */
public class RoleController extends BaseController {

    public void addRole(){
        String roleName = getParam(SQLParam.ROLE_NAME);
        if (StrUtils.isEmpty(roleName)){
            renderJsonFailed(Param.C_ROLE_NAME_NULL,null);
            return;
        }
        if (RoleModel.roleNameIsExist(roleName)){
            renderJsonFailed(Param.C_ROLE_NAME_EXISTS,null);
            return;
        }
        if (RoleModel.insert(roleName, getParam(SQLParam.STATUS, SQLParam.STATUS_ENABLE))){
            renderJsonSuccess(null);
            return;
        }else {
            renderJsonFailed(Param.C_ROLE_ADD_FAILED,null);
            return;
        }
    }

    public void updateRoleStatus(){
        String roleId = getParam(SQLParam.ROLE_ID);
        String status = getParam(SQLParam.STATUS);
        if (RoleModel.updateStatus(roleId, status)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_ROLE_STATUS_UPDATE_FAILED, null);
        }
    }

    public void delRole(){
        String roleId = getParam(SQLParam.ROLE_ID);
        if (RoleModel.del(roleId)){
            renderJsonSuccess(null);
            return;
        }else {
            renderJsonFailed(Param.C_ROLE_DEL_FAILED,null);
            return;
        }
    }

    public void getRoles(){
        renderJsonSuccess(RoleModel.getRoles());
    }
}