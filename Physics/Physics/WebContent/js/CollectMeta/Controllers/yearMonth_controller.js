function setYearMonthCol(collectionDateCols,id){
	$.each(collectionDateCols,function(i,item){
		   if($.trim(id)==""){
			   jQuery("#"+item,"#collect_form_tb").click(function(event){
				   initYMDialog($(this),event.pageX,event.pageY);
			   });
		   }
		   jQuery("#"+id+"_"+item,"#jqGrid_collect_tb").click(function(event){
			   initYMDialog($(this),event.pageX,event.pageY);
		   });
		  /* jQuery("#"+id+"_"+item,"#jqGrid_collect_tb").focus(function(event){
			   initYMDialog($(this),$(this).pageX,$(this).pageY);
		   });*/
		   jQuery("#"+id+"_"+item,"#collect_batch_add_tb").click(function(event){
			   initYMDialog($(this),event.pageX,event.pageY);
		   });
		   jQuery("#"+id+"_"+item,"#collect_import_excel_tb").click(function(event){
			   initYMDialog($(this),event.pageX,event.pageY);
		   });
		   jQuery("#"+id+"_"+item,"#collect_paper_add_tb").click(function(event){
			   initYMDialog($(this),event.pageX,event.pageY);
		   });
		   jQuery("#"+id+"_"+item,"#dup_modify_tb").click(function(event){
			   initYMDialog($(this),event.pageX,event.pageY);
		   });
	  });
}