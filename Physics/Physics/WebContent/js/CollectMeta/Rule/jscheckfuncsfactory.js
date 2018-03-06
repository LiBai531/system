//工程函数，调用属性检查函数
function jsCheckFuncsFactory(value, colname) {
	//console.log(colname);
	var paramsAndValues=new Array();
	$.each(jsCheckfunParamsDic,function(i,item){
		if(i==colname){
			paramsAndValues = item;
		}
	 });
	var funcName = jsCheckFunNameDic[colname];  //保存后台传过来的数组名
	//console.log('函数名称：'+funcName+" value : "+value+" colname : "+colname);
	if(paramsAndValues.length==0){
		return eval(funcName+"(value,colname)");
	}else{
		return eval(funcName+"(paramsAndValues,value,colname)");
	}
	
}
//工厂函数，调用实体检查函数
function jsCheckFunFactory2(entityName,records){
	//console.log(entityName);
	var funcName = jsCheckFunNameDic[entityName];
	//console.log('entityFunc: '+funcName);
	if(funcName!=""&&typeof(funcName) != "undefined"){
		var paramsAndValues = new Array();
		$.each(jsCheckfunParamsDic,function(i,item){
			if(i==entityName){
				paramsAndValues = item;
			}
		});
		if(paramsAndValues.length==0){
			return eval(funcName+"(entityName,records)");
		}else{
			return eval(funcName+"(paramsAndValues,entityName,records)");
		}
	}else{
		return true;
	}
}
/*
 * 在线编辑框用户前端检查字数
 */
function jsCheckFunFactory3(entityName,currentContent){
	var funcName = jsCheckFunNameDic[entityName];
	//console.log('entityFunc: '+funcName);
	if(funcName!=""&&typeof(funcName)!="undefined"){
		var paramsAndValues = new Array();
		$.each(jsCheckfunParamsDic,function(i,item){
			if(i==entityName){
				paramsAndValues = item;
			}
		});
		if(paramsAndValues.length==0){
			return eval(funcName+"(entityName,records)");
		}else{
			return eval(funcName+"(paramsAndValues,currentContent,entityName)");
		}
	}else{
		return true;
	}
	
}