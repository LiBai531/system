/**
 * 检查字段填写的字数
 * @param paramsAndValues
 * @param value
 * @param column
 */
function checkDataLength(paramsAndValues,value,column){
	var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
	if(parseInt(value.length)>parseInt(parm1)){
		pass_validate = '0';
		return [ false, column+" 此字段的长度超过"+parm1+"字请重新编辑！" ];
	}else{
		return [ true, ''];
	}
}
function checkDataLengthForEditor(paramsAndValues,value,column){
	var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
	if(parseInt(value.length)>parseInt(parm1)){
		pass_validate = '0';
		alert_dialog(column+" 字数不得超过"+parm1+"！");
		return false;
	}else{
		return true;
	}
}
