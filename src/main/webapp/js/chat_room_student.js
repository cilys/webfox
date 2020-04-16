$(document).ready(function(){
	//消息常量区
	const HEART_PING = 98;
    const HEART_PONG = 99;
    const ROOM_TEXT = 1001;
    const ROOM_TEXT_NOTIFY = 1002;
    const FRIEND_TEXT = 1021;
    const ROOM_MEMBER_LIST_REQUEST = 1003;    //请求获取聊天室的群成员
    const ROOM_MEMBER_LIST_RESPONSE = 1004;    //响应获取聊天室的群成员

    const OPTION_USER_STATUS = 1201;  //操作用户的状态
    
    const WHITE_BOARD_DRAW = 1301;		//白板绘图，远程同步给其它用户
	const WHITE_BOARD_CLEAR_SCREEN = 1302;	//白板绘图，清屏，远程同步给其它用户
	
	
	
	var roomId = getUrlParam("roomId");
	var roomName = getUrlParam("roomName")
//	roomId = 1;
	var user = getName();
//	user = "张三(zhangsan)";
//	user = "李四(zhangsan)";
	
	$("#div_right_title").html("<legend>" 
								+ (strIsEmpty(roomName) ? roomId : roomName) 
								+ "【" + user + "】"
								+ "</legend>")
	
	var drawType = 1;//绘制图形的类型，1直线、2圆圈、3文字
	
	
	var currentDrawStatus = 0;	//当前的绘制状态，0自己画、1远程同步过来画图
	
	$("#btn_line").on("click", function(){
		drawType = 1;
		$("#btn_line").removeClass("layui-btn-primary");
		$("#btn_circle").removeClass("layui-btn-primary");
		
		$("#btn_circle").addClass("layui-btn-primary");
	})
	$("#btn_circle").on("click", function(){
		drawType = 2;
		$("#btn_line").removeClass("layui-btn-primary");
		$("#btn_circle").removeClass("layui-btn-primary");
		
		$("#btn_line").addClass("layui-btn-primary");
	})
	
	$("#btn_pre").on("click", function(){
		clearRect();
		
		attr_draw.pop();
		
		for(var i = 0; i < attr_draw.length; i++){
			draw(attr_draw[i], false)
		}
	})
	$("#btn_clear").on("click", function(){
		clearRect();
		attr_draw.length = 0
	})
	
	var save_href = document.getElementById("save_href");
	save_href.onclick = function(){
		var tempSrc = canvas.toDataURL("image/png")
		save_href.href = tempSrc;
	}
	
	$("#btn_clear_question").on("click", function(){
//		$("#textarea_question").val("")
	})
	
	
	//白板相关功能
	var canvas = document.getElementById("canvas_white_board")
	var ctx = canvas.getContext("2d");
	
	
	
	
	var attr_draw = [];	//存储历史绘制的图形集合
	var obj;		//绘制图形的对象
	
	//画线，鼠标按下的坐标点，即直线的起点
	var startX = 0;
	var startY = 0;
	
	//鼠标按下
	canvas.onmousedown = function(event){
		if(drawType == 1){
			startX = event.x;
			startY = event.y;
			
			obj = new Object();
			obj.startX = startX;
			obj.startY = startY;
		} else if(drawType == 2){
			
		}
	}
	//鼠标抬起
	canvas.onmouseup = function(event){
		if(drawType == 1){
			var endX = event.x;
			var endY = event.y;
			
			obj.endX = endX;
			obj.endY = endY;
			obj.drawType = drawType;
			draw(obj, true);
		} else if(drawType == 2){
			obj = new Object();
			obj.endX = event.x;
			obj.endY = event.y;
			obj.drawType = drawType;
			draw(obj, true);
		}
	}
	
	
	
	
	function draw(obj, pushCache){
		if(obj.drawType == 1){
			drawLine(obj.startX, obj.startY, obj.endX, obj.endY);
		} else if(obj.drawType == 2){
			drawCircle(obj.endX, obj.endY, 50);
		} else if(obj.drawType == 3){
			drawText(obj.text)
		}
		
		//0自己画，1远程同步过来的
		if(currentDrawStatus == 0){
			if(pushCache){
				attr_draw.push(obj)
			}
			sendMsg(createMsg(WHITE_BOARD_DRAW, obj));
		}else if(currentDrawStatus == 1){
			
		}
	}
	
	function drawLine(startX, startY, endX, endY){
		ctx.beginPath();
		ctx.moveTo(startX, startY);
		ctx.lineTo(endX, endY);
		ctx.stroke();
		ctx.fill();
		ctx.closePath();
	}
	
	function drawCircle(centerX, centerY, radius){
		ctx.beginPath();
		ctx.arc(centerX, centerY, radius, 0, Math.PI * 2, true);
		ctx.stroke();
		ctx.closePath();
	}
	
	function clearRect(){
		ctx.beginPath();
		ctx.clearRect(0, 0, 600, 530);
		ctx.closePath()
		
		if(currentDrawStatus == 0){
			sendMsg(createMsg(WHITE_BOARD_CLEAR_SCREEN, "clear screen"));
		}
	}
	
	function drawText(text){
		drawTextAuto(ctx, canvas.width, text, 20, 20, 20)
	}
	
	function drawTextAuto(context, canvasWidth, str, initX, initY, lineHeight){
//		ctx.beginPath();
////		ctx.font = "20px Georgia"
//		ctx.fillText(text, x, y, maxWidth)
//		ctx.closePath();

	    var lineWidth = 0;
	    var lastSubStrIndex= 0;
	    ctx.beginPath();
	    ctx.font = "15px Georgia"
	    for(let i = 0; i < str.length; i++){
	    		var txtWidth = context.measureText(str[i]).width;
	        lineWidth += txtWidth; 
	        
	        log("lineWidth = " + lineWidth);
	        log("canvasWidth - initX = " + (canvasWidth - initX))
	        if(lineWidth > canvasWidth - 2 * initX - txtWidth){//减去initX,防止边界出现的问题
	            context.fillText(str.substring(lastSubStrIndex, i), initX, initY);
	            initY += lineHeight;
	            lineWidth = 0;
	            lastSubStrIndex = i;
	        } 
	        if(i == str.length - 1){
	            context.fillText(str.substring(lastSubStrIndex,i+1), initX, initY);
	        }
	    }
	    ctx.closePath()
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//右侧聊天区域
	
	$("#btn_chat").on("click", function(){
		$("#btn_chat").removeClass("layui-btn-primary")
		$("#btn_members").removeClass("layui-btn-primary")
		
		$("#btn_members").addClass("layui-btn-primary")
		
		$("#div_area_chat").hide();
		$("#div_area_members").hide();
		$("#div_area_chat").show();
		
	})
	
	$("#btn_members").on("click", function(){
		$("#btn_chat").removeClass("layui-btn-primary")
		$("#btn_members").removeClass("layui-btn-primary")
		
		$("#btn_chat").addClass("layui-btn-primary")
		
		
		$("#div_area_chat").hide();
		$("#div_area_members").hide();
		$("#div_area_members").show();
	})
	
	$("#btn_connect").on("click", function(){
		initWebSocket()
	})
	$("#btn_disconnect").on("click", function(){
		closeWebSocket();
	})
	
	$("#btn_clear_msg").on("click", function(){
		$("#div_area_chat").html("")
	})
	$("#btn_send").on("click", function(){
		var f = sendMsg(createMsg(1001, $("#textarea_send_msg").val()))
		if(f) {
			$("#textarea_send_msg").val("")
		}
	})
	
	function appendMsgToView(fromUser, time, msg){
		var str = "";
		
		str += "<div style='padding:5px;'>";
		str += 		"<div style='color:green; font-size:12px; '>";
		str +=			(fromUser + "   " + time);
		str += 		"</div>";
		str +=		msg;
		str +=	"</div>";
		
		$("#div_area_chat").append(str);
	}
	
	
	
	
	
	
	
	
	
	//群聊成员列表
	function setDataToGroupMemberView(members){
		if(members == null || members.length < 1){
			return;
		}
		
		$("#div_area_members").html("")
		
		$.each(members, function(v, o) {
			var s = "";
				s += "<div style='padding:5px; border-bottom: 1px solid #CCCCCC;'>"
				s +=		"<div class='layui-btn-group'>"
				
				if(o.userStatus == 1){
					s +=		"<button id='btn_answer' data-user='" + o.user + "'  data-status='" + o.userStatus + "' class='layui-btn layui-btn-xs layui-btn-normal'>进行</button>"
				}else{
					s +=		"<button id='btn_answer' data-user='" + o.user + "'  data-status='" + o.userStatus + "' class='layui-btn layui-btn-xs layui-btn-primary'>答题</button>"	
				}
				s +=		"</div>"
				s +=		o.user
				s +=	"</div>"
			$("#div_area_members").append(s);
		})
		
		$("#div_area_members #btn_answer").on("click", function(){
			var user = $(this).attr("data-user");
			var userStatus = $(this).attr("data-status");
			var obj = {}
			obj.userName = user;
			if(userStatus == 1) {
				obj.userStatus = "0";
			} else {
				obj.userStatus = "1";
			}
			
			sendMsg(createMsg(OPTION_USER_STATUS, JSON.stringify(obj)))
		})
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//websocket相关
	var ws;
	var conected = false;
	function initWebSocket(){
		if("WebSocket" in window){
//			ws = new WebSocket(getWebSocketHost() + roomId + "/" + getName())
//			ws = new WebSocket("ws://121.40.165.18:8800")
			ws = new WebSocket("ws://127.0.0.1:8080/ChatRoom/room/" + roomId + "/" + user)
			ws.onopen = function(){
				conected = true;
				showToast("服务器连接成功")
				
				$("#btn_connect").attr("disabled", true);
				$("#btn_connect").removeClass("layui-btn-disabled");
				$("#btn_disconnect").attr("disabled", false);
				$("#btn_disconnect").removeClass("layui-btn-disabled");
				
				$("#btn_send").removeClass("layui-btn-disabled");
				$("#btn_send").attr("disabled", false);
				
				$("#btn_connect").addClass("layui-btn-disabled");
				
			}
			
			ws.onmessage = function(evt){
				log(evt.data)
				parseMsg(evt.data)
			}
			
			ws.onclose = function() {
				conected = false;
				ws = null;
				showToast("连接已断开...")
				$("#btn_connect").attr("disabled", false);
				$("#btn_connect").removeClass("layui-btn-disabled");
				$("#btn_disconnect").attr("disabled", true);
				$("#btn_disconnect").removeClass("layui-btn-disabled");
				
				$("#btn_disconnect").addClass("layui-btn-disabled");
				
				$("#btn_send").removeClass("layui-btn-disabled");
				$("#btn_send").addClass("layui-btn-disabled");
				
				$("#btn_send").attr("disabled", false);
			}
		}else{
			layer.confirm('当前浏览器不支持WebSocket，请更新或更换浏览器',function(index){
				
		   	});
		}
	}
	
	function closeWebSocket(){
		if(ws != null){
			ws.close();
		}
	}
	
	function sendMsg(msg){
		if(ws != null){
			if(conected == true){
				log("发送数据：" + msg)
				ws.send(msg)
				return true;
			}
		}
		
		return false;
	}
	
	function parseMsg(str){
		var msg = JSON.parse(str)
		var msgType = msg.msgType;
		//心跳试探
		if(msgType == 98){
			//心跳回应
			sendMsg(createMsg(99, "HeartPong"))
			return;
		} else if(msgType == 99){
			//心跳回应包，不处理
			return;
		} else if(msgType == 1001){
			// 聊天室文字消息
			
		} else if(msgType == 1002){
			//聊天室通知消息
			msg.fromUser = "系统通知";
		} else if(msgType == 1003){
			//请求获取用户列表，该消息不做任何处理
			return;
		} else if(msgType == 1004){
			//响应获取用户列表
			setDataToGroupMemberView(msg.msg)
			return;
		} else if(msgType == WHITE_BOARD_DRAW){
			draw(JSON.parse(msg.msg), false);
			return;
		} else if(msgType == WHITE_BOARD_CLEAR_SCREEN){
			clearRect();
			return;
		}
		
		appendMsgToView(msg.fromUser, msg.msgTime, msg.msg);
	}
	
	function createMsg(msgType, str){
		return createMsg(msgType, str, user)
	}
	
	function createMsg(msgType, str, userName){
		var msg = {};
		msg.fromUser = userName;
		msg.msgType = msgType;
		msg.msg = str;
		
		return JSON.stringify(msg);
	}
	
	
	
	
	
	
	
	
	
	
	var input_file = document.getElementById("xFile");
		function openFile(event){
			var input = event.target;
				console.log('input = ' + input);
				var reader = new FileReader();
				reader.onload = function(){
					if(reader.result){
//						$("#textarea_question").val(reader.result);
						
						var txt = {}
						txt.drawType = 3;
						txt.text = reader.result;
						
						draw(txt, true)
//						input_file.outerHTML = input_file.outerHTML
					}
				}
				reader.readAsText(input.files[0]);
		}
	input_file.onchange = function(event){
		openFile(event);
	}
})