/**
 *  逻辑检查——字符串规则检验js函数
 *  @author lubin 
 */ 
/**
 * 检查字符串是否包含中文
 * @param value
 * @param colname
 * @returns {Array}
 * checkStringNotChinese
 */
/*function checkStringNotChs(value,colname){
	regex = /^[A-Za-z0-9.\\\/-]+$/;
	if(value.length == 0)
	{
		pass_validate = '0';// 错误的时候为0
		return [false,"此字段不允许为空"];
	}	
	else{
		if(regex.test(value))
			return [true,""];
		else{
			pass_validate = '0';// 错误的时候为0
			return [false,colname+":请输入合理数据。"];
		}
			
	}
}*/
/**
 * 检查字符串是否为空
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkStringNotNull(value, colname){
	if(value.length == 0)
	{
		pass_validate = '0';// 错误的时候为0
		return [false,"此字段不允许为空"];
	}	
	else
		return [true,""];
}

/**
 * 检验字符串格式满足ISBN号正确格式
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkStringISBN(value, colname){
	var regex13 = /^\d{3}-\d-\d{5}-\d{3}-\d$/;
	var regex10 = /^\d-\d{5}-\d{3}-\d$/;
	
	//console.log(value);
	if(value.length == 0){
		pass_validate = '0';// 错误的时候为0
		return [false,colname+" : 此字段不允许为空"];
	}
		
	else{
		if(regex13.test(value)){
			return [true,""];
		}else if(regex10.test(value)){
			return [true,""];
		}
		else{
			pass_validate = '0';// 错误的时候为0
			return [false,"请输入正确ISBN号!(说明：10位：4-88888-913-9; 13位： 978-4-88888-913-1)"];
		}
			
	}
}
/**
 * 检查ISSN代码
 * @param value
 * @param column
 * @returns {Array}
 */
function checkStringISSN(value,colname){
	var regex= /^\d{4}-\d{4}$/;
	if(value.length == 0){
		pass_validate = '0';// 错误的时候为0
		return [false,colname+" : 此字段不允许为空"];
	}
		
	else{
		if(regex.test(value))
			return [true,""];
		else{
			pass_validate = '0';// 错误的时候为0
			return [false,"请输入正确ISSN号!(格式：1234-5678)"];
		}
			
	}
}
/**
 * 检查邮箱格式
 * @param value
 * @param column
 * @returns {Array}
 */
function checkStringEmail(value,column){
	//var regex = /^[w-.]+@[w-.]+(.w+)+$/;
	//var regex = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
	//var regex = /\w+[@]{1}\w+[.]\w+/;
	//多个邮箱验证 邮箱地址;邮箱地址
	var regex = /^((([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6}\;))*(([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})))$/
	if($.trim(value)==""){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段不能为空！"];
	}else{
		if(regex.test(value)){
			return [true, ""];
		}else{
			pass_validate = '0';// 错误的时候为0
			return [false,column +" 字段格式不正确！"];
		}
	}
}
/**
 * 检查手机号、座机号、小灵通号
 * @param value
 * @param column
 */
function checkStringPhone(value,column){
	var regex = /^(1[3,5,8,7]{1}[\d]{9})|((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
	if($.trim(value)==""){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段不能为空！"];
	}else{
		if(regex.test(value)){
			return [true ,""];
		}else{
			pass_validate = '0';// 错误的时候为0
			return [false,column +" 字段格式不正确！"];
		}
	}
}
/**
 * 检查是否是电话类型或者是邮箱类型
 * @param value
 * @param column
 * @returns
 */
function checkStringPhoneOrEmail(value,column){
	var result = checkStringPhone(value,column);
	if(result[0]){
		return result;
	}else{
		result = checkStringEmail(value,column);
		if(result[0]){
			return result;
		}else{
			return [false, column+ " 字段格式不正确！"];
		}
	}
}
function checkStringPercent(value,column){
	//var regex = /\d+\.?\d+?%/;
	/*var regex1 = /^(0\.\d+|[1-9]\d*|[1-9]\d*\.\d+|0)$/;
	if($.trim(value)==""){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段不能为空！"];
	}else if(!regex1.test(value.substr(0,value.length-1))){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段格式不正确！（正确格式：20%）"];
	}else if(value.substr(value.length-1,1)!='%'){
		pass_validate = '0';// 错误的时候为0
		return [false,column +" 字段格式不正确！（正确格式：20%） "];
	}*/
	var result = checkPurePercent(value,column);
	if(!result[0]){
	 return result;
	}else{
		if(parseInt(value.substr(0,value.length-1))<=100){
			return [true ,""];
		}else{
			pass_validate = '0';// 错误的时候为0
			alert_dialog(column +" 字段应小于等于100%！");
			return [true, column +" 字段应小于等于100%！"];
		}
	}
}
function checkPurePercent(value,column){
	//var regex = /\d+\.?\d+?%/;
	var regex1 = /^(0\.\d+|[1-9]\d*|[1-9]\d*\.\d+|0)$/;
	if($.trim(value)==""){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段不能为空！"];
	}else if(!regex1.test(value.substr(0,value.length-1))){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段格式不正确！（正确格式：20%）"];
	}else if(value.substr(value.length-1,1)!='%'){
		pass_validate = '0';// 错误的时候为0
		return [false,column +" 字段格式不正确！（正确格式：20%） "];
	}else{
		if(parseInt(value.substr(0,value.length-1))>=0){
			return [true ,""];
		}else{
			pass_validate = '0';// 错误的时候为0
			return [false, column +" 字段比例必须为非负数！"];
		}
	}
}

function checkStringManyPhoneOrEmail(value,column){
	var regex = /^((([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6}\;))|(1[3,5,8,7]{1}[\d]{9}\;)|((400)-(\d{3})-(\d{4}\;))|(\d{7,8}\;)|((\d{4}|\d{3})-(\d{7,8}\;))|((\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}\;))|((\d{4}|\d{3})-(\d{3}|\d{7}|\d{8})-(\d{4}|\d{3}|\d{2}|\d{1}\;)))*((([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6}))|(1[3,5,8,7]{1}[\d]{9})|((400)-(\d{3})-(\d{4}))|(\d{7,8})|((\d{4}|\d{3})-(\d{7,8}))|((\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))|((\d{4}|\d{3})-(\d{3}|\d{7}|\d{8})-(\d{4}|\d{3}|\d{2}|\d{1})))$/;
		if($.trim(value)==""){
			pass_validate = '0';// 错误的时候为0
			return [false, column +" 字段不能为空！"];
		}else{
			if(regex.test(value)){
				return [true, ""];
			}else{
				pass_validate = '0';// 错误的时候为0
				return [false,column +" 字段格式不正确！若是多个联系方式请用英文';'隔开 "];
			}
		}
}

/**
 * @author mantantan
 * @param paramsAndValues
 * @param value
 * @param colname
 * 
 * 检验参数是否在当前字符串中，如果在则确保关联的列不为空
 */
function checkStringInAndNotNullOtherCol(paramsAndValues,value, colname){
	var key=paramsAndValues[0].split(",")[1];
	var otherCol=paramsAndValues[1].split(",")[1];
	//console.log(key,"===",otherCol);
	
	if(value.indexOf(key)>=0)
	{
	   alert("含有此字符串");
	   var content=$("input[id$='"+currentSaveRowId+"_"+otherCol+"']").val();
	   if($.trim(content)==""){
		   return [false, otherCol +" 字段应不能为空。"]; 
	   }
	}
	return [true, ""];
}