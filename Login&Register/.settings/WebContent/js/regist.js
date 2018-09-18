		//提示图标的位置信息
		var user_icon_loc = {
			"OK":"url('./image/u_ico.png') 15px 15px no-repeat,url('./image/ok.png')right 15px center no-repeat,url('./image/error.png')right -21px center no-repeat",
			"ERROR":"url('./image/u_ico.png') 15px 15px no-repeat,url('./image/ok.png')right -21px center no-repeat,url('./image/error.png')right 15px center no-repeat",
			"NONE":"url('./image/u_ico.png') 15px 15px no-repeat,url('./image/ok.png')right -21px center no-repeat,url('./image/error.png')right -21px center no-repeat"
		}		
		var psd_icon_loc = {
			"OK":"url('./image/p_ico.png') 15px 15px no-repeat,url('./image/ok.png')right 15px center no-repeat,url('./image/error.png')right -21px center no-repeat",
			"ERROR":"url('./image/p_ico.png') 15px 15px no-repeat,url('./image/ok.png')right -21px center no-repeat,url('./image/error.png')right 15px center no-repeat",
			"NONE":"url('./image/p_ico.png') 15px 15px no-repeat,url('./image/ok.png')right -21px center no-repeat,url('./image/error.png')right -21px center no-repeat"			
		}		
		var eml_icon_loc = {
			"OK":"url('./image/phone_ico.png') 15px 15px no-repeat,url('./image/ok.png')right 15px center no-repeat,url('./image/error.png')right -21px center no-repeat",
			"ERROR":"url('./image/phone_ico.png') 15px 15px no-repeat,url('./image/ok.png')right -21px center no-repeat,url('./image/error.png')right 15px center no-repeat",
			"NONE":"url('./image/phone_ico.png') 15px 15px no-repeat,url('./image/ok.png')right -21px center no-repeat,url('./image/error.png')right -21px center no-repeat"			
		}
		
		//提示信息
		var msg = {
			"username":user_icon_loc,
			"password":psd_icon_loc,
			"repassword":psd_icon_loc,
			"email":eml_icon_loc
		}
		
		//检测用户名是否已被占用
		function userCheck(val){
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4 && xmlhttp.status==200){
					var result = xmlhttp.responseText;
					if(result=="true"){
					 	$("input[name=username]").css("background",user_icon_loc["OK"]);
					 	$("#err_msg13").hide();
						$("#err_msg1").show();
					} else{
						$("input[name=username]").css("background",user_icon_loc["ERROR"]);
						$("#err_msg13").show();
						$("#err_msg1").hide();
					}
				}
			}
			xmlhttp.open("GET","/login/UserCheckServlet?nameOrEmail="+val,true);
			xmlhttp.send();
		}
		//检测邮箱是否已被占用
		function emailCheck(val){
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4 && xmlhttp.status==200){
					var result = xmlhttp.responseText;
					if(result=="true"){
						$("input[name=email]").css("background",eml_icon_loc["OK"]);
						$("#err_msg4").hide();
					} else{
						$("input[name=email]").css("background",eml_icon_loc["ERROR"]);
						$("#err_msg4").show().text("邮箱已被占用");
					}
				}
			}
			xmlhttp.open("GET","/login/UserCheckServlet?nameOrEmail="+val,true);
			xmlhttp.send();
		}
		
		//提交时验证表格是否可以提交
		function checkForm(){
			var formOK = true;
			//提交时不能包含空值
			$(":text").each(function(){
				if(this.value==""){
					$(this).parents("tr").next().find(".noll").show();
					formOK = false;	
				}				
			})
			//判断是否有err_msg
			//去干扰
			$(".prmt").hide();
			
			//提示未提示的格式错误
			var reg = /^[\u4e00-\u9fa5_a-zA-Z0-9_-]{1,20}$/;
			//检查用户名格式
			if($("input[name=username]").val()!='' && !reg.test($("input[name=username]").val())){
				$("#err_msg14").show();
			}
			var reg = /^[a-zA-Z0-9_-]{6,20}$/;
			//检查密码格式
			if($("input[name=password]").val()!='' && !reg.test($("input[name=password]").val())){
				$("#err_msg23").show();
			}			
			var fail_msg="";
			if($(".err_msg:visible").size()==0){				
			} else{
				//去除换行符
				fail_msg += $(".err_msg:visible").text().trim().replace(/[\t]/g,"");
				formOK = false;
			}
			//提示失败信息
			if(!formOK)alert(fail_msg);
			return formOK;
		}
		
		//生成验证码
		var createCode = function(){
			xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function(){
				if(xhttp.status== 200 && xhttp.readyState==4){
					$("img").attr("src","data:image/jpeg;base64,"+xhttp.responseText);
				}
			}
			xhttp.open("GET","/login/VerifyCodeCreateServlet",true)
			xhttp.send();
		}
		
		$(function(){
			//刷新页面,清屏
			$(":text,:password").each(function(){
				this.value="";
			})			
			//隐藏提示信息
			$(".err_msg").hide();
			//生成验证码
			createCode();
			
			//输入数据时,显示提示信息
			$(":text").focus(function(){				
				//清屏
				$(this).parents("tr").next().find(".err_msg").hide();
				//清空input标签的默认value
				if(this.name == "username"){
					$("#err_msg1").show();
				}
				if(this.name == "password"){
					$("#err_msg2").show();
				}
			})
			
			//没有输入数据时,恢复加载页面时的设置
			//格式不正确,进行提示
			$(":text").blur(function(){
				$(".prmt").hide();
			})
			
			//为username添加keyup事件
			//1. 检查格式,显示错误提示
			//2. 格式正确,发送请求至后台查询数据库,未被占用提示ok,占用则提示被占用
			$("input[name=username]").keyup(function(){
				var reg = /^[\u4e00-\u9fa5_a-zA-Z0-9_-]{1,20}$/;
				//检查格式
				if(!reg.test(this.value)){
					$(this).css("background",user_icon_loc["ERROR"]);
				}else{
					userCheck(this.value);	 
				}				
				//没有输入数据时不进行提示
				if(!this.value){
					$(this).css("background",user_icon_loc["NONE"]);
				}
			})	
			
			//为password输入框添加keyup事件
			//检查格式,显示错误提示
			$("input[name=password]").keyup(function(){
				var reg = /^[a-zA-Z0-9_-]{6,20}$/;
				//检查格式
				if(!reg.test(this.value)){
					$(this).css("background",psd_icon_loc["ERROR"]);
				}else{
					$(this).css("background",psd_icon_loc["OK"]);	 
				}				
				//没有输入数据时不进行提示
				if(!this.value){
					$(this).css("background",psd_icon_loc["NONE"]);
				}
				if(!($("input[name=repassword]").val()=="确认密码")){
					$("input[name=repassword]").keyup();
				}
			})	
			
			//为repassword输入框添加keyup事件	
			//1. 密码不一致,显示错误提示
			//2. 密码一致,显示正确提示
			$("input[name=repassword]").keyup(function(){
				if($("input[name=password]").val()==$("input[name=repassword]").val()){
					$("#err_msg3").hide();
					$("input[name=repassword]").css("background",psd_icon_loc["OK"]);
				} else{
					$("#err_msg3").show();
					$("input[name=repassword]").css("background",psd_icon_loc["ERROR"]);
				}
				
				if(!this.value){
					$(this).css("background",psd_icon_loc["NONE"]);
					$("#err_msg3").hide();
				}
			})
			
			//为email输入框添加keyup事件
			//1. 检查格式,显示错误提示
			//2. 格式正确,发送请求至后台查询数据库,未被占用提示ok,占用则提示被占用
			$("input[name=email]").keyup(function(){
				var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
				//检查格式
				if(!reg.test(this.value)){
					$(this).css("background",eml_icon_loc["ERROR"]);
					$("#err_msg4").show().text("邮箱格式不正确");					
				}else{
					emailCheck(this.value);	 
				}				
				//没有输入数据时不进行提示
				if(!this.value){
					$(this).css("background",eml_icon_loc["NONE"]);
					$("#err_msg4").hide();
				}
			})
			
			//为验证码输入框添加keyup事件
			$("input[name=verifyCode]").keyup(function(){
				var reg = /^[a-zA-Z0-9]{4}$/;
				if(!reg.test(this.value)){
					$(this).css("background",eml_icon_loc["ERROR"]);
				}else{
					$(this).css("background",eml_icon_loc["OK"]);
				}
				if(!this.value){
					$(this).css("background",eml_icon_loc["NONE"]);
				}
			})
			
			//为输入框添加onchange事件:调用该对象的keyup事件
			$(":text").change(function(){
				$(this).keyup();
			})
			
		})