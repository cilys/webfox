package com.cilys.webfox.web.controller.sys.role;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.BaseController;
import com.cilys.webfox.web.interceptor.RoleIdInterceptor;
import com.cilys.webfox.web.interceptor.right.RightIdInterceptor;
import com.cilys.webfox.web.model.RightModel;
import com.cilys.webfox.web.model.RoleRightModel;
import com.jfinal.aop.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/23.
 */
@Before({RoleIdInterceptor.class})
public class RoleRightController extends BaseController {

    /**
     * 获取角色的权限
     */
    public void getRoleRights(){
        String roleId = getParam(SQLParam.ROLE_ID);
        renderJsonSuccess(RoleRightModel.getRoleRightByRoleId(roleId,
                        getParaToInt(Param.PAGE_NUMBER, 1),
                        getParaToInt(Param.PAGE_SIZE, 10)));
    }

    /**
     * 获取全部的权限列表，获取该角色的权限
     */
    public void getAllRightsAndRoleRight(){
        String roleId = getParam(SQLParam.ROLE_ID);
        List<RightModel> rights = RightModel.getAllRights(null);
        List<RoleRightModel> roleRights = RoleRightModel.getRoleRightByRoleId(roleId);

        if (rights == null || rights.size() < 0){
            renderJsonSuccess(roleRights);
        } else {
            Map<String, RoleRightModel> map = new HashMap<>();
            if (roleRights != null && roleRights.size() > 0){
                for (RoleRightModel m : roleRights){
                    map.put(m.get(SQLParam.RIGHT_ID), m);
                }
            }
            for (RightModel m : rights){
                String rightId = m.getStr(SQLParam.RIGHT_ID);
                if (!StrUtils.isEmpty(rightId)){
                    RoleRightModel roleRightModel = map.get(rightId);
                    if (roleRightModel != null){
//                        m.put(SQLParam.ROLE_ID, roleId);
                        m.setRoleId(roleId);
                    }
                }
            }
            renderJsonSuccess(rights);
        }
    }

    /**
     * 批量添加角色的权限
     */
    public void addRoleRights(){
        String roleId = getParam(SQLParam.ROLE_ID);
        String rightIds = getParam(SQLParam.RIGHT_ID);
        if (StrUtils.isEmpty(rightIds)){

            return;
        } else {
            rightIds = rightIds.trim();
            if (StrUtils.isEmpty(rightIds)){

                return;
            }

            if (rightIds.endsWith(",")){
                rightIds = rightIds.substring(0, rightIds.length() - 1);
            }
            String[] rs = rightIds.split(",");
            if (rs != null && rs.length > 0){

            }
        }
//        if (RoleRightModel.insert(rightId, roleId)){
//            renderJsonSuccess(null);
//        }else {
//            renderJsonFailed(Param.C_RIGHT_ROLE_ADD_FAILED, null);
//        }
    }

    /**
     * 添加角色权限
     */
    @Before({RightIdInterceptor.class})
    public void addRoleRight(){
        String roleId = getParam(SQLParam.ROLE_ID);
        String rightId = getParam(SQLParam.RIGHT_ID);
        if (RoleRightModel.insert(rightId, roleId)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_RIGHT_ROLE_ADD_FAILED, null);
        }
    }

    /**
     * 删除角色权限
     */
    @Before({RightIdInterceptor.class})
    public void delRoleRight(){
        String roleId = getParam(SQLParam.ROLE_ID);
        String rightId = getParam(SQLParam.RIGHT_ID);
        if (RoleRightModel.delByRightIdAndRoleId(rightId, roleId)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_RIGHT_ROLE_DEL_FAILED, null);
        }
    }
}