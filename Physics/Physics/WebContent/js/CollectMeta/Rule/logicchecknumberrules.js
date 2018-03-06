/**
 *  逻辑检查——数字规则检验js函数
 *  @author lubin 
 */

/**
 * 检验非负整数,
 * 
 * @param value
 * @param colname
 * @returns {Array}
 * @author lubin
 */
function checkNumNonNegativeInt(value, colname) {// colname位待检测的列名，value为相应的值
	
	var regex = /^[0-9]\d*$/;
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value)){
			if(value.length>=2){
				var firtstChar=value.substring(0,1);
				if(firtstChar=='0'){
					return [ false, "格式错误，第一位不能为0。"];
				}
			}
			if(value.length>7){
				return [ false, "请输入合法的整数，建议小于9999999。"];
			}
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		}else{
			pass_validate = '0';
			return [ false, colname + ": 请输入非负整数。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}
function checkStringNotChs(value,colname){
	regex = /^[\u4e00-\u9fa5]/;//验证是否存在中文.*[\\u4e00-\\u9faf].*
	if(value.length == 0)
	{
		pass_validate = '0';// 错误的时候为0
		return [false,"此字段不允许为空"];
	}	
	else{
		if(regex.test(value)){
			pass_validate = '0';// 错误的时候为0
			return [false,colname+":请输入合理数据。"];
		}
		else{
			return [true,""];
		}
			
	}
}
function checkNumNonnegativeNum100(value,colname){
	var result = checkNumNonnegativeNum(value,colname);
	if(result[0]){
		if(parseInt(value)<=100){
			return [ true, ""];// 正确时的返回值，所有的检测函数都这么写
		}else{
			pass_validate = '0';
			return [ false, colname + "输入数值请小于100！" ];
		}
	}else{
		pass_validate = '0';
		return [false,colname+": 请输入非负小数。"];
	}
}
/**
 * 检查是否非负整数
 * @param value
 * @returns
 */
function checkNonNegativeInt(value) {// colname位待检测的列名，value为相应的值
	var regex = /^[0-9]\d*$/;
	if (value.length == 0) {
		pass_validate = '0';
		return false;
	} else {
		if (regex.test(value))
			return true;// 正确时的返回值，所有的检测函数都这么写
		else{
			return false;// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}

function TRULE(value, colname) {// colname位待检测的列名，value为相应的值
	var regex = /^\d+$/;
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, colname + " : 此字段不允许为空" ];
	} else {
		if (regex.test(value))
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		else {
			pass_validate = '0';// 错误的时候为0
			return [ false, colname + " : 请输入正整数或零" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
	}
}
/**
 * 检查正整数
 * 
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkNumPositiveInt(value, colname) {
	var regex = /^[1-9]\d*$/;
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value))
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		else{
			pass_validate = '0';
			return [ false, colname + ": 请输入正整数。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
	}
}
function checkNumPositiveIntOrNull(value,column){
	var regex = /^[1-9]\d*$/;
	if (value.length == 0) {
		return [true,""];
	} else {
		if (regex.test(value))
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		else{
			pass_validate = '0';
			return [ false, colname + ": 请输入正整数。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
	}
}
/**
 * 检查非负小数
 * 
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkNumNonnegativeNum(value, colname) {
	var regex = /^(0\.\d+|[1-9]\d*|[1-9]\d*\.\d+|0)$/;
	if (value.length == 0) {
		pass_validate = '0';// 错误的时候为0
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value)){
			/*if(parseInt(value)<100){
				return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
			}else{
				pass_validate = '0';
				return [ false, colname + "输入数值请小于100！" ];
			}*/
			return [true,""];
		}
		else{
			pass_validate = '0';// 错误的时候为0
			return [ false, colname + ": 请输入有效数据。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}
function checkNumInColumn(paramsAndValues,value, colname) {
	var regex = /^(0\.\d+|[1-9]\d*|[1-9]\d*\.\d+|0)$/;
	var colModels= $("#"+currentJqGridId).getGridParam("colModel");
	
	
	if (value.length == 0) {
		pass_validate = '0';// 错误的时候为0
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value)){
				var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
				if(parm1!='undefined'){
					var valueHigh = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
					var columnHigh;
					$.each(colModels,function(i,item){
						if(item.name==parm1){
							columnHigh = item.label;
						}
					});
					value = parseInt(value);
					valueHigh = parseInt(valueHigh);
					if(value>valueHigh)
					{
						return [ false, colname + "数值应小于等于:'"+columnHigh+"'字段的值!" ];
					}
				}
				return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
			
		}
		else{
			pass_validate = '0';// 错误的时候为0
			return [ false, colname + ": 请输入有效数据。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}

/**
 * 检查各列值相加为100
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
*/ 
function checkNumSumNum100(paramsAndValues,value, colname){
	var result = checkNumNonnegativeNum100(value,colname);
	if(!result[0]){
		return result;
	}else{
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var columns = parm1.split(";");
		var colValues=0;
		if(value.length==0){
			pass_validate = '0';// 错误的时候为0
			return [false,column+" : 字段不许为空！"];
		}else{
			$.each(columns,function(i,col){
				if($.trim(col)!=''){
					var colValue= $("input[id$='"+currentSaveRowId+"_"+col+"']").val();
					colValues+=parseInt(colValue);
				}
			});
			colValues+=parseInt(value);
			if(colValues==100){
				return [true,""];
			}else{
				pass_validate = '0';// 错误的时候为0
				return [false,"字段值之和不是100，请检查后重新填写！"];
			}
		}
	}
}

function checkNumAboveZoreNum(value, colname) {
	var regex = /^[1-9]\d*(\.\d+)?$/;
	if (value.length == 0) {
		pass_validate = '0';// 错误的时候为0
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value)){
			/*if(parseInt(value)<100){
				return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
			}else{
				pass_validate = '0';
				return [ false, colname + "输入数值请小于100！" ];
			}*/
			return [true,""];
		}
		else{
			pass_validate = '0';// 错误的时候为0
			return [ false, colname + ": 请输入有效数据。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}
/**
 * 一列的值大于等于另一列的值
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkNumMoreThanColumn(paramsAndValues,value, colname) {
	//console.log(paramsAndValues,value,colname);
	//alert(paramsAndValues);
	if (value.length == 0) {
		pass_validate = '0';// 错误的时候为0
		return [ false, "此字段不允许为空" ];
	}else{
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var valueCompare = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
		
		//提前检测格式是否正确
		var regex =/^[0-9]+([.]{1}[0-9]+){0,1}$/;//匹配整数或小数
		if(!(regex.test(value))){
			return [ false, colname + "：格式不正确"];
		}
		value = parseFloat(value);
		valueCompare = parseFloat(valueCompare);

		if(value<valueCompare)
		{
			return [ false, colname + "数值应大于等于："+valueCompare];
		}
		return [ true, "" ];
	}
}
/**
 * 当前列的值小于比较列的值
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkNumLessThanColumnNew(paramsAndValues,value, colname) {
	//console.log(paramsAndValues,value,colname);
	//alert(paramsAndValues);
	if (value.length == 0) {
		pass_validate = '0';// 错误的时候为0
		return [ false, "此字段不允许为空" ];
	}else{
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var valueCompare = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
		
		//提前检测格式是否正确
		var regex =/^[0-9]+([.]{1}[0-9]+){0,1}$/;//匹配整数或小数
		if(!(regex.test(value))){
			return [ false, colname + "：格式不正确"];
		}
		
		value = parseFloat(value);
		valueCompare = parseFloat(valueCompare);

		if(value>valueCompare)
		{
			return [ false, colname + "数值应小于等于："+valueCompare];
		}
		return [ true, "" ];
	}
}
/**
 * 检查当前列的值不小于其他多列的和
 * @author mantantan
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkMulColsLessCurrentCol(paramsAndValues,value, colname){
	//console.log(paramsAndValues,value,colname);
	if(!checkNonNegativeInt(value)) return  [ false, colname+":请输入非负整数！"];
	var parms = (paramsAndValues[0].split(",")[1]).split(";");// 获取第一个参数的值
	var sum=0;
	for(var i=0;i<parms.length;i++){
		var item = $("input[id$='"+currentSaveRowId+"_"+parms[i]+"']").val();
		if(!checkNonNegativeInt(item)) return [ false, "请输入非负整数！"];
		sum+=parseInt(item);
	}
	var currentValue=parseInt(value);
	
	if(sum>currentValue){
		return [ false, colname+"：应不小于"+sum+"!"];
	}
	return [ true, "" ];
}

/**
 * 检查当前列的值不大于其他多列的和
 * @author mantantan
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkMulColsMoreCurrentCol(paramsAndValues,value, colname){
	//console.log(paramsAndValues,value,colname);
	if(!checkNonNegativeInt(value)) return  [ false, colname+":请输入非负整数！"];
	var parms = (paramsAndValues[0].split(",")[1]).split(";");// 获取第一个参数的值
	var sum=0;
	for(var i=0;i<parms.length;i++){
		var item = $("input[id$='"+currentSaveRowId+"_"+parms[i]+"']").val();
		if(!checkNonNegativeInt(item)) return [ false, "请输入非负整数！"];
		sum+=parseInt(item);
	}
	var currentValue=parseInt(value);
	
	if(currentValue>sum){
		return [ false, colname+"：应不大于"+sum+"!"];
	}
	return [ true, "" ];
}

/**
 * 检查多行的特定列的值不大于指定行的值
 * @author mantantan
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkMulRowsLessOneRow(paramsAndValues,value, colname){
	//console.log(paramsAndValues,value,colname);
	if(!checkNonNegativeInt(value)) return  [ false, colname+":请输入非负整数！"];
	var rowNums = (paramsAndValues[0].split(",")[1]).split(";");// 获取第一个参数的值,并处理得到参数行号.第一个参数存相加的行号
	var calColName=paramsAndValues[1].split(",")[1];//获取要计算的列名
	var sumRowNum=paramsAndValues[2].split(",")[1];//获取总计行的行号
	var sum=0;
	//console.log('--------------',rowNums);
	
	for(var i=0;i<rowNums.length;i++){
		if(currentSaveRowId==(i+1)) continue;
		var rowData=$("#jqGrid_collect_initJq").jqGrid('getRowData',rowNums[i]); 
		sum+=parseInt(rowData[calColName]);
	}
	sum+=parseInt(value);
	var total=parseInt(($("#jqGrid_collect_initJq").jqGrid('getRowData',sumRowNum))[calColName]);
	
	if(total<sum){
		return [ false, colname+"：合计应不大于" +total+"!"];
	}
	
	return [ true, "" ];
}