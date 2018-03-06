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
		url:"/Physics/teacher/getEndingExperiment",
		datatype:"json",
		colNames:['id','实验名称','实验日期','实验课时','选择实验人数','查看'],
		colModel:[
		          {name:'experiment.id',index:'experiment.calendar',align:'center',width:50,editable:false,hidden:true},
		          {name:'experimentInfo.name',index:'experiment.calendar',align:'center',width:50,editable:false},
		          {name:'experiment.calendar',index:'experiment.calendar',align:'center',width:50,editable:false},
		          {name:'time',index:'experiment.calendar',align:'center',width:50,editable:false},
		          {name:'selectPersonNum',index:'experiment.calendar',align:'center',width:50,editable:false},
		          {name:'oper',index:'experiment.calendar',align:'center',width:50,editable:false}
		          ],
		rownumbers:true,
		height:"100%",
		autowidth:true,
		pager:"#pager_experiment_tb",
		rowNum:20,
		rowList:[20,30,40],
		sortorder: "asc",
		sortname:'',
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
			
			for( var i = 0; i < ids.length ; i++ ){
				var modify = "<a class='' style='color:#78BFE9;font-weight:bold;' href='#' onclick='getExperimentInfo(\""+datas[i]['experiment.id']+"\")'>查看</a>";
				jQuery("#experiment_tb").jqGrid('setRowData',ids[i],{oper :modify});
			}
        },
        onSelectRow : function(id) {
		},
		caption: "实验学生列表"
	 }).navGrid('#pager_experiment_tb',{edit:false,add:false,del:false});
 });
 
 
 function getExperimentInfo(id){
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
</script>
</html>