$(document).ready(function(){
	var rightId = getUrlParam("rightId")
	var param = decodeURIComponent(getUrlParam("param"))
	
	var paramJson = JSON.parse(param);
	$("#input_right_name").val(paramJson.rightName)
	$("#input_access_url").val(paramJson.accessUrl)
	if(paramJson.status == 0){
		$("#cb_status").prop("checked", "checked")
	} else{
		$("#cb_status").prop("checked", "")
	}
	
	
	
	$("#btn_add").on('click', function(){
		var rightName = $("#input_right_name").val()
		if(strIsEmpty(rightName)){
			layer.msg("请输入权限名称")
			return;
		}
		var accessUrl = $("#input_access_url").val()
		if(strIsEmpty(accessUrl)){
			layer.msg("请输入路径规则")
			return;
		}
		var status = $("#cb_status").prop("checked")
		
		updateRight(rightName, accessUrl, status ? "0" : "1")
	});
	
	function updateRight(rightName, accessUrl, status){
		post(getHost() + "sys/right/updateRight", 
		{
			rightId : rightId
			, rightName : rightName
			, accessUrl : accessUrl
			, status : status
		}, function success(res){
			layer.msg(res.msg)
			if(res.code == 0){
//				parent.window.location.href = "./right_list.html";
				parent.window.location.reload()
				x_admin_close()
			}
		}, function error(err){
			layer.msg("修改权限信息失败，请重试..")
		}
		)
	}
})