$(document).ready(function(){
	if(checkHtml()){
		return;
	}
	
	var startAllAppoint = 0;	//开始页数
	var currentPage = 1;	//当前页数
	var pageSize = 10
	var totalPage = 0;			//数据总条数
	
	getRooms()
	
	function getRooms(){
		post(getHost() + "room/getRooms", 
		{
			pageNumber : currentPage
			, pageSize : pageSize
		}
		, function success(res){
			showToast(res.msg)
			
			if(res.code == 0){
				setDataToView(res.data.list)
				totalPage = res.data.totalPage;
				currentPage = res.data.pageNumber;
				toPage();
			}
			
		}, function error(err){
			showToast("获取房间列表失败，请刷新重试..")
		}
		)
	}
	
	function setDataToView(data){
		if(data.length > 0){
			var s = "<thead>"
				+ 	"<tr>"
				+ 		"<th>房间ID</th>"
				+		"<th>房间名称</th>"
				+ 		"<th>状态</th>"
				+		"<th>创建时间</th>"
				+		"<th>修改时间</th>"
				+		"<th>操作</th>"
				+	"</tr>"
				+ "</thead>";
				
			s += "<tbody>";
			$.each(data, function(v, o) {
				s += "<tr>";
				
				s += 	"<td>" + o.roomId + "</td>";
				s += 	"<td>" + o.roomName + "</td>";
				
				if(o.status == 0){
					s += "<td>";
					s +=		"<button id='btn_status_edit' class='layui-btn layui-btn-primary layui-btn-sm' data-status='" + o.status + "' data-id='" + o.roomId + "'>";
					s +=			"<i class='layui-icon'>&#xe605;</i>";
					s +=		"</button>";
					s +=	 "</td>";
				} else{
					s += "<td>";
					s +=		"<button id='btn_status_edit' class='layui-btn layui-btn-primary layui-btn-sm' data-status='" + o.status + "' data-id='" + o.roomId + "'>";
					s +=			"<i class='layui-icon'>&#x1006;</i>";
					s +=		"</button>";
					s +=	 "</td>";
				}
				
				s += 	"<td>" + o.createTime + "</td>";				
				s +=		"<td>" + o.updateTime + "</td>";
				
				s += "<td>"
					+	"<div class='layui-btn-group'>"
					+ 		"<button class='layui-btn layui-btn-primary layui-btn-sm' id='btn_edit' data-id='" + o.roomId + "' data-all='" + JSON.stringify(o) + "'>"
					+			"<i class='layui-icon'>&#xe642;</i>"
					+		"</button>"
					+		"<button class='layui-btn layui-btn-primary layui-btn-sm' id='btn_del' data-id='" + o.roomId + "'>"
					+			"<i class='layui-icon'>&#xe640;</i>"
					+		"</button>"
					+	"</div>"
					+"</td>"
				
				s += 	"</tr>";
			});
			
			s += "</tbody>"
			$("#t_list").html(s)
			
			
			$("#t_list #btn_status_edit").on("click", function(){
				var id = $(this).attr("data-id");
				var status = $(this).attr("data-status");
				if(status == 0){
					layer.confirm('是否禁用该房间？',function(index){
		              	updataStatus(id, 1)
		        		});
				}else {
					layer.confirm('是否启用该房间？',function(index){
		              //发异步删除数据
		              	updataStatus(id, 0)
		        		});
				}
			})
			
			$("#t_list #btn_edit").on("click", function(){
				var id = $(this).attr("data-id");
				var param = $(this).attr("data-all")
				x_admin_show('编辑房间信息','./room_edit.html?roomId=' + id + "&param=" + encodeURIComponent(param))
			})
			
			$("#t_list #btn_del").on("click", function(){
				var id = $(this).attr("data-id")
				layer.confirm('确认要删除吗？',function(index){
		              del(id);
		        });
			})
		} else {
			$("#t_list").html("<br/><span style='width:10%;height:30px;display:block;margin:0 auto;'>暂无数据</span>");
			$("#paged").hide()
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
	   					getRooms()
	   				}
	   			}
	   		});
   		});
	};
	
	function updataStatus(id, status){
		post(getHost() + "room/updateRoomInfo",
		{
			roomId : id
			, status : status
		}, function success(res){
			showToast(res.msg);
			if(res.code == 0){
				getRooms()
			}
		}, function error(err){
			showToast("更新房间状态失败，请重试..")
		})
	}
	
	function del(id){
		post(getHost() + "room/delRoom", 
		{
			roomId : id
		}, function success(res){
			showToast(res.msg)
			
			if(res.code == 0){
				getRooms()
			}
		}, function error(err){
			showToast("删除房间失败，请重试..")
		}
		)
	}
});