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
$(document).ready(function(){
	$("#experiment_tb").jqGrid({
		url:"/Physics/experiment/getExperimentInfoList",
		datatype: "json",
		mtype:"post",
		colNames:['id','实验名称','实验介绍','实验地点','可选人数','选择实验'],
		colModel:[
			{name:'id',index:'joinRule',align:"center", width:50,editable:true,hidden:true},
			{name:'name',align:"center", width:50,editable:true,sortable:false},
			{name:'info',align:"left",width:120,edittype:"textarea",editoptions:{rows:"4",cols:"15"},editable:true,sortable:false},
			{name:'address',align:"center", width:50,editable:true,sortable:false},
			{name:'personLimit',align:"center", width:30,editable:true,sortable:false},
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
				var modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='editExperiment(\""+ids[i]+"\")'>选择</a>"; 
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper :modify});
					
			};
		},
		onSelectRow : function(id) {
		
		},
		caption: "实验基础信息列表"
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});	
	
	
});

function editExperiment(id){
	$.get('/Physics/experiment/getExperimentById?id='+id, function(data){
		console.log(data);
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
 	  		    title:"发布实验",
 	  		    height:'600',
 	  			width:'500',
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
	 	  		             url: "/Physics/experiment/saveExperiment",
	 	  		             data: {expInfoId:id, expTime:$('#expTime').val(),startTime:$('#startTime').val(),endTime:$('#endTime').val()},
	 	  		             dataType: "json",
	 	  		             complete: function(result){	
	 	  		             	
	 	  		             }
 	  		    		});
 	  	            },
 	 	            "关闭":function(){
 	 	            	$("#dialog").dialog("close");
 	 	            }
 	  		    }
	 	  }); 
 	 	}, 'html');
		return false; 
}
</script>
</html>