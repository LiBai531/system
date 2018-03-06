<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<style>
.info_list
{
	list-style:none;
	padding:10px;
	
}

.info_list li
{
	font-size:12px;
	color:#000; 
	border-bottom:1px dashed #000;
	line-height:28px;
}

.info_list li .news-date
{
	float:right;
	font-size:12px;
	font-family:"MicroSoft YaHei";
	color:#47A6E6;
}
.news_list:hover
{
	color:Red; 
	cursor:pointer;
}
.loginInput{
        	display: block;
		    width: 100%;
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
<body>
 <div id="mulu" hide=true>
    	 <div id="hori_menu">
			<ul id="menu_holder" >
				<li class="left">
					<a  id="leadPage" href="/Physics/rbac/main" class="hori_lvl1" ><span>首页 </span></a>
				</li>
				<li class="left">
					<a   href="/Physics/rbac/expNews" class="hori_lvl1" ><span>实验新闻</span></a>
					
				</li>
				<li class="left">
					<a  href="/Physics/rbac/experimentInfo" class="hori_lvl1"><span>实验简介</span></a>
				</li>
				
			</ul>
			
		</div>
		
    </div>
    		<div class="row col-md-11" style="margin-left:5%;">
        		<div class="col-md-3" style="width:20%;">
            		<div class="panel panel-default">
		                <div class="panel-heading">
		                    <h3 class="panel-title" >
		                       	 用户登陆
		                    </h3>
		                </div>
		                <div class="panel-body">
		                    
		                    <table>
		                    	<tr>
		                    		<td><input id="usernameDiv" type="text" class="form-control loginInput"  placeholder="输入用户名" name="username"></td>
		                    		<td><label id="user_checkcode_info1"  generated="true" class="error" style="display:none;color:red;" >用户不存在</label></td>
		                    	</tr>
		                    	<tr>
		                    		<td> <input id="passwordDiv" type="password" class="form-control loginInput"  placeholder="输入密码" name="password"></td>
		                    	    <td><label id="user_checkcode_info2"  generated="true" class="error" style="display:none;color:red;" >密码错误</label></td>
		                    	</tr>
		                    	
		                    </table>
		                    <table>
		                    	<tr>
		                    		<td>
		                    			<input id="user_checknum" name="userCheckNum" value="" 
		                    			class="input_text input_yzm input_text_focus loginInput" style="width:60%;" type="text" value=""  placeholder="验证码">
		                    		</td>
		                    		<td>
		                    			<img id="sub_right_img"  src= "/Physics/rbac/generatecode" 
		                    			onclick="changeValidateCode()" title="刷新验证码" style="cursor: pointer;height:26px;" />
		                    		</td>
		                    	</tr>
		                    </table>
		                    <div style="height:10px;"></div>
		                    		<button id="userLoginNew" type="button" class="btn  btn-info"
		                            style="">登&nbsp;录</button>
		                    		 <a href="" style="float:right;">忘记密码</a>
		                </div>
            		</div>

		            <div class="panel panel-default">
		                <div class="panel-heading">
		                    <h3 class="panel-title">
		                       	实验新闻
		                    </h3>
		                </div>
		                <div class="panel-body">
		                    <ul id="pageNewsList">
		                        <li>
		                            <a class="newa"><nobr>1.实验平台已经开通，欢迎大家使用</nobr></a>
		                        </li>
		                        <li>
		                            <a class="newa"><nobr>2.实验平台已经开通，欢迎大家使用</nobr></a>
		                        </li>
		                        <li>
		                            <a class="newa"><nobr>3.实验平台已经开通，欢迎大家使用欢迎大家使用欢迎大家使用</nobr></a>
		                        </li>
		                        <li>
		                            <a  class="newa"><nobr>4.实验平台已经开通，欢迎大已经开通，欢迎大已经开通，欢迎大家使用</nobr></a>
		                        </li>
		                        <li>
		                            <a class="newa"><nobr>5.实验平台已经开通，欢迎大家使用</nobr></a>
		                        </li>
		                        <li>
		                            <a class="newa"><nobr>6.实验平台已经开通，欢迎大家使用</nobr></a>
		                        </li>
		
		                        </li>
		                    </ul>
		                    <span class="label label-info" style="float: right;
		                    font-size: 8px;padding-bottom: 2px;padding-top: 2px" onclick="moreClick(1)">更多》</span>
		                </div>
		            </div>
		            
        		</div>
		        <div class="col-md-5" style="width:40%;">
		            <div class="panel panel-default">
		                <div class="panel-heading">
		                    <h3 class="panel-title" >
		                        实验室信息
		                    </h3>
		                </div>
		                <div class="panel-body" style="font-family:楷书;font-size: 20px">
		                    &nbsp;&nbsp;&nbsp;&nbsp;哈尔滨工业大学通信工程专业始建于1959年。本专业分别于1980年、1986年和1999年建立了硕士点、博士点和博士后流动站。1997年被确定为我校“211工程”重点建设学科群之一，1998年被确定为全国首批“长江计划”特聘教授岗位学科，其一级学科信息与通信工程1999年被确定为一级学科博士点。本专业所属二级学科通信与信息系统学科，在2001年被确定为国家重点学科。通信工程专业具有较强的教学科研队伍，张乃通院士为该专业的学术带头人。本专业现有教授13人（其中博士生导师十人），副教授13人，讲师11人。
		                    <br><br>
		                    &nbsp;&nbsp;&nbsp;&nbsp;通信工程专业具有与国民经济和国防建设紧密联系的、稳定的教学科研方向，有良好的教学和科研基础，为地方和国防工业部门培养了大量的专业人才。在科研方面紧密结合航天尖端技术的需求，形成了以星载和武器系统数据链为主要背景的通信系统研究方向。尤其是在武器指挥控制系统传输、卫星通信系统、集群移动通信系统等主要研究方向上已达到国内领先水平。近些年共获得国家科技进步二等奖1项、三等奖3项，省部级科技进步一等奖5项、二、三等奖20余项。专业综合实力居国内高校同类专业前列。通信工程实验室始建于1963年，隶属于哈尔滨工业大学通信工程专业，是针对通信与信息系统学科本科生设立的专业实验室，部分实验项目亦可对校内相关专业本科生开放。
		                    <br><br>
		                    &nbsp;&nbsp;&nbsp;&nbsp;通信工程实验室现位于新技术楼十层，使用面积600平方米。设有综合实验室、演示实验室、计算机实验室、生产实习实验室以及办公室、会议室、资料室、器件室等。目前设有专职实验室教师两名。开设的课程有：通信原理实验、程控交换技术实验、移动通信实验、卫星通信实验。通信工程实验室开设的实验课程为本科生理论联系实际，加深对通信专业理论的理解和掌握，提供了必要的条件。
		                </div>
		            </div>
		        </div>
		        <div class="col-md-4" style="width:30%;">
		            <div class="panel panel-default">
		                <div class="panel-heading">
		                    <h3 class="panel-title">
		                       	 实验介绍
		                    </h3>
		                </div>
		                <div class="panel-body">
		                    <ul id="pageExperList">
		                        <li>
		                            <a class="newa"><nobr>1.如何做好实验</nobr></a>
		                        </li>
		                    </ul>
		                    <span class="label label-info" style="float: right;
		                    font-size: 8px;padding-bottom: 2px;padding-top: 2px" onclick="moreClick(2)">更多》</span>
		                </div>
		            </div>
		            
		        </div>
    		</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
		
	$.ajax({
        type: "POST",
        url: "/Physics/rbac/AlreadyLogin",
        data: {},
        dataType: "json",
        
        complete: function(result){
        	console.log(result);
        	if(result['responseText'] == "yes"){
        		sessionUser();
        	}
        }
    });
	getExperimentInfo();
	getNewsInfo();
	
	$("#userLoginNew").click(function(){
		$.ajax({
            type: "POST",
            url: "/Physics/rbac/user_login",
            data: {username:$("#usernameDiv").val(), password:$("#passwordDiv").val(),userCheckNum:$("#user_checknum").val()},
            dataType: "json",
           
            complete: function(result){	
            	if( result['responseText'] == 1){
            		alert_dialog("请刷新验证码");
            		return;
            	}
            	if( result['responseText'] == 2){
            		alert_dialog("验证码错误");
            		return;
            	}
            	if( result['responseText'] == 3){
            		alert_dialog("用户不存在");
            		return;
            	}
            	if( result['responseText'] == 4){
            		alert_dialog("密码错误");
            		return;
            	}
            	$("#the_whole_page").empty();
            	$("#the_whole_page").append(result['responseText']);
            }
        });
	});
});
</script>
</html>