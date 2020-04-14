$(document).ready(function(){
	
	getRoles()
	
	function getRoles(){
		post(getHost() + "sys/role/getRoles", {}
		, function success(res){
			layer.msg(res.msg)
			
			if(res.code == 0){
				setDataToView(res.data)
			}
			
		}, function error(err){
			layer.msg("获取角色列表失败，请稍后重试..")
		}
		)
	}
	
	function setDataToView(data){
		if(data.length > 0){
			var s = "<thead>"
				+ 	"<tr>"
				+ 		"<th>角色ID</th>"
				+		"<th>角色名称</th>"
				+ 		"<th>状态</th>"
				+		"<th>创建时间</th>"
				+		"<th>修改时间</th>"
				+		"<th>操作</th>"
				+	"</tr>"
				+ "</thead>";
				
			s += "<tbody>";
			$.each(data, function(v, o) {
				s += "<tr>";
				
				s += 	"<td>" + o.roleId + "</td>";
				s += 	"<td>" + o.roleName + "</td>";
				
				if(o.status == 0){
					s += "<td>";
					s +=		"<button id='btn_status_edit' class='layui-btn layui-btn-primary layui-btn-sm' data-status='" + o.status + "' data-id='" + o.roleId + "'>";
					s +=			"<i class='layui-icon'>&#xe605;</i>";
					s +=		"</button>";
					s +=	 "</td>";
				} else{
					s += "<td>";
					s +=		"<button id='btn_status_edit' class='layui-btn layui-btn-primary layui-btn-sm' data-status='" + o.status + "' data-id='" + o.roleId + "'>";
					s +=			"<i class='layui-icon'>&#x1006;</i>";
					s +=		"</button>";
					s +=	 "</td>";
				}
				
				s += 	"<td>" + o.createTime + "</td>";				
				s +=		"<td>" + o.updateTime + "</td>";
				
				s += "<td>"
					+	"<div class='layui-btn-group'>"
					+ 		"<button class='layui-btn layui-btn-primary layui-btn-sm' id='btn_edit' data-id='" + o.roleId + "' data-name='" + o.roleName + "'>"
					+			"<i class='layui-icon'>&#xe60d;</i>"
					+		"</button>"
					+		"<button class='layui-btn layui-btn-primary layui-btn-sm' id='btn_del' data-id='" + o.roleId + "'>"
					+			"<i class='layui-icon'>&#xe640;</i>"
					+		"</button>"
					+	"</div>"
					+"</td>"
				
				s += 	"</tr>";
			});
			
			s += "</tbody>"
			$("#t_roles").html(s)
			
			
			$("#t_roles #btn_status_edit").on("click", function(){
				var roleId = $(this).attr("data-id");
				var status = $(this).attr("data-status");
				if(status == 0){
					layer.confirm('是否禁用该角色？',function(index){
		              	updataRoleStatus(roleId, 1)
		        		});
				}else {
					layer.confirm('是否启用该角色？',function(index){
		              //发异步删除数据
		              	updataRoleStatus(roleId, 0)
		        		});
				}
			})
			
			$("#t_roles #btn_edit").on("click", function(){
				var name = $(this).attr("data-name")
				var roleId = $(this).attr("data-id");
				x_admin_show('编辑角色【' + name + '】权限','./role_right.html?roleId=' + roleId)
			})
			
			$("#t_roles #btn_del").on("click", function(){
				var roleId = $(this).attr("data-id")
				layer.confirm('确认要删除吗？',function(index){
		              delRole(roleId);
		        });
			})
		} else {
			$("#t_roles").html("<br/><span style='width:10%;height:30px;display:block;margin:0 auto;'>暂无数据</span>");
		
		}
	}
	
	function updataRoleStatus(roleId, status){
		post(getHost() + "sys/role/updateRoleStatus",
		{
			roleId : roleId
			, status : status
		}, function success(res){
			layer.msg(res.msg);
			if(res.code == 0){
				getRoles()
			}
		}, function error(err){
			layer.msg("更新角色状态失败，请重试..")
		})
	}
	
	function delRole(roleId){
		post(getHost() + "sys/role/delRole", 
		{
			roleId : roleId
		}, function success(res){
			layer.msg(res.msg)
			
			if(res.code == 0){
				getRoles()
			}
		}, function error(err){
			layer.msg("删除角色失败，请重试..")
		}
		)
	}
	
	
	
});