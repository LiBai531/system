function setSearchSelectCol(collectionCols,id){
	
	$.each(collectionCols,function(i,item){
		 var values=$("#"+id+"_"+item).val();   
		 if(values==''){
			 $("#"+id+"_"+item).val('点击进行选择');
		 }
		 $("#"+id+"_"+item).click(function(){
			  $("#searchSelect_dialog_div").dialog(
			  {
					title:"查询选择",
				  	height:'410',
				  	width:'500',
				  	position:'center',
				  	modal:true,
				  	draggable:true,
				  	buttons:{ 
				  	  	  "确定":function(){
					  	  		if($("#searchSelect_dialog_div :radio:checked").val()=="true"){
					  	  			//alert($("#foreignUnit").val());//国外学校
					  	  		 $("#"+id+"_"+item).val($("#foreignUnit").val());
					  	  		}else{
						  	  		var sId=$("#unitSelect_tab").jqGrid('getGridParam','selrow');
						  	  		var rowdata = $("#unitSelect_tab").jqGrid('getRowData',sId);
					  	  			//alert("UnitId="+rowdata.id+"   UnitName="+rowdata.name);//国内学校
						  	  		$("#"+id+"_"+item).val(rowdata.name);
					  	  		}
					  	  		$("#searchSelect_dialog_div").dialog("destroy");
				  	  	   },
			  	  		   "关闭":function(){ 
			  	  			   $("#searchSelect_dialog_div").dialog("destroy");
			  	  		   }
					},
					close:function(){
						$(this).dialog("destroy");
					}
			  });
			  $("#searchSelectBtn").button();
			  $("#unitSelect_tab").jqGrid({
					url:'/DSEP/Collect/toCollect/getUnitData?condition='+$("#searchCondition").val(),
					datatype:"json",
					mtype:'GET',
					colNames:['学校编号','学校'],
					colModel:[
			 		 	{name:'id',index:'id',align:"center"},
			 		 	{name:'name',index:'name',align:"center"}
				     ],
				    cmTemplate: {sortable:false},          
					height : '100%',
					autowidth : true,
					width:'100%',
					pager : '#dv_unitSelect_tab_pager',
					pgbuttons : true,
					/* shrinkToFit : false, */
					rowNum : 5,
					rownumbers:true,
					viewrecords : true,
					gridComplete:function(){//给表格添加编辑列和删除列	
					},
					loadonce:true,
					jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
						root : 'rows', //包含实际数据的数组  
						page : 'pageIndex', //当前页  
						total : 'totalPage',//总页数  
						records : 'totalCount', //查询出的记录数  
						repeatitems : false
					},
					beforeSelectRow : function(id){
						if($("#searchSelect_dialog_div :radio:checked").val()=="true"){
							alert_dialog("国外高校不再此范围内，请在下方输入！");
							$("#foreignUnit").focus();
							return false;
						}
						$(this).setSelection(id,true);
					}
				}).navGrid('#dv_unitSelect_tab_pager',{edit:false,add:false,del:false});	  
		 }); 
	});
}
$("#searchSelect_dialog_div :radio").change(function(){
	if($(this).val()=='true'){
		$("#foreignUnit_div").show();
		$("#foreignUnit").focus();
		 $("#searchSelect_dialog_div .ui-state-highlight").removeClass("ui-state-highlight");
	}else{
		$("#foreignUnit_div").hide();
	}
});
$("#searchSelect").click(function(){
	
	 var url="/DSEP/Collect/toCollect/getUnitData?condition="+encodeURI(encodeURI($("#searchCondition").val()));
	// alert(urlReload);
	  $("#unitSelect_tab").jqGrid('setGridParam', { datatype:'json',page:1,url: url});
	  $("#unitSelect_tab").trigger("reloadGrid");
});


