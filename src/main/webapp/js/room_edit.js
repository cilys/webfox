$(document).ready(function(){
	var roomId = getUrlParam("roomId")
	var param = decodeURIComponent(getUrlParam("param"))
	
	var paramJson = JSON.parse(param);
	$("#input_room_name").val(paramJson.roomName)
	if(paramJson.status == 0){
		$("#cb_status").prop("checked", "checked")
	} else{
		$("#cb_status").prop("checked", "")
	}
	
	$("#btn_add").on('click', function(){
		var rightName = $("#input_room_name").val()
		if(strIsEmpty(rightName)){
			showToast("请输入房间名称")
			return;
		}
		
		var status = $("#cb_status").prop("checked")
		
		addRoom(rightName, status ? "0" : "1")
	});
	
	function addRoom(rightName, status){
		post(getHost() + "room/addRoom", 
		{
			roomName : rightName
			, userId : getUserId()
			, status : status
		}, function success(res){
			showToast(res.msg)
			if(res.code == 0){
				dismissDialog()
				reloadParent()
			}
		}, function error(err){
			showToast("添加房间失败，请重试..")
		}
		)
	}
	
})