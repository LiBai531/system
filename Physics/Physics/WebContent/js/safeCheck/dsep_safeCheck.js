/**
 * 对于提交非法字符进行检查
 */
var UnsafeCheck = function(){
	//this.unsafeCharSet = ["|","&",";","$","%","@","'","\"\"","\'","\"","<",">"];
	this.checkIfSafe = function(str){
		//console.log(str);
		var length = str.length||"";
		for(var i=0;i<length;i++){
			if($.inArray(str[i],unsafeCharSet)!=-1)
				return false;
		}
		return true;
	};
	/**
	 * 检查表单是否合法，mapValues为键值对，键：ID 值，value为中文标题
	 * 返回string，如果出错返回提示信息
	 * 如果没有问题返回字符串形式的 true
 	 */
	this.checkFormIfSafe = function(mapValues){
		var unsafeCharSet = ["|","&",";","$","%","@","'","\"\"","\'","\"","<",">"];
		var result = "true";
		$.each(mapValues,function(key,title){
			var value = $("#"+key).val();
			var length = value.length||"";
			for(var i=0;i<length;i++){
				if($.inArray($.trim(value[i]),unsafeCharSet)!=-1){
					result = title +" 包含非法字符，请重新检查！";
					break;
				}	
			}	
		});
		return result;
	};
};
