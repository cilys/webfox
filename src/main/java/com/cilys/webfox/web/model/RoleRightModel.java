package com.cilys.webfox.web.model;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.SQLParam;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by admin on 2018/2/22.
 */
public class RoleRightModel extends Model<RoleRightModel> {
    private static RoleRightModel dao = new RoleRightModel();

    public static boolean insert(String rightId, String roleId){
        if (StrUtils.isEmpty(rightId) || StrUtils.isEmpty(roleId)){
            return false;
        }
        RoleRightModel m = new RoleRightModel();
        m.set(SQLParam.RIGHT_ID, rightId);
        m.set(SQLParam.ROLE_ID, roleId);
        return m.save();
    }

    public static boolean delByRightId(String rightId){
        if (StrUtils.isEmpty(rightId)){
            return false;
        }
        return Db.delete("delete from ", SQLParam.T_RIGHT_ROLE,
                " where ", SQLParam.RIGHT_ID, " = '", rightId, "';") > 0;
    }

    public static boolean delByRoleId(String roleId){
        if (StrUtils.isEmpty(roleId)){
            return false;
        }
        return Db.delete(StrUtils.join("delete from ",
                SQLParam.T_RIGHT_ROLE,
                " where ", SQLParam.ROLE_ID, " = '", roleId, "';")) > 0;
    }

    public static boolean delByRightIdAndRoleId(String rightId, String roleId){
        if (StrUtils.isEmpty(rightId) || StrUtils.isEmpty(roleId)) {
            return false;
        }
        return Db.delete(StrUtils.join("delete from ",
                SQLParam.T_RIGHT_ROLE, " where ",
                SQLParam.RIGHT_ID, " = '", rightId, "' and ",
                SQLParam.ROLE_ID, " = '", roleId, "';")) > 0;
    }

    public static Page<RoleRightModel> getRoleRightByRoleId(
            String roleId, int pageNumber, int pageSize){
        if (StrUtils.isEmpty(roleId)){
            return null;
        }
        if (pageSize < 1){
            pageSize = 10;
        }
        if (pageNumber < 1){
            pageNumber = 1;
        }

        return dao.paginate(pageNumber, pageSize, "select * ",
                StrUtils.join(" from " , SQLParam.T_RIGHT_ROLE,
                        " left join ", SQLParam.T_RIGHT, " on ",
                        SQLParam.T_RIGHT_ROLE, ".", SQLParam.RIGHT_ID,
                        " = ", SQLParam.T_RIGHT, ".", SQLParam.RIGHT_ID,
                        " where ", SQLParam.T_RIGHT_ROLE, ".", SQLParam.ROLE_ID,
                        " = '", roleId, "';"));
    }

    public static List<RoleRightModel> getRoleRightByRoleId(String roleId){
        return dao.find(
                StrUtils.join("select * from " , SQLParam.T_RIGHT_ROLE,
                        " left join ", SQLParam.T_RIGHT, " on ",
                        SQLParam.T_RIGHT_ROLE, ".", SQLParam.RIGHT_ID,
                        " = ", SQLParam.T_RIGHT, ".", SQLParam.RIGHT_ID,
                        " where ", SQLParam.T_RIGHT_ROLE, ".", SQLParam.ROLE_ID,
                        " = '", roleId, "';")
        );
    }
}
