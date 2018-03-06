function setSelectLinkSelectCol(collectionCols,id){
	
	$.each(collectionCols,function(i,item){
		 var values=$("#"+id+"_"+item).val();   
		 if(values==''){
			 $("#"+id+"_"+item).val('点击进行选择');
		 }

		 
		 $("#"+id+"_"+item).click(function(){
			 
			 var level1=((jqGridConfig.selectlinkSelectDicValues)[item]).LEVEL1; 
			 var level2=((jqGridConfig.selectlinkSelectDicValues)[item]).LEVEL2;
			 level2_content=((jqGridConfig.selectlinkSelectDicValues)[item]).LEVEL2_CONTENT;
			 console.log(level1,level2,level2_content);
			 var content1="";
			 var content2="";
			 var content3="";
			 var key1="";
			 var count=0;
			 for(var key in level1){
					content1+="<option> "+level1[key]+" </option>";	
			 }
			 content1+="<option> "+level2+" </option>";
			 
			for(var key in level2_content){
				if(count==0) key1=key;
				count++;
				content2+="<option> "+key+" </option>";
			}
			for(var key in level2_content[key1]){
				content3+="<option> "+(level2_content[key1])[key]+" </option>";
			}
			$("#sls_level1").html(content1);
			$("#sls_level2").html(content2);
			$("#sls_level2_content").html(content3);
			$("#sls_value").val($("#sls_level1").val());
			
			
			$("#selectLinkSelect_div").dialog(
			  {
					title:"查询选择",
				  	height:'300',
				  	width:'380',
				  	position:'center',
				  	modal:true,
				  	draggable:true,
				  	buttons:{ 
				  	  	  "确定":function(){
				  	  		    $("#"+id+"_"+item).val( $("#sls_value").val());
				  	  		   
					  	  		$("#selectLinkSelect_div").dialog("destroy");
				  	  	   },
			  	  		   "关闭":function(){ 
			  	  			   $("#selectLinkSelect_div").dialog("destroy");
			  	  		   }
					},
					close:function(){
						$("selectLinkSelect_div").dialog("destroy");
					}
			  });
			 
		 }); 
		 $("#sls_level1").change(function(){
			 var level2=((jqGridConfig.selectlinkSelectDicValues)[item]).LEVEL2;
			 if ($("#sls_level1").val()==level2){
				 $("#addtion_content").show();
				 $("#sls_value").val($("#sls_level2_content").val());
			 }else{
				 $("#addtion_content").hide();
				 $("#sls_value").val($("#sls_level1").val());
			 }
		 });
		 
		 $("#sls_level2").change(function(){
				
				var level2_content=((jqGridConfig.selectlinkSelectDicValues)[item]).LEVEL2_CONTENT;
				
				var tempValues=level2_content[$("#sls_level2").val()];
				var content="";
				for(var key in tempValues){
					content+="<option> "+tempValues[key]+" </option>";
				}
				$("#sls_level2_content").html(content);
				$("#sls_value").val($("#sls_level2_content").val());
		 });
		 $("#sls_level2_content").change(function(){
			 $("#sls_value").val($("#sls_level2_content").val());
		 });
	});
	
	
}


