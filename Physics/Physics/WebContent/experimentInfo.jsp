<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link  rel="stylesheet" href="/Physics/css/jquery-ui.theme.min.css">
<script type="text/javascript" src="/Physics/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="/Physics/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="/Physics/js/jquery-ui-1.10.3.custom.js"></script> -->
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

</style>
<body>
	<ul class="info_list news_list" style="width:80%;margin-left:10%;">
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="item">
				<c:if test="${item.state==1}">
					<li onclick="showExpInfo('${item.id}')">
						<span class="hidden">${item.id}</span>
				        <span class="news_important" style="display:block;font-size:14px;font-weight:bold; ">[  ${item.name}   ]</span>
	                    <span class="news_normal" style="margin-left:30px">
	                    	<c:set var="date"  value="${item.info}"/> 
							实验简介：${fn:substring(date, 0, 45)}......
	                    </span>
					</li>
				</c:if>
			</c:forEach>	
		</c:if> 
	</ul>
<script type="text/javascript">
function showExpInfo(id){
	$.get('/Physics/experiment/expInfoPage?id='+id, function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
	  		    title:"实验介绍",
	  		    height:'450',
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
</body>
</html>