$(document).ready(function(){
	
	$("#btn_add").on('click', function(){
		var rightName = $("#input_right_name").val()
		if(strIsEmpty(rightName)){
			showToast("请输入权限名称")
			return;
		}
		var accessUrl = $("#input_access_url").val()
		if(strIsEmpty(accessUrl)){
			showToast("请输入路径规则")
			return;
		}
		var status = $("#cb_status").prop("checked")
		
		addRight(rightName, accessUrl, status ? "0" : "1")
	});
	
	function addRight(rightName, accessUrl, status){
		post(getHost() + "sys/right/addRight", 
		{
			rightName : rightName
			, accessUrl : accessUrl
			, status : status
		}, function success(res){
			showToast(res.msg)
			if(res.code == 0){
				dismissDialog()
				reloadParent()
			}
		}, function error(err){
			showToast("添加权限失败，请重试..")
		}
		)
	}
	
})