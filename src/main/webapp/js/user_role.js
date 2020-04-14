$(document).ready(function(){
	var userId = getUrlParam("userId")
	
	
	getAllRolesAndUserRoles()
	
	function getAllRolesAndUserRoles(){
		post(getHost() + "sys/userRole/getAllRolesAndUserRoles", 
		{
			userId : userId
		}, function success(res){
			showToast(res.msg)
			if(res.code == 0){
				setDataToView(res.data);	
			}
		}, function error(err){
			showToast("获取角色列表失败");
		}
		);
	}
	
	function setDataToView(data){
		if(data.length < 1){
			showToast("暂无角色数据");
			return;
		}
		var s = '';
		$.each(data, function(v, o) {
			if(o.status == 0){
				if(strIsEmpty(o.userId)){
					s += "<input lay-skin='primary' type='checkbox' title='" 
						+ o.roleName + "' value='" + o.roleId + "'>";
				} else {
					if(userId == o.userId){
						s += "<input checked='checked' lay-skin='primary' type='checkbox' title='" 
							+ o.roleName + "' value='" + o.roleId + "'>";
					}else{
						s += "<input lay-skin='primary' type='checkbox' title='" 
							+ o.roleName + "' value='" + o.roleId + "'>";
					}
				}
			}else{
				if(strIsEmpty(o.userId)){
					s += "<input lay-skin='primary' type='checkbox' title='" 
						+ o.roleName + "' value='" + o.roleId + "' disabled=''>";
				} else {
					if(userId == o.userId){
						s += "<input checked='checked' name='checkbox_role_right' lay-skin='primary' type='checkbox' title='" 
							+ o.roleName + "' value='" + o.roleId + "' disabled=''>";
					}else{
						s += "<input lay-skin='primary' type='checkbox' title='" 
							+ o.roleName + "' value='" + o.roleId + "' disabled=''>";
					}
				}
			}
		});                                        
		
		$("#div_right_list").html(s);
		
		layui.use('form', function(){
   			var form = layui.form;
   			form.on('checkbox', function(){
   				var status = $(this).prop("checked")
   				var value = $(this).attr("value")
   				var title = $(this).attr("title")
   				if(status){
   					addUserRole(userId, value, $(this), form)
   				}else{
   					delUserRole(userId, value, $(this), form)
   				}
   			})
   			form.render()
   		});
	}
	
	
	
	function addUserRole(userId, roleId, checkbox, form){
		post(getHost() + "sys/userRole/addUserRole",
		{
			userId :  userId
			, roleId : roleId
		}
		, function success(res){
			showToast(res.msg)
			if(res.code == 0){
				
			} else{
				var status = checkbox.prop("checked")
				if (status == false){
					checkbox.prop("checked", true)
				}else{
					checkbox.prop("checked", false)
				}
				form.render()
			}
		}, function error(err){
			showToast("添加用户角色失败，请重试..")
			var status = checkbox.prop("checked")
			if (status == false){
				checkbox.prop("checked", true)
			}else{
				checkbox.prop("checked", false)
			}
			form.render()
		})
	}
	
	function delUserRole(userId, roleId, checkbox, form){
		post(getHost() + "sys/userRole/delUserRole",
		{
			roleId :  roleId
			, userId : userId
		}
		, function success(res){
			showToast(res.msg)
			if(res.code == 0){
				
			} else{
				var status = checkbox.prop("checked")
				if (status == false){
					checkbox.prop("checked", true)
				}else{
					checkbox.prop("checked", false)
				}
				form.render()
			}
		}, function error(err){
			showToast("删除用户角色失败，请重试..")
			var status = checkbox.prop("checked")
			if (status == false){
				checkbox.prop("checked", true)
			}else{
				checkbox.prop("checked", false)
			}
			form.render()
		})
	}
})