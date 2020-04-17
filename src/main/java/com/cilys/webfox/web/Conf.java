package com.cilys.webfox.web;

import com.cilys.webfox.web.Handler.WebSocketHandler;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.*;
import com.cilys.webfox.web.controller.sys.user.SysUserController;
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

        arp.addMapping(SQLParam.T_TOKEN, SQLParam.USER_ID, TokenModel.class);

        arp.addMapping(SQLParam.T_ROOM, SQLParam.ROOM_ID, RoomModel.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new LogInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new WebSocketHandler());
    }
}