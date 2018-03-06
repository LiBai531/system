<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
	<div class="input-group">
         <span class="input-group-addon">用户名称</span>
         <input id="userName" type="text" value="" class="form-control">
    </div>

      
      <div class="input-group">
         <span class="input-group-addon">登录名称</span>
		<input id="loginId" type="text" value="" class="form-control">
      </div>
      
      <div class="input-group">
         	<span class="input-group-addon">用户类型</span>
         	<input type="radio" name="userType" value="1" checked="checked" />学生 
			<input type="radio" name="userType" value="2" />教师 
			<input type="radio" name="userType" value="3" />管理员
      </div>
</div>
</body>
</html>