<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.btn-group, .btn-group-vertical{
	    position: relative;
	    display: inline-block;
	    vertical-align: middle;
	}
	
	*, *:before, *:after { 
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.btn-group>.btn:first-child:not(:last-child):not(.dropdown-toggle) {
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
}
.btn-group>.btn:first-child {
    margin-left: 0;
}
.btn-group>.btn, .btn-group-vertical>.btn {
    position: relative;
    float: left;
}
.btn-default {
    color: #333;
    background-color: #fff;
    border-color: #ccc;
}
input, button, select, textarea {
    font-family: inherit;
    font-size: inherit;
    line-height: inherit;
}
button, html input[type="button"], input[type="reset"], input[type="submit"] {
    cursor: pointer;
    -webkit-appearance: button;
}
button, select {
    text-transform: none;
}
button, input {
    line-height: normal;
}
button, input, select, textarea {
    margin: 0;
    font-family: inherit;
    font-size: 100%;
}
*, *:before, *:after {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
user agent stylesheetinput[type="button" i], input[type="submit" i], input[type="reset" i], input[type="file" i]::-webkit-file-upload-button, button {
    padding: 1px 6px;
}
user agent stylesheetinput[type="button" i], input[type="submit" i], input[type="reset" i], input[type="file" i]::-webkit-file-upload-button, button {
    align-items: flex-start;
    text-align: center;
    cursor: default;
    color: buttontext;
    padding: 2px 6px 3px;
    border: 2px outset buttonface;
    border-image-source: initial;
    border-image-slice: initial;
    border-image-width: initial;
    border-image-outset: initial;
    border-image-repeat: initial;
    background-color: buttonface;
    box-sizing: border-box;
}
user agent stylesheetinput, textarea, keygen, select, button {
    margin: 0em;
    font: 13.3333px Arial;
    text-rendering: auto;
    color: initial;
    letter-spacing: normal;
    word-spacing: normal;
    text-transform: none;
    text-indent: 0px;
    text-shadow: none;
    display: inline-block;
    text-align: start;
}
user agent stylesheetinput, textarea, keygen, select, button, meter, progress {
    -webkit-writing-mode: horizontal-tb;
}
user agent stylesheetbutton {
    -webkit-appearance: button;
}
</style>
</head>
<body>
	<div style="height:40px;">
		<div style="float:left">
			<div class="btn-group">
			  <button type="button" class="btn btn-default" style="margin-right:0;margin-left:0;" onclick="getTypeUsers(0)">全部</button>
			  <button type="button" class="btn btn-default" style="margin-right:0;margin-left:0;" onclick="getTypeUsers(1)">学生</button>
			  <button type="button" class="btn btn-default" style="margin-left:0;margin-right:0;" onclick="getTypeUsers(2)">教师</button>
			  <button type="button" class="btn btn-default" style="margin-left:0;" onclick="getTypeUsers(3)">管理员</button>
			</div>
		</div>
		<div style="float:right">
			<a id="exportUser" class="btn btn-info" style="width:140px" href="#"  onclick="exportUser()">导出用户</a>
			<button type="button" class="btn btn-info" style="width:140px" onclick="addUser()">添加用户</button>
			<button type="button" class="btn btn-info" style="width:140px" onclick="addUser()">导入用户</button>
		</div>
	</div>
	<div class="selectbar layout_holder">
			<table id="experiment_tb"></table>
			<div id="pager_experiment_tb"></div>
	</div>
</body>
<script type="text/javascript">
var userType = "0";
$(document).ready(function(){
	$("#experiment_tb").jqGrid({
		url:"/Physics/rbac/getUserManagerList",
		datatype: "json",
		mtype:"post",
		colNames:['ID','用户状态','初始密码','用户名','用户登陆名','用户类型','最近登陆时间','最近登陆IP','操作'],
		colModel:[
			{name:'id',index:'joinRule',align:"center", width:50,editable:true,hidden:true},
			{name:'state',align:"center",formatter:"select",edittype:"select",editoptions:{value:{"2":'正常用户',"1":'初始用户'}}, width:35,editable:true,sortable:false},
			{name:'initPassword',align:"center", width:35,editable:true,sortable:false},
			{name:'name',align:"center", width:30,editable:true,sortable:false},
			{name:'loginId',align:"center", width:30,editable:true,sortable:false},
			{name:'userType',align:"center", formatter:"select",edittype:"select",editoptions:{value:{"1":'学生',"2":'教师',"3":'管理员'}},width:30,editable:true,sortable:false},
			{name:'loginTime',align:"center", width:45,editable:true,sortable:false},
			{name:'loginIP',align:"center", width:45,editable:true,sortable:false},
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
				
				var modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='resetUser(\""+datas[i]['id']+"\")'>重置 </a>"; 
				var delete_ = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='deleteUser(\""+datas[i]['id']+"\")'>|  删除</a>"; 
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper :modify+delete_});
					
			};
		},
		onSelectRow : function(id) {
		
		},
		caption: "用户管理"
	}).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});	
	
	
});

function resetUser(id){
	 $('#dialog').empty(); 
	  $('#dialog').append( "重置后，用户将变为初始状态重新生成初始密码，并许使用初始密码登陆，确定重置？  ");
	  $('#dialog').dialog({
 		    title:"重置用户", 
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
	  		             url: "/Physics/rbac/resetUser",
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

function deleteUser(id){
		  $('#dialog').empty(); 
		  $('#dialog').append( "确认删除此项用户？");
	 	  $('#dialog').dialog({
	  		    title:"删除用户", 
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
	 	  		             url: "/Physics/rbac/deleteUser",
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

function addUser(){
	$.get('/Physics/rbac/addUserPage', function(data){
		  $('#dialog').empty(); 
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
	  		    title:"添加用户",
	  		    height:'300',
	  			width:'600',
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
	 	  		             url: "/Physics/rbac/addUser",
	 	  		             data: {userName:$('#userName').val(),loginId:$('#loginId').val(),userType:$("input[name='userType']:checked").val() },
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

function getTypeUsers(type){
	userType = type;
	console.log(type);
		jQuery("#experiment_tb").setGridParam({
			url:"/Physics/admin/getTypeUsers?type="+type,
		}).trigger("reloadGrid");
}

function exportUser(){
	 var url='/Physics/export/expotUsers/'+ userType;
	 console.log(url);
	 document.getElementById("exportUser").href=url;
}
</script>
</html>