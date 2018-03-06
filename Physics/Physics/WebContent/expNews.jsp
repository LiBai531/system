<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/Physics/js/jquery-ui-1.10.3.custom.js"></script>
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
<script type="text/javascript">
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
<body>

<ul class="info_list news_list" style="width:80%;margin-left:10%;">
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="item">
				<li onclick="showNews('${item.id}')">
					<span class="hidden">${item.id}</span>
					<span class="news_normal" style="float:right;margin-right:20px;font-weight:bold; ">
                    	from ${item.user.name}
                    </span>
					
			        <span class="news_important" style="display:block;font-size:14px;font-weight:bold; ">[  ${item.content.head}  ]</span>
                    <span class="news_normal">
                    	<c:set var="date"  value="${item.content.content}"/> 
						内容摘要：${fn:substring(date, 0, 35)}......
                    </span>
					<span class="news-date">
						${item.calander}
					</span>
				</li>
			</c:forEach>	
		</c:if> 
	</ul>

</body>

</html>