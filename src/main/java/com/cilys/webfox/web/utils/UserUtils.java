package com.cilys.webfox.web.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cily.utils.base.StrUtils;
import com.cily.utils.base.UUIDUtils;
import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.BaseController;
import com.cilys.webfox.web.model.UserModel;
import com.jfinal.kit.HttpKit;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by admin on 2018/2/5.
 */
public class UserUtils {

    public static void regist(BaseController c, String userId, String status){
        if(c == null){
            throw new NullPointerException("The Controller is null.");
        }

        String token = c.getHeader(SQLParam.TOKEN);
        String deviceImei = c.getAttr(Param.DEVICE_IMEI);

        String userName = c.getPara(SQLParam.USER_NAME);
        String pwd = c.getPara(SQLParam.PWD);
        String realName = c.getPara(SQLParam.REAL_NAME, null);
        String sex = c.getPara(SQLParam.SEX, null);
        String phone = c.getPara(SQLParam.PHONE, null);
        String address = c.getPara(SQLParam.ADDRESS, null);
        String idCard = c.getPara(SQLParam.ID_CARD, null);

        if (StrUtils.isEmpty(status)){
            status = SQLParam.STATUS_DISABLE;
        }

        if (UserModel.getUserByUserName(userName) != null){
            c.renderJson(ResUtils.res(Param.C_RESIGT_USER_EXISTS,
                    TokenUtils.createToken(userId, deviceImei, token), null));
            return;
        }

        if (UserModel.insert(userName, pwd, realName,
                sex, phone, address, idCard, status)){
            c.renderJson(ResUtils.success(TokenUtils.createToken(
                    userId, deviceImei, token), null));
        }else {
            c.renderJson(ResUtils.res(Param.C_REGIST_FAILURE,
                    TokenUtils.createToken(userId, deviceImei, token), null));
        }
    }

    public static void registByJsonData(BaseController c, String userId){
        String token = c.getHeader(SQLParam.TOKEN);
        String deviceImei = c.getAttr(Param.DEVICE_IMEI);

        String params = HttpKit.readData(c.getRequest());
        Logger.getLogger(UserUtils.class.getSimpleName()).info("params = " + params);
        System.out.println("params = " + params);
        Map<String, Object> m = JSON.parseObject(params,  new TypeReference<Map<String, Object>>(){}.getType());
        UserModel um = new UserModel();
        um.put(m);
        um.removeNullValueAttrs();
        um.set(SQLParam.USER_ID, UUIDUtils.getUUID());
        if (UserModel.getUserByUserName(um.getStr(SQLParam.USER_NAME)) != null){
            c.renderJson(ResUtils.res(Param.C_RESIGT_USER_EXISTS,
                    TokenUtils.createToken(userId, deviceImei, token), null));
            return;
        }

        if (um.save()) {
            c.renderJsonSuccess(um);
        }else {
            c.renderJson(ResUtils.res(Param.C_REGIST_FAILURE,
                    TokenUtils.createToken(userId, deviceImei, token), null));
        }
    }

    public static void updateUserInfo(BaseController c, String rootUserId , String userId, String status){
        if(c == null){
            throw new NullPointerException("The Controller is null.");
        }
        String token = c.getHeader(SQLParam.TOKEN);
        String deviceImei = c.getAttr(Param.DEVICE_IMEI);

        String pwd = c.getPara(SQLParam.PWD);
        String realName = c.getPara(SQLParam.REAL_NAME, null);
        String sex = c.getPara(SQLParam.SEX, null);
        String phone = c.getPara(SQLParam.PHONE, null);
        String address = c.getPara(SQLParam.ADDRESS, null);
        String idCard = c.getPara(SQLParam.ID_CARD, null);

        if (StrUtils.isEmpty(userId)){
            c.renderJson(ResUtils.res(Param.C_USER_ID_NULL, TokenUtils.createToken(rootUserId, deviceImei, token), null));
            return;
        }

        int updateResult = UserModel.updateUserInfo(userId, pwd, realName,
                sex, phone, address, idCard, status, null);
        if (updateResult == UserModel.USER_INFO_UPDATE_SUCCESS){
            c.renderJson(ResUtils.success(TokenUtils.createToken(
                    rootUserId, deviceImei, token), null));
        }else if (updateResult == UserModel.USER_NOT_EXIST){
            c.renderJson(ResUtils.res(Param.C_USER_NOT_EXIST,
                    TokenUtils.createToken(rootUserId, deviceImei, token), null));
        }else if (updateResult == UserModel.USER_INFO_NO_UPDATE){
            c.renderJson(ResUtils.res(Param.C_USER_INFO_NO_UPDATE,
                    TokenUtils.createToken(rootUserId, deviceImei, token), null));
        }else {
            c.renderJson(ResUtils.res(Param.C_USER_INFO_UPDATE_FAILURE,
                    TokenUtils.createToken(rootUserId, deviceImei, token), null));
        }
    }

    public static void updateUserInfoByJsonData(BaseController c, String rootUserId){
        if(c == null){
            throw new NullPointerException("The Controller is null.");
        }
        String token = c.getHeader(SQLParam.TOKEN);
        String deviceImei = c.getAttr(Param.DEVICE_IMEI);

        String params = HttpKit.readData(c.getRequest());
        Logger.getLogger(UserUtils.class.getSimpleName()).info("params = " + params);
        System.out.println("params = " + params);
        Map<String, Object> m = JSON.parseObject(params,  new TypeReference<Map<String, Object>>(){}.getType());
        UserModel um = new UserModel();
        um.put(m);
        um.removeNullValueAttrs();

        String userId = um.getStr(SQLParam.USER_ID);
        String pwd = um.getStr(SQLParam.PWD);
        String realName = um.getStr(SQLParam.REAL_NAME);
        String sex = um.getStr(SQLParam.SEX);
        String phone = um.getStr(SQLParam.PHONE);
        String address = um.getStr(SQLParam.ADDRESS);
        String idCard = um.getStr(SQLParam.ID_CARD);
        String status = um.getStr(SQLParam.STATUS);
        String userIdentify = um.getStr(SQLParam.USER_IDENTIFY);

        if (StrUtils.isEmpty(userId)){
            c.renderJson(ResUtils.res(Param.C_USER_ID_NULL, TokenUtils.createToken(rootUserId, deviceImei, token), null));
            return;
        }

        int updateResult = UserModel.updateUserInfo(userId, pwd, realName,
                sex, phone, address, idCard, status, null);
        if (updateResult == UserModel.USER_INFO_UPDATE_SUCCESS){
            c.renderJson(ResUtils.success(TokenUtils.createToken(
                    rootUserId, deviceImei, token), null));
        }else if (updateResult == UserModel.USER_NOT_EXIST){
            c.renderJson(ResUtils.res(Param.C_USER_NOT_EXIST,
                    TokenUtils.createToken(rootUserId, deviceImei, token), null));
        }else if (updateResult == UserModel.USER_INFO_NO_UPDATE){
            c.renderJson(ResUtils.res(Param.C_USER_INFO_NO_UPDATE,
                    TokenUtils.createToken(rootUserId, deviceImei, token), null));
        }else {
            c.renderJson(ResUtils.res(Param.C_USER_INFO_UPDATE_FAILURE,
                    TokenUtils.createToken(rootUserId, deviceImei, token), null));
        }
    }

    private static boolean isEmpty(String... strs){
        if (strs == null){
            return true;
        }
        if (strs.length < 1){
            return true;
        }
        for (String s : strs){
            if (!StrUtils.isEmpty(s)){
                return false;
            }
        }
        return true;
    }
}
