/*对采集项进行检查*/
function checkPCTSumEquals100(paramsAndValues,entityName,records){
	//alert('checkPCTSumEquals100');
	return true;
}
function entityCheckPCTSum(paramsAndValues,entityName,records){
	var parm1 = paramsAndValues[0].split(",")[1];//获取第一个参数
	var result=true;
	if(records>=5){
		result = false;
	}
	if(!result){
		alert_dialog('学缘结构限填5项！');
	}
	return result;
}
function checkentityLimitData(paramsAndValues,entityName,records){
	//alert('checkEntityLimitData');
	var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值(门类：限制条件)
	var countLimits = parm1.split(";");
	var result = true;
	var maxCount = parseInt((countLimits[0]).split(":")[1]);
	//console.log("categoryId====",maxCount);
	
	$.each(countLimits,function(i,limit){
		if(limit.split(":")[0]==categoryId){
			maxCount=limit.split(":")[1];
			//console.log("categoryId====",categoryId);
			if(records>=maxCount){
				result = false;
			}
		}
	});
	if(records>=maxCount){
		result = false;
	}
	if(!result)
		alert_dialog("该项采集成果不得超过"+maxCount+"条！");
	return result;
}
function checkEntityRecordsLimitation(paramsAndValues,entityName,records){
	var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数（比较类型）
	var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值（门类:记录数）
	var compareType = parm1;//LE/L/GE/G
	var result = true;
	var categoryAndCounts = parm2.split(";");
	var maxCount = -1;
	$.each(categoryAndCounts,function(i,limit){
		if(limit.split(":")[0]==categoryId){
			maxCount=limit.split(":")[1];
		}
	});
	if(maxCount==-1){
		$.each(categoryAndCounts,function(i,limit){
			if(limit.split(":")[0]=='#'){
				maxCount=limit.split(":")[1];
			}
		});
	}
	var message = "该项采集成果必须";
    if(parm1=='L'){
    	if(records>=maxCount){
    		result = false;
    		message+="小于";
    	}
    }else if(parm1=='LE'){
    	if(records>maxCount){
    		result = false;
    		message+="小于等于";
    	}
    }else if(parm1=='G'){
    	if(records<=maxCount){
    		result = false;
    		message +="大于";
    	}
    }else if(parm1=='GE'){
    	if(records<maxCount){
    		result = false;
    		message +="大于等于";
    	}
    }
	if(!result)
		alert_dialog(message+maxCount+"条！");
	return result;
	
}
