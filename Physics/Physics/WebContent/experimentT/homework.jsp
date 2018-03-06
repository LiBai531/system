<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	input {
	    display: block;
	    width: 140px;
	    padding: 6px 12px;
	    font-size: 14px;
	    line-height: 1.428571429;
	    color: #555;
	    vertical-align: middle;
	    background-color: #fff;
	    background-image: none;
	    border: 1px solid #ccc;
	    border-radius: 4px;
	    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
	    box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
	    -webkit-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	}
</style>
</head>
<body>
	<div style="height:50px;float:left;">
		<span class="panel-title">批改报告</span>
	</div>
	<div style="height:50px;float:right;">
		
		<input id="score" placeholder="得分" style="display:inline;">
		<button id="setScoreButton" type="button" class="btn btn-info" style="width:140px" onclick="setScore()">确定</button>
		<a id="downloadHomework" type="button" class="btn btn-info" style="width:140px">下载文档</a>
		<button type="button" class="btn btn-info" style="width:140px" onclick="goBack()">返回</button>
	</div>
    <embed width="100%"  
                   height="600px"  
                   name="plugin"  
                   src="${fileUri}"  
                   type="application/pdf"  
    /> 
</body>

<script>
var experimentStudentId = "${experimentStudentId}";
var score = "${score}";
var experimentState = "${experimentState}";
$(document).ready(function(){
	if( score != 0)
		$("#score").val(score);
	if( experimentState =="1")
		$("#setScoreButton").hide();
});
	function setScore(){
		$.ajax({
			type:"POST",
			url:"/Physics/teacher/setScore",
			data:{score:$('#score').val(),experimentStudentId:experimentStudentId},
			dataType:"json",
			complete:function(result){
				console.log(result);
				if(result['responseText'] == "error"){
					alert_dialog("非法操作!");
				}else{
					alert_dialog("操作成功！");
				}
			}
		});
	}
	
	$('#downloadHomework').click(function(){
		var url="/Physics/teacher/downloadHomework?experimentStudentId="+experimentStudentId;
		document.getElementById("downloadHomework").href=url;
	});
	
	function goBack(){
		$.ajax({
            type: "POST",
            url: "/Physics/teacher/getExperimentStudent?experimentStudentId="+experimentStudentId,
            data: {},
            dataType: "json",
            complete: function(result){	
            	$("#mainContent").empty();
            	$("#mainContent").append(result['responseText']);
            }
        });
	}
</script>
</html>