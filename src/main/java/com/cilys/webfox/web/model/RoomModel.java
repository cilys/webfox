package com.cilys.webfox.web.model;

import com.cily.utils.base.StrUtils;
import com.cilys.webfox.web.conf.SQLParam;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class RoomModel extends Model<RoomModel> {
    private static RoomModel dao = new RoomModel();

    public static boolean insert(String roomName, String createUserId, String timeOut, String status){
        RoomModel m = new RoomModel();
        m.set(SQLParam.ROOM_NAME, roomName);
        m.set(SQLParam.CREATE_USER_ID, createUserId);
        m.set(SQLParam.TIME_OUT, timeOut);

        return m.save();
    }

    public static boolean roomNameExist(String roomName){
        if (StrUtils.isEmpty(roomName)){
            return true;
        }
        return dao.findFirst(StrUtils.join(
           "select * from ", SQLParam.T_ROOM, " where ", SQLParam.ROOM_NAME, " = '", roomName, "'"
        )) != null;
    }

    public static RoomModel queryById(String roomId){
        return dao.findById(roomId);
    }

    public static Page<RoomModel> queryAll(int pageNumber, int pageSize, String status){
        if (StrUtils.isEmpty(status)){
            return dao.paginate(pageNumber, pageSize, "select * ",
                    StrUtils.join(" from ", SQLParam.T_ROOM));
        }
        return dao.paginate(pageNumber, pageSize, "select * ",
                StrUtils.join(" from ", SQLParam.T_ROOM, " where ", SQLParam.STATUS, " = '", status, "'"));
    }

    public static boolean updateRoomInfo(String roomId, String roomName,
                                         String createUserId, String timeOut, String status){
        if (StrUtils.isEmpty(roomId)){
            return false;
        }
        RoomModel m = dao.findById(roomId);
        if (m == null){
            return false;
        }
        if (!StrUtils.isEmpty(roomName)){
            m.set(SQLParam.ROOM_NAME, roomName);
        }
        if (!StrUtils.isEmpty(createUserId)){
            m.set(SQLParam.CREATE_USER_ID, createUserId);
        }
        if (!StrUtils.isEmpty(timeOut)){
            m.set(SQLParam.TIME_OUT, timeOut);
        }
        if (!StrUtils.isEmpty(status)){
            m.set(SQLParam.STATUS, status);
        }
        return m.update();
    }

    public static boolean del(String roomId){
        if (StrUtils.isEmpty(roomId)){
            return false;
        }
        return dao.deleteById(roomId);
    }



}
