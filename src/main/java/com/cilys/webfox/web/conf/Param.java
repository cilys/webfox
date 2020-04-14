package com.cilys.webfox.web.conf;

import com.cily.utils.base.StrUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/1/30.
 */
public class Param {
    public final static String DEVICE_IMEI = "deviceImei";

    public final static String PAGE_NUMBER = "pageNumber";
    public final static String PAGE_SIZE = "pageSize";
    public final static String SEARCH_TEXT = "searchText";
    public final static String OS_TYPE = "osType";


    //错误码规则：0成功；1~99系统错误；1001~1999用户相关错误
    public final static String C_SUCCESS = "0";

    public final static String C_SERVER_ERROR = "11";

    public final static String C_404 = "404";
    public final static String C_500 = "500";

    public final static String C_USER_NOT_EXIST = "1001";
    public final static String C_USER_NOT_LOGIN = "1002";
    public final static String C_USER_LOGIN_ON_OTHER = "1003";
    public final static String C_USER_OR_PWD_ERROR = "1006";
    public final static String C_USER_NAME_NULL = "1011";
    public final static String C_USER_NAME_ILLAGLE = "1012";

    public final static String C_USER_DEL_FAILED = "1021";

    public final static String C_PWD_NULL = "1031";
    public final static String C_PWD_ILLAGLE = "1032";
    public final static String C_PWD_NEW_NULL = "1033";
    public final static String C_PWD_NOT_EQUAL = "1034";
    public final static String C_PWD_CHANGE_FAILED = "1035";

    public final static String C_REAL_NAME_TOO_LONG = "1041";

    public final static String C_PHONE_ILLAGLE = "1051";

    public final static String C_ID_CARD_ILLAGLE = "1061";


    public final static String C_RESIGT_USER_EXISTS = "1101";
    public final static String C_REGIST_FAILURE = "1106";


    public final static String C_USER_ID_NULL = "1111";
    public final static String C_TOKEN_NULL = "1112";
    public final static String C_TOKEN_ERROR = "1113";

    public final static String C_USER_INFO_UPDATE_FAILURE = "1121";
    public final static String C_USER_INFO_NO_UPDATE = "1122";

    public final static String C_ROLE_NAME_NULL = "1201";
    public final static String C_ROLE_NAME_EXISTS = "1202";
    public final static String C_ROLE_ID_NULL = "1203";
    public final static String C_ROLE_NOT_EXIST = "1204";

    public final static String C_ROLE_ADD_FAILED = "1206";
    public final static String C_ROLE_STATUS_UPDATE_FAILED = "1207";
    public final static String C_ROLE_DEL_FAILED = "1211";

    public final static String C_USER_ROLE_DEL_FAILED = "1216";
    public final static String C_USER_ROLE_ADD_FAILED = "1217";

    public final static String C_RIGHT_LOW = "1251";
    public final static String C_RIGHT_ID_NULL = "1252";
    public final static String C_RIGHT_NOT_EXIST = "1253";
    public final static String C_RIGHT_STATUS_CHANGE_FAILED = "1254";
    public final static String C_RIGHT_REFUSE = "1255";
    public final static String C_RIGHT_NAME_EXIST = "1256";
    public final static String C_RIGHT_NAME_NULL = "1257";
    public final static String C_RIGHT_ADD_FAILED = "1258";
    public final static String C_RIGHT_DEL_FAILED = "1259";

    public final static String C_RIGHT_ROLE_ADD_FAILED = "1261";
    public final static String C_RIGHT_ROLE_DEL_FAILED = "1262";
    public final static String C_ADD_PROJECT_FAILED = "2001";

    public final static String C_RECORD_ADD_PARAM_NULL = "2101";
    public final static String C_RECORD_NAME_NULL = "2102";
    public final static String C_RECORD_NUM_NULL = "2103";
    public final static String C_RECORD_ADD_FAILED = "2104";
    public final static String C_RECORD_LEVEL_NULL = "2105";
    public final static String C_RECORD_DEL_FAILED = "2106";
    public final static String C_RECORD_CONTENT_UPDATE_FAILED = "2107";
    public final static String C_RECORD_STATUS_UPDATE_FAILED = "2108";

    public final static String C_RECORD_ID_NULL = "2111";
    public final static String C_RECORD_ID_NOT_EXIST = "2112";
    public final static String C_NO_RIGHT_DEL_OTHER_USER_ROCORD = "2121";

    public final static String C_SEARCH_TEXT_NULL = "3001";
    public final static String C_SEARCH_TEXT_TOO_LONG = "3002";

    public final static String C_MSG_TO_USER_ID_NULL = "4001";
    public final static String C_MSG_TO_USER_ID_NOT_EXIST = "4002";
    public final static String C_MSG_INSERT_FAILED = "4003";
    public final static String C_MSG_NULL = "4004";
    public final static String C_MSG_DEL_FAILED = "4010";

    public final static String C_PRODUCT_ADD_FAILED = "5001";
    public final static String C_PRODUCT_UPDATE_FAILED = "5002";
    public final static String C_PRODUCT_DEL_FAILED = "5003";
    public final static String C_ORDER_CAR_ADD_FAILED = "5011";
    public final static String C_ORDER_CAR_UPDATE_FAILED = "5012";
    public final static String C_ORDER_CAR_DEL_FAILED = "5011";



    public final static String C_ROOM_NAME_EXIST = "5051";
    public final static String C_ROOM_ADD_FAILED = "5052";
    public final static String C_ROOM_UPDATE_FAILED = "5053";
    public final static String C_ROOM_DEL_FAILED = "5054";
    public final static String C_ROOM_ID_NULL = "5055";
    public final static String C_ROOM_NOT_EXIST = "5056";
    public final static String C_ROOM_NAME_NULL = "5057";




    private final static Map<String, String> failureInfo = new HashMap<>();

    static {
        failureInfo.put(C_SUCCESS, "操作成功");
        failureInfo.put(C_SERVER_ERROR, "系统内部异常");
        failureInfo.put(C_USER_NOT_EXIST, "用户不存在");
        failureInfo.put(C_USER_OR_PWD_ERROR, "用户名或密码错误");
        failureInfo.put(C_USER_NAME_NULL, "用户名为空");
        failureInfo.put(C_RESIGT_USER_EXISTS, "该账号已存在");
        failureInfo.put(C_REGIST_FAILURE, "注册失败");
        failureInfo.put(C_USER_ID_NULL, "用户id为空");
        failureInfo.put(C_TOKEN_NULL, "token为空");
        failureInfo.put(C_TOKEN_ERROR, "用户未登录或登录已失效");
        failureInfo.put(C_USER_INFO_UPDATE_FAILURE, "更新用户信息失败");
        failureInfo.put(C_USER_INFO_NO_UPDATE, "用户信息无更新");
        failureInfo.put(C_USER_NOT_LOGIN, "该用户未登录或登录已失效");
        failureInfo.put(C_USER_LOGIN_ON_OTHER, "该用户已在其他地方登录");
        failureInfo.put(C_ROLE_NAME_NULL, "角色名称为空");
        failureInfo.put(C_RIGHT_LOW, "权限不足");
        failureInfo.put(C_ROLE_NAME_EXISTS, "角色名称已存在");
        failureInfo.put(C_ROLE_ADD_FAILED, "添加角色失败");
        failureInfo.put(C_ROLE_DEL_FAILED, "删除角色失败");
        failureInfo.put(C_USER_ROLE_DEL_FAILED, "删除用户角色失败");
        failureInfo.put(C_USER_ROLE_ADD_FAILED, "添加用户角色失败");
        failureInfo.put(C_ROLE_ID_NULL, "角色id为空");
        failureInfo.put(C_ROLE_NOT_EXIST, "该角色不存在");
        failureInfo.put(C_RIGHT_ID_NULL, "权限id为空");
        failureInfo.put(C_RIGHT_NOT_EXIST, "权限不存在");
        failureInfo.put(C_RIGHT_STATUS_CHANGE_FAILED, "修改权限状态失败");
        failureInfo.put(C_RIGHT_ROLE_ADD_FAILED, "添加角色权限失败");
        failureInfo.put(C_RIGHT_ROLE_DEL_FAILED, "删除角色权限失败");
        failureInfo.put(C_RIGHT_REFUSE, "权限被禁用");
        failureInfo.put(C_ADD_PROJECT_FAILED, "添加项目失败");
        failureInfo.put(C_RECORD_ADD_PARAM_NULL, "参数为空");
        failureInfo.put(C_RECORD_NAME_NULL, "名称为空");
        failureInfo.put(C_RECORD_NUM_NULL, "编号为空");
        failureInfo.put(C_RECORD_LEVEL_NULL, "等级为空");
        failureInfo.put(C_RECORD_ADD_FAILED, "发布物料信息失败");
        failureInfo.put(C_USER_DEL_FAILED, "删除用户失败");
        failureInfo.put(C_USER_NAME_ILLAGLE, "用户名必须是2至30位的字母或数字或中划线或下划线");
        failureInfo.put(C_PWD_NULL, "密码为空");
        failureInfo.put(C_PWD_ILLAGLE, "密码不合法");

        failureInfo.put(C_404, "找不到路径");
        failureInfo.put(C_500, "系统内部异常");
        failureInfo.put(C_PHONE_ILLAGLE, "手机号码不合法");
        failureInfo.put(C_ID_CARD_ILLAGLE, "身份证号不合法");
        failureInfo.put(C_SEARCH_TEXT_NULL, "待搜索的内容为空");
        failureInfo.put(C_SEARCH_TEXT_TOO_LONG, "搜索内容过长");
        failureInfo.put(C_RECORD_ID_NULL, "物料id为空");
        failureInfo.put(C_RECORD_ID_NOT_EXIST, "物料信息不存在");
        failureInfo.put(C_NO_RIGHT_DEL_OTHER_USER_ROCORD, "无权删除其他人的发布");
        failureInfo.put(C_PWD_NEW_NULL, "新密码为空");
        failureInfo.put(C_PWD_NOT_EQUAL, "原密码错误");
        failureInfo.put(C_PWD_CHANGE_FAILED, "修改密码失败");
        failureInfo.put(C_MSG_TO_USER_ID_NULL, "对方用户id为空");
        failureInfo.put(C_MSG_TO_USER_ID_NOT_EXIST, "对方用户不存在");
        failureInfo.put(C_MSG_INSERT_FAILED, "插入记录失败");
        failureInfo.put(C_MSG_NULL, "消息内容为空");
        failureInfo.put(C_MSG_DEL_FAILED, "消息失败");
        failureInfo.put(C_RECORD_CONTENT_UPDATE_FAILED, "更新发布内容失败");
        failureInfo.put(C_RECORD_STATUS_UPDATE_FAILED, "更新发布状态失败");
        failureInfo.put(C_PRODUCT_ADD_FAILED, "添加商品失败");
        failureInfo.put(C_PRODUCT_UPDATE_FAILED, "更新商品信息失败");
        failureInfo.put(C_PRODUCT_DEL_FAILED, "删除商品失败");
        failureInfo.put(C_ORDER_CAR_ADD_FAILED, "添加到购物车失败");
        failureInfo.put(C_ORDER_CAR_UPDATE_FAILED, "更新购物车商品失败");
        failureInfo.put(C_ORDER_CAR_DEL_FAILED, "删除购物车商品失败");
        failureInfo.put(C_ROLE_STATUS_UPDATE_FAILED, "更新角色状态失败");
        failureInfo.put(C_RIGHT_NAME_EXIST, "权限名称已存在");
        failureInfo.put(C_RIGHT_NAME_NULL, "权限名称为空");
        failureInfo.put(C_RIGHT_ADD_FAILED, "添加权限失败");
        failureInfo.put(C_RIGHT_DEL_FAILED, "删除权限失败");
        failureInfo.put(C_ROOM_NAME_EXIST, "房间名称已存在");
        failureInfo.put(C_ROOM_ADD_FAILED, "添加房间失败");
        failureInfo.put(C_ROOM_UPDATE_FAILED, "更新房间信息失败");
        failureInfo.put(C_ROOM_DEL_FAILED, "删除房间失败");
        failureInfo.put(C_ROOM_ID_NULL, "房间id为空");
        failureInfo.put(C_ROOM_NOT_EXIST, "房间不存在");
        failureInfo.put(C_ROOM_NAME_NULL, "房间名称已存在");
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
//        failureInfo.put(, );
    }

    public final static String getMsg(String code) {
        if (StrUtils.isEmpty(code)) {
            code = C_SERVER_ERROR;
        }
        return failureInfo.get(code);
    }

    public final static String REGX_PHONE = "1[0-9]{10}";
    public final static String REQUEST_SOURCE_WEB = "1";
    public final static String REQUEST_SOURCE_ANDROID = "2";
    public final static String REQUEST_SOURCE_IOS = "3";
}