<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src=""></script>
</head>
<body>
<div>
		<form action="" class = "infofr_form user_main_info" id = "infoForm" >
			<fieldset class = "infofr_fieldset">
				<legend class="smallTitle">实验信息：</legend>
				
				<label for="loginId" class = "info_label2">实验名称：</label>
				<input type="text" id = "loginId" name = "loginId" class = "info_inputL_readonly"  value="${experimentInfo['name']}" />
				<span class = "validSpan"></span>
				<br/>
				<label for="name" class = "info_label2">实验地址：</label>
				<input type="text" id = "name" name = "name" class = "info_inputR_readonly"  value="${experimentInfo['address']}"/>
				<span class = "validSpan"></span>
				<br/>
				<label for="loginId" class = "info_label2">人数限制：</label>
				<input type="text" id = "loginId" name = "loginId" class = "info_inputL_readonly"  value="${experimentInfo['personLimit']}" />
				<span class = "validSpan"></span>
				<br/>
				<label for="memo" style="position:relative;top:-40px" class = "info_label2">实验简介：</label>
				<textarea style="width:75%;position：relative;right:40px;" name="memo" rows="10">${experimentInfo['info']}</textarea>
				<span class = "validSpan"></span><br/>
			</fieldset>
			<fieldset class = "infofr_fieldset">
				<legend class="smallTitle">时间设置：</legend>
				
				<label for="loginId" class = "info_label2">实验日期：</label>
                <input id="expTime" size="16" type="text" value="" readonly/>
				<br/>
				<label for="name" class = "info_label2">开始时间</label>
				<select id="startTime">
				    	<option value="1">第一节</option>
				    	<option value="2">第二节</option>
				    	<option value="3">第三节</option>
				    	<option value="4">第四节</option>
				    	<option value="5">第五节</option>
				    	<option value="6">第六节</option>
				    	<option value="7">第七节</option>
				    	<option value="8">第八节</option>
				    	<option value="9">第九节</option>
				    	<option value="10">第十节</option>
				    </select>
				<span class = "validSpan"></span>
				<label for="name" class = "info_label2">结束时间</label>
					<select id="endTime">
				    	<option value="1">第一节</option>
				    	<option value="2">第二节</option>
				    	<option value="3">第三节</option>
				    	<option value="4">第四节</option>
				    	<option value="5">第五节</option>
				    	<option value="6">第六节</option>
				    	<option value="7">第七节</option>
				    	<option value="8">第八节</option>
				    	<option value="9">第九节</option>
				    	<option value="10">第十节</option>
				    </select>
				<span class = "validSpan"></span>
				<br/>
				
			</fieldset>
		</form>
	
</div>
<!-- <div class="control-group">
                <label class="control-label">实验日期</label>
                <div class="controls input-append date form_date" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input id="expTime" size="16" type="text" value="" readonly>
                    <span class="add-on"><i class="icon-remove"></i></span>
					<span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" /><br/>
            </div>
<div>
            	<div>
            		<span class="TextFont">开始时间</span>
				    <select id="startTime">
				    	<option value="1">第一节</option>
				    	<option value="2">第二节</option>
				    	<option value="3">第三节</option>
				    	<option value="4">第四节</option>
				    	<option value="5">第五节</option>
				    	<option value="6">第六节</option>
				    	<option value="7">第七节</option>
				    	<option value="8">第八节</option>
				    	<option value="9">第九节</option>
				    	<option value="10">第十节</option>
				    </select>
            	</div>
            	<div>
            		<span class="TextFont">结束时间</span>
				    <select id="endTime">
				    	<option value="1">第一节</option>
				    	<option value="2">第二节</option>
				    	<option value="3">第三节</option>
				    	<option value="4">第四节</option>
				    	<option value="5">第五节</option>
				    	<option value="6">第六节</option>
				    	<option value="7">第七节</option>
				    	<option value="8">第八节</option>
				    	<option value="9">第九节</option>
				    	<option value="10">第十节</option>
				    </select>
            	</div>
            </div> -->
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#expTime").datepicker({minDate:+7,maxDate:"+1M +7D"});
	$("#expTime").datepicker("option","dateFormat","yy-mm-dd");
	
});
</script>
</html>