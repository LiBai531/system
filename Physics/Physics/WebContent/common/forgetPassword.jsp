<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
			<fieldset class = "infofr_fieldset">
				<legend class="smallTitle">邮件找回密码：</legend>
				
				<label for="loginId" class = "info_label2">登录帐号：</label>
				<input type="text" id = "EmailLoginId" name = "EmailLoginId" class = "info_inputL_readonly"  />
				<span class = "validSpan"></span>
				
				<br/>
				<button type="button" class="btn btn-info" style="width:140px" onclick="sendEmail()">确认</button>
				<br/>
			</fieldset>
</body>
<script type="text/javascript">
function sendEmail(){
	$.ajax({
        type: "POST",
        url: "/Physics/rbac/forgetPassword",
        data: {loginId:$("#EmailLoginId").val()},
        dataType: "json",
        
        complete: function(result){	
        	
        	var list = eval(result['responseText']);
        	
        }
    });
}
</script>
</html>