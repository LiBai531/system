<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
    <meta charset="utf-8">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/Physics/bootstrap/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="/Physics/bootstrap/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script type="text/javascript" src="/Physics/js/jquery-1.9.1.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="/Physics/bootstrap/js/bootstrap.min.js"></script>
    <title>
        通信工程实验室信息平台
    </title>
    <style>
        nav div ul li{
            width: 120px;
            text-align: center;
            font-weight: bold;

        }
        .navbar-link{
            text-decoration:underline;
            color: blue;
        }
        .panel-body ul li {
            font-size: 10px;
            text-overflow: ellipsis;
            overflow: hidden;
            color: #6c6c6c;
            border: none;
            border-bottom:1.5px dashed #c0c0c0 ;
            margin-top: 5px;
        }

        .panel-default>.panel-heading{
            background-image: linear-gradient(to bottom,white 0,white 100%);
        }
        .panel-title{
            color: #2E7498;
            font-weight: bold;
            font-family: 隶书;
            font-size: 24px;
        }
        .newa{
            color: #6c6c6c;
            font-family: "宋体";
        }
    </style>
</head>
<body>
    <div class="col-md-12"
     style="background: url(/Physics/img/headlogup.jpg) no-repeat center;height: 40px;background-color: #ECF4F6">
    </div>
    <div class="col-md-12"
         style="background: url(/Physics/img/headlogdown.jpg) no-repeat center;height: 146px;background-color: #78BFE9">
    </div>
    <div class="col-md-11 " style="margin-left: 4.1%">
        <nav class="navbar navbar-default" role="navigation">

            <div class="">
                <ul class="nav navbar-nav">
                     <li class="active"><a id="leadPage">首页 </a></li>
                    <li><a id="expNews">实验新闻</a></li>
                    <li><a id="experimentInfo">实验简介</a></li>
                    <li class="dropdown">
			            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
			               	我的实验 
			               <b class="caret"></b>
			            </a>
			            <ul class="dropdown-menu">
			               <li><a id="chooseExperiment">选择实验</a></li>
			               <li><a id="studentExperiment">已选实验</a></li>
			               <li class="divider"></li>
			               <li><a href="#">已完成实验</a></li>
			               
			            </ul>
			        </li>
                </ul>
            </div>
            <div>
                <form class="navbar-form navbar-left" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="站内搜索">
                    </div>
                    <button type="submit" class="btn btn-default">查询</button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <p class="navbar-text navbar-right"><b></b>
                
                    ${userName}
                    <b><span>欢迎登陆！</span></b>
                </p>
            </div>
        </nav>
    </div>
    <div id="mainContent" class="row col-md-11" style="margin-left: 4.1%;">
        <div class="col-md-3">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        实验新闻
                    </h3>
                </div>
                <div class="panel-body">
                    <ul>
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
                    font-size: 8px;padding-bottom: 2px;padding-top: 2px">更多》</span>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title" >
                        实验室信息
                    </h3>
                </div>
                <div class="panel-body" style="font-family:楷书;font-size: 20px">
                  &nbsp;&nbsp;&nbsp;&nbsp;哈尔滨工业大学通信工程实验室以培养创新型、研究型人才为目标，构建先进、科学的通信教学与实验体系，为哈尔滨工业大学培养高素质创新型人才打下坚实的通信理论与实验基础。
                    <br><br>
                    &nbsp;&nbsp;&nbsp;&nbsp;通信工程实验室以实验教学为主。开设移动通信、卫星定位与导航等多门课程的实验，促进学生理解、掌握课本知识，增强动手能力。
                    <br><br>
                    &nbsp;&nbsp;&nbsp;&nbsp;我校通信实验室年科研经费超过1500万元，拥有美国CANDENCE公司的卫星网络专用仿真软件SATLAB，以及CADENCE公司的网络级仿真软件BONES、信号级仿真软件SPW。以及10多台SUN工作站和200多台个人计算机终端。另外还有VSAT卫星地面站、小卫星地面测控站、ATMI广域网交换机、局域网(工作组)交换机及DSP、FPGA开发、移动卫星电波测试、AD-HOC网络演示和二代导航开发等平台。

                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel panel-default">
                <div class="panel-body" style="background: url(/Physics/img/sz.JPG) no-repeat center;height: 254px">

                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        师生问答
                    </h3>
                </div>
                <div class="panel-body">
                    <ul>
                        <li>
                            <a class="newa"><nobr>1.如何做好实验</nobr></a>
                        </li>
                        <li>
                            <a class="newa"><nobr>2.如何做好实验如何做好实验如何做好实验实验平台已经开通，欢迎大家使用</nobr></a>
                        </li>
                        <li>
                            <a class="newa"><nobr>3.如何做好实验如何做好实验如何做好实验实验平台已经开通，欢迎大家使用欢迎大家使用欢迎大家使用</nobr></a>
                        </li>
                        <li>
                            <a class="newa"><nobr>4.如何做好实验如何做好实验如何做好实验实验平台已经开通，欢迎大已经开通，欢迎大已经开通，欢迎大家使用</nobr></a>
                        </li>
                        <li>
                            <a class="newa"><nobr>5.如何做好实验如何做好实验如何做好实验实验平台已经开通，欢迎大家使用</nobr></a>
                        </li>
                        <li>
                            <a class="newa"><nobr>6.如何做好实验如何做好实验如何做好实验实验平台已经开通，欢迎大家使用</nobr></a>
                        </li>

                    </ul>
                    <span class="label label-info" style="float: right;
                    font-size: 8px;padding-bottom: 2px;padding-top: 2px">更多》</span>
                </div>
            </div>
        </div>
    </div>
    <div class="row col-md-11" style="margin-left: 4.1%;height: 100px"></div>
    <nav class="navbar navbar-inverse navbar-fixed-bottom" style="margin-top: 150px" role="navigation">
        <div class="container" style="color: #ffffff;text-align: center;line-height: 24.5px">
          Copyright 2016 © 哈尔滨工业大学电信学院通信工程系 技术支持 李忻瑶
        </div>

    </nav>
<div id="dialog"></div>
<script  type="text/javascript" >
$(document).ready(function(){
	$('#chooseExperiment').click(function(){
		$.ajax({
            type: "POST",
            url: "/Physics/student/getExperiment",
            data: {},
            dataType: "json",
            complete: function(result){	
            	console.log(result);
            	$("#mainContent").empty();
            	$("#mainContent").append(result['responseText']);
            }
        });
	});
	
	$('#studentExperiment').click(function(){
		$.ajax({
            type: "POST",
            url: "/Physics/student/studentExperiment",
            data: {},
            dataType: "json",
            complete: function(result){	
            	console.log(result);
            	$("#mainContent").empty();
            	$("#mainContent").append(result['responseText']);
            }
        });
	});
	
	
	
	$('#leadPage').click(function(){
		$.ajax({
            type: "POST",
            url: "/Physics/rbac/leadPage",
            data: {},
            dataType: "json",
            complete: function(result){	
            	console.log(result);
            	$("#mainContent").empty();
            	$("#mainContent").append(result['responseText']);
            }
        });
	});
	
	$('#expNews').click(function(){
		$.ajax({
            type: "POST",
            url: "/Physics/rbac/expNews",
            data: {},
            dataType: "json",
            complete: function(result){	
            	console.log(result);
            	$("#mainContent").empty();
            	$("#mainContent").append(result['responseText']);
            }
        });
	});
	
	$('#experimentInfo').click(function(){
		$.ajax({
            type: "POST",
            url: "/Physics/rbac/experimentInfo",
            data: {},
            dataType: "json",
            complete: function(result){	
            	console.log(result);
            	$("#mainContent").empty();
            	$("#mainContent").append(result['responseText']);
            }
        });
	});
	
});
</script>
</body>
</html>