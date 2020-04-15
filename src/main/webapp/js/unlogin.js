function login(res){
	if(res == 'undefined'){
		pHref("./login.html");
		return false;
	}
	var code = res.code;

	if(code == "1002"){
		//1002用户未登录
		clearCookie();
		pHref("./login.html");
		return false;
	}else if(code == "1003"){
		//1003登录已过期
		clearCookie();
		pHref("./login.html");
		
		return false;
	}else{
		return true;
	}
}

function userUnLogin(){
	return false;
}

function loginInfoEmpty(){
//	var uid = $.cookie('userId');
//	var token = $.cookie("token");

	var uid = window.localStorage.getItem("userId");
	var token = window.localStorage.getItem("token");
	
	log("userId = " + uid + "<--->token = " + token);

	if(uid == null || uid == "undefined" || uid == "" || uid == "null"
		|| token == null || token == "undefined" || token == "" || token == "null"){
			
		clearCookie();
		
		return true;
	}
		return false;
	
}

function unlogin(){
		pHref("./login.html");
//		href("./login.html");
}

function clearCookie(){
//	$.cookie("userId", null);
//	$.cookie("token", null);
	
	window.localStorage.removeItem("token");
	window.localStorage.removeItem("userId");
	window.localStorage.removeItem("userIdentify");
	window.localStorage.removeItem("realName");
}

function saveCookie(token, userId){
//	$.cookie('token', token, {expires: 1, path: '/'});
//	$.cookie('userId', userId, {expires: 1, path: '/'});

	window.localStorage.setItem("token", token);
	window.localStorage.setItem("userId", userId);
}

function saveUserIdentify(userIdentify){
	window.localStorage.setItem("userIdentify", userIdentify);
}

function getUserIdentify(){
	return window.localStorage.getItem("userIdentify");
}

function getUserId(){
	return window.localStorage.getItem("userId");
}

function saveLoginInfo(userName, pwd){
	window.localStorage.setItem("userName", userName);
	window.localStorage.setItem("pwd", pwd);
}
function getUserName(){
	var userName = window.localStorage.getItem("userName");
	return (userName == null || userName == "") ? "" : userName;
}
function getPwd(){
	var pwd = window.localStorage.getItem("pwd");
	return (pwd == null || pwd == "") ? "" : pwd;
}

function getRealName(){
	var realName = window.localStorage.getItem("realName");
	return (realName == null || realName == "") ? getUserName() : realName;
}

function getName(){
	return getRealName() + "(" + getUserName() + ")"
}
