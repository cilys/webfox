$(document).ready(function() {

	initCorverImg()

	function initCorverImg() {
		layui.use('upload', function() {
			var $ = layui.jquery,
				upload = layui.upload;

			upload.render({
				elem: '#img_corver_img',
				size: 600,
				auto: false,
				choose: function(obj) {
					obj.preview(function(index, file, result) {
						$("#img_corver_img").attr("src", result)
					})
				}

			})
		})
	}

	var E = window.wangEditor
	var editor = new E("#div_details")
	initEditor();

	function initEditor() {
		editor.customConfig.uploadImgShowBase64 = true;
		editor.customConfig.menus = [
			'head', // 标题
			'bold', // 粗体
			'fontSize', // 字号
			'underline', // 下划线
			'strikeThrough', // 删除线
			'foreColor', // 文字颜色
			'justify', // 对齐方式
			'image' // 插入图片
			
		]
		editor.create();
	}
	
	$("#btn_add").on("click", function(){
		getInput();
	})
	
	function getInput(){
		var name = $("#input_name").val();
		var corverImg = $("#img_corver_img").attr("src");
		var price = $("#input_price").val()
		var stockCount = $("#input_stock_count").val();
		var discountPrice = $("#input_discount_price").val();
		var discountNum = $("#input_discount_num").val();
		var status = $("#cb_status").attr("checked");
		var deatils = editor.txt.html();
		
		if(strIsEmpty(name)){
			showToast("请输入商品名称")
			return;
		}
		if(strIsEmpty(corverImg)){
			showToast("请选择封面图片")
			return;
		}
		if(strIsEmpty(price)){
			showToast("请输入商品价格")
			return;
		}
		
		if(strIsEmpty(stockCount)){
			showToast("请输入库存数量")
			return;
		}
		
		addProduct(name, corverImg, price, stockCount, discountPrice, discountNum, status ? "0" : "1", deatils)
	}
	
	function addProduct(name, corverImg, price, stockCount, discountPrice, discountNum, status, deatils){
		post(getHost() + "sys/product/addProduct",
		JSON.stringify({
			name : name
			, corverImg : corverImg
			, price : price
			, stockCount : stockCount
			, discountPrice : discountPrice
			, discountNum : discountNum
			, status : status
			, deatils : deatils
		}), function success(res){
			showToast(res.msg)
		}, function error(err){
			showToast("添加商品失败，请重试..")
		}
		)
	}
	
	
	
	
	
	
	
	

})