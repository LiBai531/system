<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
	<form action="" class = "infofr_form user_main_info" id = "infoForm" >
			<fieldset class = "infofr_fieldset">
				<legend class="smallTitle">实验基础信息：</legend>
				
				<label for="loginId" class = "info_label2">实验名称：</label>
				<input type="text" id="expName" name = "loginId" class = "info_inputL_readonly"  value="${experimentInfo['name']}" />
				<span class = "validSpan"></span>
				<br/>
				<label for="name" class = "info_label2">实验地址：</label>
				<input type="text" id="expAddress" name = "name" class = "info_inputR_readonly"  value="${experimentInfo['address']}"/>
				<span class = "validSpan"></span>
				<br/>
				<label for="loginId" class = "info_label2">人数限制：</label>
				<input type="text" id="expLimit" name = "loginId" class = "info_inputL_readonly"  value="${experimentInfo['personLimit']}" />
				<span class = "validSpan"></span>
				<br/>
				<label for="memo" style="position:relative;top:-40px" class = "info_label2">实验简介：</label>
				<textarea id="expInfo" style="width:75%;position：relative;right:40px;" name="memo" rows="10">${experimentInfo['info']}</textarea>
				<span class = "validSpan"></span><br/>
				
			</fieldset>
	</form>
	<%-- <div class="input-group">
         <span class="input-group-addon">实验名称</span>
         <input id="expName" type="text" value="${experimentInfo['name']}" class="form-control">
    </div>
    
    <div class="input-group">
         <span class="input-group-addon">实验地址</span>
         <input id="expAddress" type="text"  value="${experimentInfo['address']}" class="form-control" >
    </div>
      
      <div class="input-group">
         <span class="input-group-addon">人数限制</span>
         <input id="expLimit" type="text" value="${experimentInfo['personLimit']}" class="form-control" >
      </div>
      
      
      <div class="input-group">
         <span class="input-group-addon">实验简介</span>
         <textarea id="expInfo" name="expInfo"  cols="45" rows="5" class="form-control" >${experimentInfo['info']}</textarea>
      </div> --%>
</div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</html>