<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
	
	table tr tb label{
		font-size:20px;
		font-family:宋体;
		font-weight:800;
	}
	
	td{
		border:0;
		border-bottom: #c0c0c0 1px solid ;
		margin-bottom:20px;
	}
	
	tr{
		margin-bottom:20px;
	}
	
	input{
		border:0;
	}
</style>
<div style="width:90%;margin-left:5%;">
	<div style="border-bottom: rgb(78, 78, 88) 2px double ;margin-bottom:20px;">
		<h1 style="  color: rgb(44, 153, 226);">用户基本信息：</h1>
	</div>
	<div>
		<form action="" class = "infofr_form user_main_info" id = "infoForm" >
			<fieldset class = "infofr_fieldset">
				<legend class="smallTitle">基本信息：</legend>
				
				<label for="loginId" class = "info_label2">登录帐号：</label>
				<input type="text" id = "loginId" name = "loginId" class = "info_inputL_readonly" readonly value="${user.user.loginId}" />
				<span class = "validSpan"></span>
				<label for="name" class = "info_label2">用户类型：</label>
				<input type="text" id = "name" name = "name" class = "info_inputR_readonly" readonly value="${user.userType}"/>
				<span class = "validSpan"></span>
				<br/>
				<label for="loginId" class = "info_label2">用户状态：</label>
				<input type="text" id = "loginId" name = "loginId" class = "info_inputL_readonly" readonly value="${user.userState}" />
				<span class = "validSpan"></span>
				<label for="name" class = "info_label2">初始密码：</label>
				<input type="text" id = "name" name = "name" class = "info_inputR_readonly" readonly value="${user.user.initPassword}"/>
				<span class = "validSpan"></span>
				<br/>
			</fieldset>
			<fieldset class = "infofr_fieldset">
				<legend class="smallTitle">个人信息：</legend>
				
				<label for="loginId" class = "info_label2">用户名称：</label>
				<input type="text" id="user_name" name = "loginId" class = "info_inputL_readonly canInput" readonly value="${user.user.name}" />
				<span class = "validSpan"></span>
				<label for="name" class = "info_label2">邮箱：</label>
				<input type="text" id="user_Email" name = "name" class = "info_inputR_readonly canInput" readonly value="${user.user.email}"/>
				<span class = "validSpan"></span>
				<br/>
				
			</fieldset>
		</form>
	</div>
	<div style="float:right;margin-right:15%;margin-top:20px;">
		<button id="edit" type="button" class="btn btn-info">编辑</button>
		<button id="save" type="button" class="btn btn-warning">保存</button>
	</div>
	

</div>
<script type="text/javascript">
	$("#edit").click(function(){
		$(".canInput").removeAttr("readonly");
		$(".canInput").css("border","1px solid #B7B7B7");
	});
	
	$("#save").click(function(){
		if(!checkEmail($("#user_Email").val())){
			alert_dialog("邮箱不正确");
			return;
		}
		$.ajax({
            type: "POST",
            url: "/Physics/rbac/changeUserInfo",
            data: {user_name:$("#user_name").val(), user_Email:$("#user_Email").val(),user_loginId:$("#loginId").val()},
            dataType: "json",
           
            complete: function(result){	
            	if(result['responseText'] == "success"){
            		$("input").attr({readonly:"readonly"});
            		$("input").css("border","0px solid");
            	}else{
            		alert_dialog(result['responseText']);
            	}
            }
        });
		
	});
	
	function checkEmail(str){
	    var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	    return re.test(str);
	}

</script>
