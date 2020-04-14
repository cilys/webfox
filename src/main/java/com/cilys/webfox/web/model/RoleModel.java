package com.cilys.webfox.web.model;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.SQLParam;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/2/22.
 */
public class RoleModel extends Model<RoleModel> {
    private static RoleModel dao = new RoleModel();

    public static boolean insert(String roleName, String status){
        if (StrUtils.isEmpty(roleName)){
            return false;
        }
        RoleModel m = new RoleModel();
//        m.set(SQLParam.ROLE_ID, UUIDUtils.getUUID());
        m.set(SQLParam.ROLE_NAME, roleName);
        m.set(SQLParam.STATUS, status);

        return m.save();
    }

    public static boolean updateStatus(String roleId, String status){
        RoleModel m = dao.findById(roleId);
        if (m != null){
            m.set(SQLParam.STATUS, status);
            return m.update();
        }
        return false;
    }

    public static boolean del(String roleId){
        if (StrUtils.isEmpty(roleId)){
            return false;
        }
        return dao.deleteById(roleId);
    }

    public static boolean roleNameIsExist(String roleName){
        if (StrUtils.isEmpty(roleName)){
            return true;
        }
        return dao.findFirst(StrUtils.join("select * from ",
                SQLParam.T_ROLE, " where ", SQLParam.ROLE_NAME,
                " = '", roleName, "';")) != null;
    }

    public static boolean roleIsExist(String roleId){
        if (StrUtils.isEmpty(roleId)){
            return false;
        }
        return dao.findFirst(StrUtils.join(
                "select * from ", SQLParam.T_ROLE,
                " where ", SQLParam.ROLE_ID, " = '", roleId, "';")) != null;
    }

    public static List<RoleModel> getRoles(){
        return dao.find(StrUtils.join("select * from ", SQLParam.T_ROLE));
    }

    private String userId;

    @Override
    protected Map<String, Object> _getAttrs() {
        Map<String, Object> map = super._getAttrs();
        map.put(SQLParam.USER_ID, userId);
        return map;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}