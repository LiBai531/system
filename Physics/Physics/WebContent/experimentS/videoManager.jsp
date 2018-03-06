<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div>
		<table id="experiment_tb"></table>
		<div id="pager_experiment_tb"></div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#experiment_tb").jqGrid({
		url:"/Physics/admin/getVideoList",
		datatype:"json",
		colNames:['id','视频名称','上传日期',''],
		colModel:[
		          {name:"id",index:"id",align:"center",editable:false,hidden:true},
		          {name:"name",index:"id",align:"center",editable:false},
		          {name:"date",index:"id",align:"center",editable:false},
		          {name:"operLook",index:"id",align:"center",editable:false}
		          ],
		rownumber:true,
		height:"100%",
		authwidth:true,
		pager:"#pager_experiment_tb",
		rowNum:20,
		rowList:[20,30,40],
		sortorder:"asc",
		sortName:'',
		jsonReader:{
			root:"rows",
			page:"pageIndex",
			total:"totalPage",
			records:"totalCount",
		},
		gridComplete:function(){
			var ids = jQuery("#experiment_tb").jqGrid('getDataIDs');
			var datas = jQuery("#experiment_tb").jqGrid('getRowData');
			
			for( var i = 0 ; i < ids.length ; i++){
				var operLook = "<a onclick='lookVideo(\""+datas[i]['id']+"\")'>查看</a>"
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{operLook:operLook});
			}
		},
		onselectRow:function(id){
			
		},
		caption:"视频列表",
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});
});


function lookVideo(id){
	var href = "/Physics/student/lookVideo?id="+id;
	$.get(href, function(data){
		  $("#mainContent").empty();
		  $( "#mainContent" ).append( data );
	}, 'html');
}
</script>
</html>