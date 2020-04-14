$(document).ready(function(){
	
//	getRights()
	
	function getRights(){
		post(getHost() + "sys/right/getRights", 
		{
			pageNumber: 1
			, pageSize : 1000
			, status : status
		}, function success(res){
			
			if(res.code == 0){
				setDataToView(res.data.list);
				
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
				s += "<input name='right' lay-skin='primary' type='checkbox' title='" 
						+ o.rightName + "' value='" + o.rightId + "'>";
			}else{
				s += "<input name='right' lay-skin='primary' type='checkbox' title='" 
						+ o.rightName + "' value='" + o.rightId + "' disabled=''>";
			}
		});                                        
		
		$("#div_right_list").html(s);
		
		layui.use('form', function(){
   			var form = layui.form;
   			
   			form.render()
   		});
	}
	
	$("#btn_add").on("click", function(){
		var roleName = $("#input_role_name").val()
		var status = $("#input_status").prop("checked")
		
		if(strIsEmpty(roleName)){
			layer.msg("请输入角色名称")
			return;
		}
		
		addRole(roleName, status ? "0" : "1")
	})
	
	function addRole(roleName, status){
		post(getHost() + "sys/role/addRole", {
			roleName : roleName
			, status : status
		}, function success(res){
			layer.msg(res.msg)
			if(res.code == 0){
				parent.window.location.reload()
				x_admin_close()	
			}
		}, function error(err){
			layer.msg("添加角色失败，请重试..")
		})
	}
	
	function addRoleRights(roleId, rightIds){
		
	}
})