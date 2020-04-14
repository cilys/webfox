package com.cilys.webfox.web.controller.sys.right;

import com.cilys.webfox.web.conf.Param;
import com.cilys.webfox.web.conf.SQLParam;
import com.cilys.webfox.web.controller.BaseController;
import com.cilys.webfox.web.interceptor.right.RightIdInterceptor;
import com.cilys.webfox.web.interceptor.right.RightNameExistInterceptor;
import com.cilys.webfox.web.interceptor.right.RightNameNullInterceptor;
import com.cilys.webfox.web.model.RightModel;
import com.jfinal.aop.Before;

/**
 * Created by admin on 2018/2/23.
 */
public class RightController extends BaseController {

    @Before({RightNameNullInterceptor.class, RightNameExistInterceptor.class})
    public void addRight(){
        String status = getParam(SQLParam.STATUS);
        String rightName = getParam(SQLParam.RIGHT_NAME);
        String accessUrl = getParam(SQLParam.ACCESS_URL);

        if (SQLParam.STATUS_ENABLE.equals(status)
                || SQLParam.STATUS_DISABLE.equals(status)){

        }else {
            status = SQLParam.STATUS_DISABLE;
        }
        if (RightModel.insert(rightName, accessUrl, status)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_RIGHT_ADD_FAILED, null);
        }
    }

    /**
     * 根据权限状态，获取所有权限
     */
    public void getRights(){
        int pageNumber = getParaToInt(Param.PAGE_NUMBER, 1);
        int pageSize = getParaToInt(Param.PAGE_SIZE, 10);
        String status = getParam(SQLParam.STATUS);
        renderJsonSuccess(RightModel.getRights(pageNumber, pageSize, status));
    }

    /**
     * 修改权限状态信息
     */
    @Before({RightIdInterceptor.class})
    public void updateRight(){
        String rightId = getParam(SQLParam.RIGHT_ID);
        String status = getParam(SQLParam.STATUS);
        String rightName = getParam(SQLParam.RIGHT_NAME);
        String accessUrl = getParam(SQLParam.ACCESS_URL);

        if (SQLParam.STATUS_ENABLE.equals(status)
                || SQLParam.STATUS_DISABLE.equals(status)){

        }else {
            status = SQLParam.STATUS_DISABLE;
        }
        if (RightModel.update(rightId, rightName, accessUrl, status)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_RIGHT_STATUS_CHANGE_FAILED, null);
        }
    }

    @Before({RightIdInterceptor.class})
    public void delRight(){
        String rightId = getParam(SQLParam.RIGHT_ID);
        if (RightModel.del(rightId)){
            renderJsonSuccess(null);
        }else {
            renderJsonFailed(Param.C_RIGHT_DEL_FAILED, null);
        }
    }
}