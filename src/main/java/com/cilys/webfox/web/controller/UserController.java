package com.cilys.webfox.web.controller;

import com.alibaba.fastjson.JSON;
import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.interceptor.*;
import com.cilys.webfox.web.model.UserModel;
import com.cilys.webfox.web.utils.ResUtils;
import com.cilys.webfox.web.utils.RootUserIdUtils;
import com.cilys.webfox.web.utils.TokenUtils;
import com.cilys.webfox.web.utils.UserUtils;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

import java.util.List;

/**
 * Created by admin on 2018/1/30.
 */

public class UserController extends BaseController {

    @Clear({LoginedInterceptor.class})
    @Before({UserNameInterceptor.class, PwdInterceptor.class})
    public void login(){
        String userName = getParam(SQLParam.USER_NAME);
        String pwd = getParam(SQLParam.PWD);

        String deviceImei = getDeviceImei();

        UserModel um = UserModel.getUserByUserName(userName);
        if (um == null){
            renderJson(ResUtils.res(Param.C_USER_NOT_EXIST, null, null));
            return;
        }
        if (pwd.equals(um.get(SQLParam.PWD))){
            um.remove(SQLParam.PWD);

            if (Param.REQUEST_SOURCE_WEB.equals(getHeader(Param.OS_TYPE))) {
                if (RootUserIdUtils.isRootUser(um.get(SQLParam.USER_ID))) {
                    String token = TokenUtils.createToken(um.get(SQLParam.USER_ID), deviceImei, null);
                    renderJson(ResUtils.success(token, um));
                    return;
                }else {
                    renderJsonFailed(Param.C_RIGHT_LOW, null);
                }
            }else {
                String token = TokenUtils.createToken(um.get(SQLParam.USER_ID), deviceImei, null);
                renderJson(ResUtils.success(token, um));
            }
            return;
        }else {
            renderJson(ResUtils.res(Param.C_USER_OR_PWD_ERROR, null, null));
            return;
        }
    }

    @Clear({LoginedInterceptor.class})
    @Before({UserNameInterceptor.class, PwdInterceptor.class,
            PhoneInterceptor.class, IdCardInterceptor.class})
    public void regist(){
        UserUtils.regist(this, null, null);
    }

    @Before({UserIdInterceptor.class, PwdInterceptor.class,
            PhoneInterceptor.class, IdCardInterceptor.class})
    public void updateUserInfo(){
        UserUtils.updateUserInfo(this, null, getUserId(), null);
    }

    @Before({SearchInterceptor.class})
    public void search(){
        String searchText = getParam(Param.SEARCH_TEXT);
        renderJsonSuccess(UserModel.searchUser(getParaToInt(Param.PAGE_NUMBER, 1),
                getParaToInt(Param.PAGE_SIZE, 10), searchText, !"1".equals(getHeader("osType"))));
    }

    @Before({UserIdInterceptor.class})
    public void userInfo(){
        String userId = getParam(SQLParam.USER_ID);
        UserModel um = UserModel.getUserByUserId(userId);
        String osType = getHeader("osType");
//        um.remove(SQLParam.PWD);
        if (!"1".equals(osType)) {
            um.set(SQLParam.REAL_NAME, UserModel.formcatRealName(um.get(SQLParam.REAL_NAME, null)));
            um.set(SQLParam.PHONE, UserModel.formcatPhone(um.get(SQLParam.PHONE, null)));
            um.set(SQLParam.ID_CARD, UserModel.formcatIdCard(um.get(SQLParam.ID_CARD, null)));
            um.set(SQLParam.ADDRESS, UserModel.formcatAddress(um.get(SQLParam.ADDRESS, null)));
        }
        renderJsonSuccess(um);
    }

    @Before({PwdInterceptor.class})
    public void changePwd(){
        String pwd = getParam(SQLParam.PWD);
        String newPwd = getParam("newPwd");
        if (StrUtils.isEmpty(newPwd)){
            renderJsonFailed(Param.C_PWD_NEW_NULL, null);
            return;
        }
        if (newPwd.length() > 32){
            renderJsonFailed(Param.C_PWD_ILLAGLE, null);
            return;
        }
        String userId = getUserId();
        UserModel um = UserModel.getUserByUserId(userId);
        if (um == null){
            renderJsonFailed(Param.C_USER_NOT_EXIST, null);
            return;
        }
        if (!pwd.equals(um.get(SQLParam.PWD))){
            renderJsonFailed(Param.C_PWD_NOT_EQUAL, null);
            return;
        }
        if (UserModel.updateUserInfo(userId, newPwd, null, null,
                null, null, null, null, null) == UserModel.USER_INFO_UPDATE_SUCCESS){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_PWD_CHANGE_FAILED, null);
        }
    }

    public void searchUsers(){
        String userIds = getParam("userIds");
        if (StrUtils.isEmpty(userIds)){
            renderJsonFailed(Param.C_USER_ID_NULL, null);
            return;
        }
        List<String> us = JSON.parseArray(userIds, String.class);
        renderJsonSuccess(UserModel.searchUsers(us));
    }
}