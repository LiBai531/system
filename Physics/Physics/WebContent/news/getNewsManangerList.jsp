<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<button type="button" class="btn btn-info" style="width:140px" onclick="addNews()">添加新闻</button>
	<div class="selectbar layout_holder">
			<table id="experiment_tb"></table>
			<div id="pager_experiment_tb"></div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#experiment_tb").jqGrid({
		url:"/Physics/news/getNewsManagerList",
		datatype: "json",
		mtype:"post",
		colNames:['ID','标题','发布人','日期','操作'],
		colModel:[
			{name:'id',index:'joinRule',align:"center", width:50,editable:true,hidden:true},
			{name:'content.head',align:"center", width:50,editable:true,sortable:false},
			{name:'user.name',align:"center", width:50,editable:true,sortable:false},
			{name:'calander',align:"center", width:30,editable:true,sortable:false},
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
				var modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='editNews(\""+datas[i]['id']+"\")'>编辑查看    </a>"; 
				var delete_ = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='deleteNews(\""+datas[i]['id']+"\")'>|  删除</a>"; 
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper :modify+delete_});
					
			};
		},
		onSelectRow : function(id) {
		
		},
		caption: "实验新闻管理"
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});	
	
	
});


function editNews(id){
	$.get('/Physics/news/editNews?id='+id, function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
	  		    title:"编辑新闻",
	  		    height:'400',
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
	 	  		             url: "/Physics/news/saveNews",
	 	  		             data: {id:id,newsHead:$('#newsHead').val(),newsContent:$('#newsContent').val()},
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

function deleteNews(id){
	
		  $('#dialog').empty(); 
		  $('#dialog').append( "确认删除此项新闻？");
	 	  $('#dialog').dialog({
	  		    title:"编辑新闻", 
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
	 	  		             url: "/Physics/news/deleteNews",
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

function addNews(){
	$.get('/Physics/news/addNews', function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
	  		    title:"添加新闻",
	  		    height:'700',
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
	 	  		             url: "/Physics/news/saveAddNews",
	 	  		             data: {newsHead:$('#newsHead').val(),newsContent:$('#newsContent').val()},
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
</script>
</html>