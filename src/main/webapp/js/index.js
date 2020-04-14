$(document).ready(function(){
	getSysMenu()
	
	function getSysMenu(){
		post(getHost() + "sys/menu/sysMenu",
		{},
		function success(res){
			new Toast({message : res.msg}).show()
			if(res.code == 0){
				setSysMenuView(res.data)
			}
		}, function error(err){
			new Toast({message : "获取菜单列表失败，请刷新页面重试..."}).show()
		}
		)
	}
	
	function setSysMenuView(sysMenus){
		if(sysMenus == null || sysMenus.length < 1){
			return;
		}
		var s = '<ul id="nav">';
		$.each(sysMenus, function(v, o) {
			s += '<li>';
			s +=		'<a href="javascript:;">';
			s +=      		'<i class="iconfont">&#xe6b8;</i>';
			s +=      		'<cite>' + o.name + '</cite>';
			s +=    			'<i class="iconfont nav_right">&#xe697;</i>';
			s += 	'</a>'; 
            if(o.children != null && o.children.length > 0){
            		s += setChildrenMenuView(o.children, '')
            }
            s += '</li>';
		});
		s += '</ul>';
		$("#side-nav").html(s)
//		
//		layui.use(['form'], function(){
//			layui.form.render()
//		})

		//触发事件
  var tab = {
        tabAdd: function(title,url,id){
          //新增一个Tab项
          element.tabAdd('xbs_tab', {
            title: title 
            ,content: '<iframe tab-id="'+id+'" frameborder="0" src="'+url+'" scrolling="yes" class="x-iframe"></iframe>'
            ,id: id
          })
        }
        ,tabDelete: function(othis){
          //删除指定Tab项
          element.tabDelete('xbs_tab', '44'); //删除：“商品管理”
          
          
          othis.addClass('layui-btn-disabled');
        }
        ,tabChange: function(id){
          //切换到指定Tab项
          element.tabChange('xbs_tab', id); //切换到：用户管理
        }
      };

		
		$('.left-nav #nav li').click(function (event) {
	        if($(this).children('.sub-menu').length){
	            if($(this).hasClass('open')){
	                $(this).removeClass('open');
	                $(this).find('.nav_right').html('&#xe697;');
	                $(this).children('.sub-menu').stop().slideUp();
	                $(this).siblings().children('.sub-menu').slideUp();
	            }else{
	                $(this).addClass('open');
	                $(this).children('a').find('.nav_right').html('&#xe6a6;');
	                $(this).children('.sub-menu').stop().slideDown();
	                $(this).siblings().children('.sub-menu').stop().slideUp();
	                $(this).siblings().find('.nav_right').html('&#xe697;');
	                $(this).siblings().removeClass('open');
	            }
	        }else{
	
	            var url = $(this).children('a').attr('_href');
	            var title = $(this).find('cite').html();
	            var index  = $('.left-nav #nav li').index($(this));
	
	            for (var i = 0; i <$('.x-iframe').length; i++) {
	                if($('.x-iframe').eq(i).attr('tab-id')==index+1){
	                    tab.tabChange(index+1);
	                    event.stopPropagation();
	                    return;
	                }
	            };
	            
	            tab.tabAdd(title,url,index+1);
	            tab.tabChange(index+1);
	        }
	        
	        event.stopPropagation();
	         
	    })	
	}
	
	function setChildrenMenuView(children, s){
		s += '<ul class="sub-menu">';
		$.each(children, function(v, o) {
			
             s += '<li>';
             s += '<a _href="' + o.href +'">';
             s += '<i class="iconfont">&#xe6a7;</i>' ;
             s += '<cite>';
             s += o.name;
             s += '</cite>';
             
             if(o.children != null && o.children.length > 0){
             	s += setChildrenMenuView(o.children, s);
             }
		});
		s += '</ul>'
		return s;
	}
	
});