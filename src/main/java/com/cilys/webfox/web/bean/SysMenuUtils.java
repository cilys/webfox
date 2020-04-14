package com.cilys.webfox.web.bean;

import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.model.SysMenuModel;

import java.util.ArrayList;
import java.util.List;

public class SysMenuUtils {

    public static List<SysMenu> queryAll(String status){
        List<SysMenuModel> ls = SysMenuModel.queryAll(status);
        if (ls == null || ls.size() < 1){
            return null;
        }
        List<SysMenu> sourceDatas = new ArrayList<>();
        for (SysMenuModel m : ls) {
            SysMenu menu = new SysMenu();
            menu.setId(m.get(SQLParam.SYS_MENU_ID));
            menu.setpId(m.get(SQLParam.P_ID));
            menu.setHref(m.get(SQLParam.HREF));
            menu.setName(m.get(SQLParam.SYS_MENU_NAME));
            sourceDatas.add(menu);
        }
        return getTreeList(0, sourceDatas);
    }

    private static  List<SysMenu> getTreeList(int topId, List<SysMenu> entityList) {
        List<SysMenu> resultList = new ArrayList<>();

        //获取顶层元素集合
        int parentId;
        for (SysMenu entity : entityList) {
            parentId = entity.getpId();
            if (topId == parentId) {
                resultList.add(entity);
            }
        }

        //获取每个顶层元素的子数据集合
        for (SysMenu entity : resultList) {
            entity.setChildren(getSubList(entity.getId(), entityList));
        }

        return resultList;
    }

    private  static  List<SysMenu> getSubList(int id, List<SysMenu> entityList) {
        List<SysMenu> childList=new ArrayList<>();
        int parentId;

        //子集的直接子对象
        for (SysMenu entity : entityList) {
            parentId=entity.getpId();
            if(id == parentId){
                childList.add(entity);
            }
        }

        //子集的间接子对象
        for (SysMenu entity : childList) {
            entity.setChildren(getSubList(entity.getId(), entityList));
        }

        //递归退出条件
        if(childList.size()==0){
            return null;
        }

        return childList;
    }

}
