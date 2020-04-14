$(document).ready(function(){
	var startAllAppoint = 0;	//开始页数
	var currentPage = 1;	//当前页数
	var pageSize = 10
	var totalPage = 0;			//数据总条数
	var status = "";
	
	getRights();
	
	//获取权限列表
	function getRights(){
		post(getHost() + "sys/right/getRights", 
		{
			pageNumber: currentPage
			, pageSize : pageSize
			, status : status
		}, function success(res){
			showToast(res.msg);
			if(res.code == 0){
				setDataToView(res.data.list);
				totalPage = res.data.totalRow;
				currentPage = res.data.pageNumber;
				toPage();
			}
		}, function error(err){
			showToast("获取权限列表失败");
		}
		);
	}
	
	function setDataToView(data){
		var s = "<thead>"
				+ 	"<tr>"
				+ 		"<th>权限ID</th>"
				+		"<th>权限名称</th>"
				+		"<th>权限规则</th>"
				+		"<th>状态</th>"
				+		"<th>操作</th>"
				+	"</tr>"
				+ "</thead>";
		
		if(data.length > 0){
			
			s += "<tbody>";
			
			$.each(data, function(v, o) {
				s += "<tr>";
				
				s += 	"<td>" + o.rightId + "</td>";
				s += 	"<td>" + o.rightName + "</td>";
				s += 	"<td>" + o.accessUrl + "</td>";				
				if(o.status == 0){
					s += "<td>";
					s +=		"<button id='btn_status_edit' class='layui-btn layui-btn-primary layui-btn-sm' data-status='" + o.status + "' data-id='" + o.rightId + "'>";
					s +=			"<i class='layui-icon'>&#xe605;</i>";
					s +=		"</button>";
					s +=	 "</td>";
				} else{
					s += "<td>";
					s +=		"<button id='btn_status_edit' class='layui-btn layui-btn-primary layui-btn-sm' data-status='" + o.status + "' data-id='" + o.rightId + "'>";
					s +=			"<i class='layui-icon'>&#x1006;</i>";
					s +=		"</button>";
					s +=	 "</td>";
				}
				
				s += "<td>"
					+	"<div class='layui-btn-group'>"
					+ 		"<button class='layui-btn layui-btn-primary layui-btn-sm' id='btn_edit' data-id='" + o.rightId + "' data-all='" + JSON.stringify(o) + "'>"
					+			"<i class='layui-icon'>&#xe642;</i>"
					+		"</button>"
					+		"<button class='layui-btn layui-btn-primary layui-btn-sm' id='btn_del' data-id='" + o.rightId + "'>"
					+			"<i class='layui-icon'>&#xe640;</i>"
					+		"</button>"
					+	"</div>"
					+"</td>"
				
				s += 	"</tr>";
			});
			
			s += "</tbody>"
			
			$("#t_rights").html(s)
			
			
			$("#t_rights #btn_status_edit").on('click', function(){
				var rightId = $(this).attr("data-id");
				var status = $(this).attr("data-status");
				if(status == 0){
					layer.confirm('是否禁用该权限？',function(index){
		              	updateRightStatus(rightId, 1)
		        		});
				}else {
					layer.confirm('是否启用该权限？',function(index){
		              //发异步删除数据
		              	updateRightStatus(rightId, 0)
		        		});
				}
			})
			
			$("#t_rights #btn_edit").on('click', function(){
				var rightId = $(this).attr("data-id");
				var param = $(this).attr("data-all")
				
				console.log("./right_edit.html?rightId" + rightId + "&param=" + param)
				x_admin_show("编辑权限", "./right_edit.html?rightId=" + rightId + "&param=" + encodeURIComponent(param))
			})
			
			$("#t_rights #btn_del").on('click', function(){
				var rightId = $(this).attr("data-id");
				layer.confirm('是否删除该权限？',function(index){
					delRight(rightId)
		        	});
			})
		} else{
			$("#paged").hide();
			$("#t_rights").html("<br/><span style='width:10%;height:30px;display:block;margin:0 auto;'>暂无数据</span>");
		}	
	}
	
	function toPage(){
   		layui.use('laypage', function(){
   			var laypage = layui.laypage;
   			
   			laypage.render({
	   			elem : 'paged'
	   			, count : totalPage
	   			, limit : pageSize
	   			, curr : currentPage
	   			, jump : function(obj, first){
	   				currentPage = obj.curr;
	   				
	   				if(!first){
	   					getRights()
	   				}
	   			}
	   		});
   		});
	};
	
	function delRight(rightId){
		post(getHost() + "sys/right/delRight", {
			rightId : rightId
		}, function success(res){
			layer.msg(res.msg)
			
			if(res.code == 0){
				getRights()
			}
		}, function error(err){
			layer.msg("删除权限失败，请重试...")
		})
	}
	
	function updateRightStatus(rightId, status){
		post(getHost() + "sys/right/updateRight",
		{
			rightId : rightId
			, status : status
		}, function success(res){
			layer.msg(res.msg)
			if(res.code == 0){
				getRights()
			}
		}, function error(err){
			layer.msg("更新权限状态失败，请重试..")
		})
	}
	
	
	
	
	
	
	
	
	
	
})