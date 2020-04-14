package com.cilys.webfox.web.controller.sys.menu;

import com.cilys.webfox.web.bean.SysMenuUtils;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.BaseController;


/**
 * 系统后台控制页，左侧菜单
 */
public class SysMenuController extends BaseController {

    public void sysMenu(){
        renderJsonSuccess(SysMenuUtils.queryAll(SQLParam.STATUS_ENABLE));
    }



}
