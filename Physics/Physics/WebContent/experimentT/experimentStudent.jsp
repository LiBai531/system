<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div style="height:34px;">
		<button id="completeButton" class="btn btn-info" style="width:140px;float:right;" onclick="completeExperiment()">完成实验</button>
		<a id="pdfDownloadButton" class="btn btn-info" style="width:140px;float:right;" onclick="exportPDF()">导出</a>
	</div>
	<div class="selectbar layout_holder">
			<table id="experiment_tb"></table>
			<div id="pager_experiment_tb"></div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	var experimentState = "${experimentState}";
	changeButtonByState(experimentState);
	$("#experiment_tb").jqGrid({
		url:"/Physics/experiment/getExperimentStudentList?expId="+"${expId}",
		datatype: "json",
		mtype:"post",
		colNames:['id','学号','姓名','实验名称','实验状态','得分','操作',''],
		colModel:[
			{name:'experimentStudent.id',index:'joinRule',align:"center", width:50,editable:true,hidden:true},
			{name:'user.loginId',align:"center", width:50,editable:true,sortable:false},
			{name:'user.name',align:"center", width:50,editable:true,sortable:false},
			{name:'experimentInfo.name',align:"center", width:90,editable:true,sortable:false},
			{name:'experimentStudent.state',align:"center",formatter:"select",edittype:"select",editoptions:{value:{"0":'未批改',"1":'已打分',"2":'已完成'}}, width:30,editable:true,sortable:false},
			{name:'experimentStudent.score',align:"center", width:50,editable:true,sortable:false},
			{name:'oper',align:"center", width:30,editable:true,sortable:false},
			{name:'experimentStudent.attachment',align:"center", width:30,editable:true,sortable:false,hidden:true}
			
		],
		rownumbers: true, 
		height:"100%",
		autowidth:true,
		pager: '#pager_experiment_tb',
		rowNum:0,
		rowList:[120],
		multiselect : false,
		viewrecords: true,
		sortorder: "asc",
		sortname:'',
		//multiselect: true,
		//multiboxonly: true,
		jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
            root: "rows",  //包含实际数据的数组  
            page: "pageIndex",  //当前页  
            total: "totalPage",//总页数  
            records:"totalCount", //查询出的记录数  
            repeatitems : false,
        },
		gridComplete: function(){
			var ids = jQuery("#experiment_tb").jqGrid('getDataIDs');
			var datas = jQuery("#experiment_tb").jqGrid('getRowData');
			
			for(var i=0;i < ids.length;i++){
				if( datas[i]['experimentStudent.attachment'] != ""){
					var modify = "";
					if( experimentState == "1")
						modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='getHomework(\""+datas[i]['experimentStudent.id']+"\")'>查看报告</a>";  
					else
					    modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='getHomework(\""+datas[i]['experimentStudent.id']+"\")'>批改报告</a>"; 
					jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper :modify});
				}
			};
		},
		onSelectRow : function(id) {
		},
		caption: "实验学生列表"
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});	
});

function getHomework(id){
	$.get("/Physics/teacher/homework?experimentStudentId="+id, function(data){ 
		  $( "#mainContent" ).empty();
		  $( "#mainContent" ).append( data);
	  }, 'html');
}

//进行一次状态判断
function completeExperiment(){
	var ids = jQuery("#experiment_tb").jqGrid('getDataIDs');
	var datas = jQuery("#experiment_tb").jqGrid('getRowData');
	var isAllComplete = 1;
	console.log(datas);
	for( var i = 0; i < ids.length; i++){
		console.log(datas[i]['experimentStudent.state']);
		if( datas[i]['experimentStudent.state'] == "0")
			isAllComplete = 0;
	}
	
	if( isAllComplete == 0){
		notAllCompleteDialog();
	}else{
		completeExperimentReal();
	}
}
//真正的完成实验的函数
function completeExperimentReal(){
	var experimentId = "${expId}";
	$.get("/Physics/teacher/completeExperiment?experimentId="+experimentId, function(data){ 
		  $( "#mainContent" ).empty();
		  $( "#mainContent" ).append( data);
	}, 'html');
}

function notAllCompleteDialog(){
	$("#dialog").empty();
	$('#dialog').append( "你还没用对全部的实验报告进行评分，是否确认<b>完成实验</b>?" );
	$('#dialog').dialog({
		    title:"警告",
		    height:'150',
			width:'150',
			position:'center',
			modal:true,
			draggable:true,
		    hide:'fade',
			show:'fade',
		    autoOpen:true,
	  		buttons:{  
		    	"确定":function(){ 
		    		$("#dialog").dialog("close");
		    		completeExperimentReal();
	            },
	            "关闭":function(){
	            	$("#dialog").dialog("close");
	            }
		    }
	  });
}

function changeButtonByState(experimentState){
	if( experimentState == "1")
		$("#completeButton").hide();
}

$('#pdfDownloadButton').click(function(){
	var url="/Physics/teacher/pdfDownload?experimentId=${expId}";
	document.getElementById("pdfDownloadButton").href=url;
});
</script>
</html>