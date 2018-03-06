<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="selectbar layout_holder">
			<table id="experiment_tb"></table>
			<div id="pager_experiment_tb"></div>
	</div>
</body>

<script type="text/javascript">
var experimentStudentId="";
$(document).ready(function(){
	$("#experiment_tb").jqGrid({
		url:"/Physics/student/getStudentExperiment",
		datatype: "json",
		mtype:"post",
		colNames:['id','实验名称','实验介绍','老师','实验日期','实验课时','实验室','上传作业','退选实验'],
		colModel:[
			{name:'experimentStudent.id',index:'joinRule',align:"center", width:30,editable:true,hidden:true},
			{name:'experimentInfo.name',align:"center", width:30,editable:true,sortable:false},
			{name:'experimentInfo.info',align:"center", width:130,editable:true,sortable:false},
			{name:'teacher.name',align:"center", width:20,editable:true,sortable:false},
			{name:'experiment.calendar',align:"center", width:30,editable:true,sortable:false},
			{name:'time',align:"center", width:30,editable:true,sortable:false},
			{name:'experimentInfo.address',align:"center", width:40,editable:true,sortable:false},
			{name:'homework',align:"center", width:30,editable:true,sortable:false},
			{name:'oper',align:"center", width:30,editable:true,sortable:false}
			
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
				console.log(ids[i]);
				
				var modify = "<a class='' style='color:#78BFE9;font-weight:bold;' onclick='deleteExperiment(\""+datas[i]['experimentStudent.id']+"\")'>退选实验</a>";
				var homework = "<a class='' style='color:#78BFE9;font-weight:bold;' onclick='uploadHomework(\""+datas[i]['experimentStudent.id']+"\")'>上传报告</a>";
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper :modify});
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{homework :homework});
					 
			};
		},
		onSelectRow : function(id) {
		
		},
		caption: "在进行实验信息列表"
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});	
	
	
});

function deleteExperiment(id){
	$('#dialog').empty(); 
	$('#dialog').append("确认退选此实验？");
	$('#dialog').dialog({
		    title:"退选实验", 
		    height:'200',
			width:'300',
			position:'center',
			modal:true,
			draggable:true,
		    hide:'fade',
			show:'fade',
		    autoOpen:true,
	  		buttons:{  
		    	"确定":function(){ 
		    		$("#dialog").dialog("close");
		    		$.ajax({
		    			 type: "POST",
	  		             url: "/Physics/student/deleteExperiment?id="+id,
	  		             data: {id:id},
	  		             dataType: "json",
	  		             complete: function(result){	
	  		            	jQuery("#experiment_tb").trigger("reloadGrid");
	  		             }
		    		});
	            },
	            "关闭":function(){
	            	$("#dialog").dialog("close");
	            }
		    }
	  });
}

function uploadHomework(id){
	experimentStudentId = id;
	$.get('/Physics/upload/uploadPage', function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
 	  		    title:"上传作业",
 	  		    height:'300',
 	  			width:'300',
 	  			position:'center',
 	  			modal:true,
 	  			draggable:true,
 	  		    hide:'fade',
 	  			show:'fade',
 	  		    autoOpen:true,
	   	  		buttons:{  
 	  		    	/* "确定":function(){ 
 	  		    		$("#dialog").dialog("close");
 	  		    		$.ajax({
 	  		    			 type: "POST",
	 	  		             url: "/Physics/experiment/saveExperiment",
	 	  		             data: {expInfoId:id, expTime:$('#expTime').val(),startTime:$('#startTime').val(),endTime:$('#endTime').val()},
	 	  		             dataType: "json",
	 	  		             complete: function(result){	
	 	  		             	
	 	  		             }
 	  		    		});
 	  	            }, */
 	 	            "关闭":function(){
 	 	            	$("#dialog").dialog("destroy");
 	 	            }
 	  		    }
	 	  }); 
 	 	}, 'html');
		return false; 
}
</script>
</html>