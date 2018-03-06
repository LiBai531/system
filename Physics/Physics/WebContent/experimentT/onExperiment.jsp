<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="selectbar layout_holder">
			<table id="onExperiment_tb"></table>
			<div id="pager_onExperiment_tb"></div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#onExperiment_tb").jqGrid({
		url:"/Physics/experiment/getOnexperimentByTeacher",
		datatype: "json",
		mtype:"post",
		colNames:['id','实验名称','实验日期','实验课时','实验室','人数限额','实验人数空位','操作'],
		colModel:[
			{name:'experiment.id',index:'joinRule',align:"center", width:50,editable:true,hidden:true},
			{name:'experimentInfo.name',align:"center", width:50,editable:true,sortable:false},
			{name:'experiment.calendar',align:"center", width:30,editable:true,sortable:false},
			{name:'time',align:"center", width:30,editable:true,sortable:false},
			{name:'experimentInfo.address',align:"center", width:50,editable:true,sortable:false},
			{name:'experimentInfo.personLimit',align:"center", width:30,editable:true,sortable:false},
			{name:'experimentCount.count',align:"center", width:30,editable:true,sortable:false},
			{name:'oper',align:"center", width:30,editable:true,sortable:false}
			
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_onExperiment_tb',
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
			var ids = jQuery("#onExperiment_tb").jqGrid('getDataIDs');
			var datas = jQuery("#onExperiment_tb").jqGrid('getRowData');
			
			for(var i=0;i < ids.length;i++){
				console.log(ids[i]);
				var modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='editExperiment(\""+datas[i]['experiment.id']+"\")'>选择  |</a>"; 
				modify += "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='deleteExperiment(\""+datas[i]['experiment.id']+"\")'>  删除</a>"; ;
				jQuery("#onExperiment_tb").jqGrid('setRowData',ids[i],{oper :modify});
					
			};
		},
		onSelectRow : function(id) {
		
		},
		caption: "在进行实验信息列表"
	}).navGrid('#pager_onExperiment_tb',{edit:false,add:false,del:false});	
	
	
});

function editExperiment(id){
		$.ajax({
            type: "POST",
            url: "/Physics/experiment/getExperimentStudent?id="+id,
            data: {},
            dataType: "json",
            complete: function(result){	
            	$("#mainContent").empty();
            	$("#mainContent").append(result['responseText']);
            }
        });
}

function deleteExperiment(id){
	$.ajax({
        type: "POST",
        url: "/Physics/teacher/deleteExperiment?expId="+id,
        data: {},
        dataType: "json",
        complete: function(result){	
        	alert_dialog("删除成功");
        	$("#onExperiment_tb").trigger("reloadGrid");
        }
    });
}
</script>
</html>