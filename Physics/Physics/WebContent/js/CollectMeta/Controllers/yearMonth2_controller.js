function setYearMonth2Col(collectionYearMonth2Cols,id){
	$.each(collectionYearMonth2Cols,function(i,item){
		   if($.trim(id)==""){
			   jQuery("#"+item,"#collect_form_tb").click(function(event){
				   initYearMonth2Dialog($(this),event.pageX,event.pageY);
			   });
		   }
		   var collect_val = jQuery("#"+id+"_"+item,"#jqGrid_collect_tb").val();
		   if(collect_val==""){
			   jQuery("#"+id+"_"+item,"#jqGrid_collect_tb").val("点击添加");
		   }
		   jQuery("#"+id+"_"+item,"#jqGrid_collect_tb").click(function(event){
			   initYearMonth2Dialog($(this),event.pageX,event.pageY);
		   });
		   var batch_val = jQuery("#"+id+"_"+item,"#collect_batch_add_tb").val();
		   if(batch_val==""){
			   jQuery("#"+id+"_"+item,"#collect_batch_add_tb").val("点击添加");
		   }
		   jQuery("#"+id+"_"+item,"#collect_batch_add_tb").click(function(event){
			   initYearMonth2Dialog($(this),event.pageX,event.pageY);
		   });
		   var import_val = jQuery("#"+id+"_"+item,"#collect_import_excel_tb").val();
		   if(import_val==""){
			   jQuery("#"+id+"_"+item,"#collect_import_excel_tb").val("点击添加");
		   }
		   jQuery("#"+id+"_"+item,"#collect_import_excel_tb").click(function(event){
			   initYearMonth2Dialog($(this),event.pageX,event.pageY);
		   });
	  });
}