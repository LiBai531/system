function setOnlyReadCol(onlyReadCols,id){
	
	$.each(onlyReadCols,function(i,item){
		$("input[id$='"+id+"_"+item+"']").prop('disabled',true);
	});
}
function setOnlyReadNum(onlyReadCols,id){
	var onlyReadRows = new Array();
	if(controlTypeRules!=null){
		$.each(controlTypeRules,function(type,values){
			if(type=='OR'){
				onlyReadRows = values;
			}
		});
	}
	if(onlyReadRows.length==0){
		$.each(onlyReadCols,function(i,item){
			$("input[id$='"+id+"_"+item+"']").prop('disabled',true);
		});
	}else{
		var orCols = new Array();
		$.each(onlyReadRows,function(i,item){
			if((item.split('#')[1]==id+'')){
				orCols.push(item.split('#')[0]);
			}
		});
		$.each(orCols,function(i,item){
			$("input[id$='"+id+"_"+item+"']").prop('disabled',true);
		});
	}
}

