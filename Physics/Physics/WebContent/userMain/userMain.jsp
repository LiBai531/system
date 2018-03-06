<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<script src="/Physics/js/index.jquery.js"></script>
    <div >
        <div id="hori_menu">
			<ul id="menu_holder">
				<!-- 动态生成菜单权限 -->
				<c:forEach items="${menu}" var="tree" varStatus="status">
					<li class="left">
						<c:if test="${tree.state != '0'}">	
							<a class="hori_lvl1" href="${tree.href}"><span>${tree.name}</span></a>
						</c:if>
						<c:if test="${!empty tree.menuSeconds}">				
							<ul class="hori_lvl2">
								<li>
									<c:forEach items="${tree.menuSeconds}" var="children">
										<c:if test="${children.state != '0'}">	
											<a href="${children.href}">${children.name}</a>
										</c:if>
									</c:forEach>
								</li>
							</ul>
						</c:if>
					</li>
				</c:forEach>		
				<li class="right"> 
					<span class="highlight_info">欢迎您,${userSession.name} </span> |
					<a href="#" class="alert_info" style="font-size:17px;display:inline;" onclick="userLogout()">退出系统</a>
				</li>
				<div class="clear"></div>
			</ul>
		</div>
       
    </div>
    <div class="row col-md-12" style="margin-left: 4.1%;height: 20px"></div>
    <div id="mainContent" class="row col-md-11" style="margin-left:5%;">
        <div class="row col-md-11" style="margin-left:5%;">
        		<div class="col-md-3" style="width:20%;">
            		

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
    </div>
    <div class="row col-md-11" style="margin-left: 4.1%;height: 100px"></div>
    
<script type="text/javascript">

$(document).ready(function(){
	var userState = "${state}";
	if( userState == "0"){
		
	}else if( userState == "1"){
		$('#dialog').empty(); 
		$('#dialog').append("您使用的是初始密码登陆，请尽快去修改密码！");
		$('#dialog').dialog({
			    title:"提示", 
			    height:'200',
				width:'200',
				position:'center',
				modal:true,
				draggable:true,
			    hide:'fade',
				show:'fade',
			    autoOpen:true,
		  		buttons:{  
		            "关闭":function(){
		            	$("#dialog").dialog("close");
		            }
			    }
		  });
	}
	
	getExperimentInfo();
	getNewsInfo();
});

function userLogout(){
	$.ajax({
        type: "POST",
        url: "/Physics/rbac/logout",
        data: {},
        dataType: "json",
        complete: function(result){
        	location.reload();
        }
    });
}

function getExperimentInfo(){
	$.ajax({
        type: "POST",
        url: "/Physics/rbac/PageGetExperimentInfo",
        data: {},
        dataType: "json",
        
        complete: function(result){	
        	$("#pageExperList").empty();
        	var listB = "<li><a class='newa' onclick='showExpInfo(\"";
        	var listB2 = "\")'><nobr>";
        	var listE = "</nobr></a></li>";
        	var list = eval(result['responseText']);
        	var text = "";
        	for(var i = 0; i < list.length;i++){
        		if(list[i]['state'] == "0")
        			continue;
        		text += listB+list[i]['id']+listB2+list[i]['name']+listE;
        	}
        	console.log(list[0].name);
        	$("#pageExperList").append(text);
        }
    });
}

function getNewsInfo(){
	$.ajax({
        type: "POST",
        url: "/Physics/rbac/PageGetNews",
        data: {},
        dataType: "json",
        
        complete: function(result){	
        	$("#pageNewsList").empty();
        	var listB = "<li><a class='newa' onclick='showNews(\"";
        	var listB2 = "\")'><nobr>";
        	var listE = "</nobr></a></li>";
        	var list = eval(result['responseText']);
        	var text = "";
        	for(var i = 0; i < list.length;i++){
        		text += listB+list[i]['id']+listB2+list[i].content.head+listE;
        	}
        	console.log(list[0].name);
        	$("#pageNewsList").append(text);
        }
    });
}


function showExpInfo(id){
	$.get('/Physics/experiment/expInfoPage?id='+id, function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
	  		    title:"实验介绍",
	  		    height:'550',
	  			width:'900',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	   	  		buttons:{  
	 	            "关闭":function(){
	 	            	$("#dialog").dialog("close");
	 	            }
	  		    }
	 	  }); 
	 	}, 'html');
		return true; 
}

function showNews(id){
	$.get('/Physics/news/newsPage?id='+id, function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
	  		    title:"新闻信息",
	  		    height:'550',
	  			width:'900',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	   	  		buttons:{  
	 	            "关闭":function(){
	 	            	$("#dialog").dialog("close");
	 	            }
	  		    }
	 	  }); 
	 	}, 'html');
		return true; 
}
</script>
