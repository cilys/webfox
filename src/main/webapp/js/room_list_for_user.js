$(document).ready(function(){
	if(checkHtml()){
		return;
	}
	
	$("#a_user").text(getRealName())
	$("#a_logout").on("click", function(){
		clearCookie()
		href("./login.html");
		window.location.reload()
	})
	
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
				
				+		"<th>房间名称</th>"
				
				+		"<th style='width:30px'>操作</th>"
				+	"</tr>"
				+ "</thead>";
				
			s += "<tbody>";
			$.each(data, function(v, o) {
				s += "<tr>";
				
				s += 	"<td>" + o.roomName + "</td>";
				
				
				if(o.status == 0){
					s += "<td>"
					+	"<div class='layui-btn-group'>"
					+		"<button class='layui-btn layui-btn-sm' id='btn_del' data-id='" + o.roomId + "' data-name = '" + o.roomName + "'>"
//					+			"<i class='layui-icon'>&#xe608;</i>"
					+ 			"进入"
					+		"</button>"
					+	"</div>"
					+"</td>"
				}else{
					s += "<td>"
					+	"<div class='layui-btn-group' >"
					+		"<button disabled='' class='layui-btn layui-btn-sm layui-btn-disabled' id='btn_del' data-id='" + o.roomId + "' data-name = '" + o.roomName + "'>"
//					+			"<i class='layui-icon'>&#xe608;</i>"
					+		"进入"
					+		"</button>"
					+	"</div>"
					+"</td>"
				}
				
				s += 	"</tr>";
			});
			
			s += "</tbody>"
			$("#t_list").html(s)
			
			
			
			$("#t_list #btn_del").on("click", function(){
				var id = $(this).attr("data-id")
				var name = $(this).attr("data-name")
				layer.confirm('是否进入该房间？',function(index){
					layer.closeAll();
					
					if(getUserIdentify() == 1){
		            		toUrl("./chat_room_teacher.html?roomId=" + id + "&roomName=" + name);
		            }else{
		            		toUrl("./chat_room_student.html?roomId=" + id + "&roomName=" + name);
		            }
		            
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
	
	
});