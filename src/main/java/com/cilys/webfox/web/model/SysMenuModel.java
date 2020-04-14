package com.cilys.webfox.web.model;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.SQLParam;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class SysMenuModel extends Model<SysMenuModel> {
    private static SysMenuModel dao = new SysMenuModel();

    public static List<SysMenuModel> queryAll(String status){
        if (StrUtils.isEmpty(status)){
            return dao.find("select * from " + SQLParam.T_SYS_MENU);
        } else {
            return dao.find("select * from " + SQLParam.T_SYS_MENU + " where status = '" + status + "'");
        }
    }


}
