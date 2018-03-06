<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<button type="button" class="btn btn-info" style="width:140px" onclick="editExperimentInfo_()">添加基础实验信息</button>
	<div class="selectbar layout_holder">
			<table id="experiment_tb"></table>
			<div id="pager_experiment_tb"></div> 
	</div>
</body>
<link  rel="stylesheet" href="/Physics/css/jqgridmine.css">
<script type="text/javascript">
$(document).ready(function(){
	$("#experiment_tb").jqGrid({
		url:"/Physics/admin/getExperimentInfoList",
		datatype: "json",
		mtype:"post",
		colNames:['id','实验名称','实验介绍','实验地点','可选人数','操作'],
		colModel:[
			{name:'id',index:'joinRule',align:"center", width:50,editable:true,hidden:true},
			{name:'name',align:"center", width:50,editable:true,sortable:false},
			{name:'info',align:"left",width:120,edittype:"textarea",editable:true,sortable:false},
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
				var modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='editExperimentInfo(\""+ids[i]+"\")'>编辑    </a>"; 
				var delete_ = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='deleteExperimentInfo(\""+ids[i]+"\")'>|  删除</a>"; 
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper :modify+delete_});
					
			};
		},
		onSelectRow : function(id) {
		
		},
		caption: "实验基础信息列表"
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});	
	
	
});

function editExperimentInfo(id){
	$.get('/Physics/admin/editExpermentInfo?id='+id, function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
 	  		    title:"修改实验",
 	  		    height:'450',
 	  			width:'800',
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
	 	  		             url: "/Physics/admin/saveExperimentInfo",
	 	  		             data: {expInfoId:id, expName:$('#expName').val(),expAddress:$('#expAddress').val(),expLimit:$('#expLimit').val(),expInfo:$('#expInfo').val()},
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
 	 	}, 'html');
		return true; 
}

function editExperimentInfo_(){
	$.get('/Physics/admin/editExpermentInfo?id=123456', function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
 	  		    title:"修改实验",
 	  		    height:'450',
 	  			width:'800',
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
	 	  		             url: "/Physics/admin/saveExperimentInfo_",
	 	  		             data: {expName:$('#expName').val(),expAddress:$('#expAddress').val(),expLimit:$('#expLimit').val(),expInfo:$('#expInfo').val()},
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
 	 	}, 'html');
		return true; 
}

function deleteExperimentInfo(id){
	  $('#dialog').empty(); 
	  $('#dialog').append("确定删除此项基础信息？");
	  $('#dialog').dialog({
 		    title:"删除",
 		    height:'300',
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
	  		             url: "/Physics/admin/deleteExperimentInfo",
	  		             data: {expInfoId:id},
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
	return true; 
}
</script>
</html>