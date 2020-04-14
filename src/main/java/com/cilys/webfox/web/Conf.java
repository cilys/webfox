package com.cilys.webfox.web;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.*;
import com.cilys.webfox.web.controller.sys.menu.SysMenuController;
import com.cilys.webfox.web.controller.sys.right.RightController;
import com.cilys.webfox.web.controller.sys.role.RoleController;
import com.cilys.webfox.web.controller.sys.role.RoleRightController;
import com.cilys.webfox.web.controller.sys.user.SysUserController;
import com.cilys.webfox.web.controller.sys.user.UserRoleController;
import com.cilys.webfox.web.interceptor.LogInterceptor;
import com.cilys.webfox.web.model.*;
import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.template.Engine;

/**
 * Created by admin on 2018/1/17.
 */
public class Conf extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        PropKit.use("conf.properties");
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class);
        me.add("user", UserController.class);
        me.add("sys/user", SysUserController.class);
        me.add("sys/role", RoleController.class);
//        me.add("record", RecordController.class);
//        me.add("msg", MsgController.class);
        me.add("sys/menu", SysMenuController.class);
        me.add("sys/right", RightController.class);
        me.add("sys/roleRight", RoleRightController.class);
        me.add("sys/userRole", UserRoleController.class);
        me.add("room", RoomController.class);
    }

    @Override
    public void configEngine(Engine me) {

    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0 = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("pwd"));
        me.add(c3p0);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
        arp.setShowSql(PropKit.getBoolean("showSql"));
        me.add(arp);
        arp.addMapping(SQLParam.T_USER, SQLParam.USER_ID, UserModel.class);

        arp.addMapping(SQLParam.T_RIGHT, SQLParam.RIGHT_ID, RightModel.class);
        arp.addMapping(SQLParam.T_RIGHT_ROLE,
                StrUtils.join(SQLParam.RIGHT_ID, ",",
                        SQLParam.ROLE_ID), RoleRightModel.class);
        arp.addMapping(SQLParam.T_ROLE, SQLParam.ROLE_ID, RoleModel.class);
        arp.addMapping(SQLParam.T_USER_ROLE, SQLParam.USER_ID + "," + SQLParam.ROLE_ID, UserRoleModel.class);
//        arp.addMapping(SQLParam.T_RECORD, SQLParam.RECORD_ID, RecordModel.class);
        arp.addMapping(SQLParam.T_TOKEN, SQLParam.USER_ID, TokenModel.class);
//        arp.addMapping(SQLParam.T_MSG, SQLParam.MSG_ID, MsgModel.class);
//        arp.addMapping(SQLParam.T_FRIEND,
//                SQLParam.USER_ID_1 + "," + SQLParam.USER_ID_2, FriendModel.class);

        arp.addMapping(SQLParam.T_SYS_MENU, SQLParam.SYS_MENU_ID, SysMenuModel.class);

        arp.addMapping(SQLParam.T_ROOM, SQLParam.ROOM_ID, RoomModel.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
//        me.add(new LogInterceptor());
//        me.add(new DeviceImeiInterceptor());
//        me.add(new LoginedInterceptor());
//        me.add(new UserRightInterceptor());
        me.add(new LogInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }
}