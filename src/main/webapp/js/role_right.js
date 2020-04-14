$(document).ready(function(){
	var roleId = getUrlParam("roleId")
	
	
	getAllRightsAndRoleRights()
	
	function getAllRightsAndRoleRights(){
		post(getHost() + "sys/roleRight/getAllRightsAndRoleRight", 
		{
			roleId : roleId
		}, function success(res){
			
			if(res.code == 0){
				setDataToView(res.data);
				
			}else{
				new Toast({message: res.msg}).show();
			}
		}, function error(err){
			new Toast({message: "获取权限列表失败"}).show();
		}
		);
	}
	
	function setDataToView(data){
		if(data.length < 1){
			new Toast({message: "暂无权限数据"}).show();
			return;
		}
		var s = '';
		$.each(data, function(v, o) {
			if(o.status == 0){
				if(strIsEmpty(o.roleId)){
					s += "<input id='input_role_right' name='checkbox_role_right' lay-skin='primary' type='checkbox' title='" 
						+ o.rightName + "' value='" + o.rightId + "'>";
				} else {
					if(roleId == o.roleId){
						s += "<input id='input_role_right' name='checkbox_role_right' checked='checked' lay-skin='primary' type='checkbox' title='" 
							+ o.rightName + "' value='" + o.rightId + "'>";
					}else{
						s += "<input id='input_role_right' name='checkbox_role_right' lay-skin='primary' type='checkbox' title='" 
							+ o.rightName + "' value='" + o.rightId + "'>";
					}
				}
			}else{
				if(strIsEmpty(o.roleId)){
					s += "<input id='input_role_right' name='checkbox_role_right' lay-skin='primary' type='checkbox' title='" 
						+ o.rightName + "' value='" + o.rightId + "' disabled=''>";
				} else {
					if(roleId == o.roleId){
						s += "<input id='input_role_right' checked='checked' name='checkbox_role_right' lay-skin='primary' type='checkbox' title='" 
							+ o.rightName + "' value='" + o.rightId + "' disabled=''>";
					}else{
						s += "<input id='input_role_right' name='checkbox_role_right' lay-skin='primary' type='checkbox' title='" 
							+ o.rightName + "' value='" + o.rightId + "' disabled=''>";
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
   					addRoleRight(roleId, value, $(this), form)
   				}else{
   					delRoleRight(roleId, value, $(this), form)
   				}
   			})
   			form.render()
   	});
	}
	
	$("#btn_submit").on('click', function(){
		var checkboxs = document.getElementsByName("checkbox_role_right");
		for(var i = 0; i< checkboxs.length; i++){
			console.log(checkboxs[i].checked)
			console.log(checkboxs[i].value)
		}
	})
	
	function addRoleRight(roleId, rightId, checkbox, form){
		post(getHost() + "sys/roleRight/addRoleRight",
		{
			roleId :  roleId
			, rightId : rightId
		}
		, function success(res){
			layer.msg(res.msg)
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
			layer.msg("添加角色权限失败，请重试..")
			var status = checkbox.prop("checked")
			if (status == false){
				checkbox.prop("checked", true)
			}else{
				checkbox.prop("checked", false)
			}
			form.render()
		})
	}
	
	function delRoleRight(roleId, rightId, checkbox, form){
		post(getHost() + "sys/roleRight/delRoleRight",
		{
			roleId :  roleId
			, rightId : rightId
		}
		, function success(res){
			layer.msg(res.msg)
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
			layer.msg("删除角色权限失败，请重试..")
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