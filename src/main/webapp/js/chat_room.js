$(document).ready(function(){
	var drawType = 1;//绘制图形的类型，1直线、2圆圈
	
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
			draw(attr_draw[i])
		}
	})
	$("#btn_clear").on("click", function(){
		clearRect();
		attr_draw.length = 0
	})
	$("#btn_download").on("click", function(){
		
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
			draw(obj);
			attr_draw.push(obj);
			
			console.log(attr_draw)
		}
	}
	
	
	
	function draw(obj){
		if(obj.drawType == 1){
			drawLine(obj.startX, obj.startY, obj.endX, obj.endY);
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
	
	function clearRect(){
		ctx.beginPath();
		ctx.clearRect(0, 0, 600, 530);
		ctx.closePath()
	}
})