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
		url:"/Physics/student/getExperimentEndList",
		datatype:"json",
		colNames:['id','实验名称','实验介绍','老师','实验日期','实验课时','得分','下载报告',''],
		colModel:[
		          {name:"experimentStudent.id",index:"experimentStudent.id",align:"center",editable:false,hidden:true},
		          {name:"experimentInfo.name",index:"experimentStudent.id",align:"center",editable:false},
		          {name:"experimentInfo.info",index:"experimentStudent.id",align:"center",editable:false},
		          {name:"teacher.name",index:"experimentStudent.id",align:"center",editable:false},
		          {name:"experiment.calendar",index:"experimentStudent.id",align:"center",editable:false},
		          {name:"time",index:"experimentStudent.id",align:"center",editable:false},
		          {name:"experimentStudent.score",index:"experimentStudent.id",align:"center",editable:false},
		          {name:"oper",index:"experimentStudent.id",align:"center",editable:false},
		          {name:"experimentStudent.id",index:"experimentStudent.id",align:"center",editable:false,hidden:true},
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
				var oper = "<a id='"+datas[i]['experimentStudent.id']+"' onclick='downLoadHomework(\""+datas[i]['experimentStudent.id']+"\")'>下载</a>"
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper:oper});
			}
		},
		onselectRow:function(id){
			
		},
		caption:"已完成实验",
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});
});

function downLoadHomework(id){
	var url="/Physics/teacher/downloadHomework?experimentStudentId="+id;
	document.getElementById(id).href=url;
}
</script>
</html>