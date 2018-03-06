<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>

.ztree * {font-size: 10pt;font-family:"Microsoft Yahei",Verdana,SimSun,"Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue",Arial}

.ztree li {line-height:30px;}
.ztree li a {width:200px;height:30px;padding-top: 0px;}
.ztree li ul{ margin:0; padding:0;}
.ztree li a:hover {text-decoration:none; background-color: #E7E7E7;}
.ztree li a span.button.switch {visibility:hidden}
.ztree.showIcon li a span.button.switch {visibility:visible}
.ztree li a.curSelectedNode {background-color:#D4D4D4;border:0;height:30px;}
.ztree li span {line-height:30px;}
.ztree li span.button {margin-top: -7px;}
.ztree li span.button.switch {width: 16px;height: 16px;}

.ztree li a.level0 span {font-size: 120%;font-weight: bold;}
.ztree li span.button {background-image:url("/Physics/css/zTreeStyle/img/left_menuForOutLook.png"); *background-image:url("/Physics/css/zTreeStyle/img/left_menuForOutLook.gif")}
.ztree li span.button.switch.level0 {width: 20px; height:20px}
.ztree li span.button.switch.level1 {width: 20px; height:20px}
.ztree li span.button.noline_open {background-position: 0 0;}
.ztree li span.button.noline_close {background-position: -18px 0;}
.ztree li span.button.noline_open.level0 {background-position: 0 -18px;}
.ztree li span.button.noline_close.level0 {background-position: -18px -18px;}
.level0 {background-color:#DFEFFC;}
.level1 {background-color:white;}
.level1 a span {font-weight:bold;color:#116DAE;}
.level2 {background-color:white;}
.level2 a span {color:grey;}

.level0 li.level1{margin-left:50px;}
.level1 li.level2{margin-left:30px;}
.level2 li.level3{margin-left:20px;}
.level2 a{border:1px solid silver;border-width:0 0 1px;  }

.hidden{display:none;}
.btn{width:18%;margin-left:10px;margin-right:10px;}

.my_table_td{width:33%;}
.my_table_tr{height:30px;}
.discTitle{
	color:#116DAE;
	font-weight:bold;
	font-size:15px;
}
.my_first_table_head{}
</style>
</head>
<body>

	<div style="border-bottom: rgb(78, 78, 88) 2px double ;margin-bottom:20px;">
		<h1 style="  color: rgb(44, 153, 226);">用户菜单管理：</h1>
	</div>
	<div class="sim_check_first_block" style="margin-left:15%;margin-top:0;width:24%;">
		<button onclick="getMenu(1)" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" >
			<span class="ui-button-text">查看学生菜单</span>
		</button>
		<button onclick="getMenu(2)" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" >
			<span class="ui-button-text">查看教师菜单</span>
		</button>
		
	</div>

	<div class="sim_check_second_block" style="margin-left:45%;width:35%;">
		<button onclick="saveMenu()" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
		 style="width:67%;" >
			<span class="ui-button-text">保存</span>
		</button>
		<div id="TreeStudent" class="zTreeDemoBackground left">
			<ul id="rightTree" class="ztree"></ul>
		</div>
		
	</div>
</body>
<script type="text/javascript">
var setting = {
		check: {
			enable: true,
			chkDisabledInherit: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
var zNodes = ${ztreeNode};

$(document).ready(function(){
	$.fn.zTree.init($("#rightTree"), setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj("rightTree");
	zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
});


function getMenu(type){
	$.ajax({
        type: "POST",
        url: "/Physics/admin/userMenuManager?type="+type,
        data: {},
        dataType: "json",
        complete: function(result){	
        	$("#mainContent").empty();
        	$("#mainContent").append(result['responseText']);
        }
    });
}

function saveMenu(){
	var treeObj = $.fn.zTree.getZTreeObj("rightTree");
	var nodes=treeObj.getCheckedNodes(true);
	if( nodes.length == 0){
		alert_dialog("菜单不能为空！ ");
		return false;
	}
	
	var tArray = new Array();
	for(var i=0;i<nodes.length;i++){
        tArray.push(nodes[i].id);
    }
	
	$.ajax({
        type: "POST",
        url: "/Physics/admin/saveMenuManager/submit/"+tArray+"?userType=${userType}",
        data: {},
        dataType: "json",
        complete: function(result){	
        	
        }
    });
	
}
</script>
</html>