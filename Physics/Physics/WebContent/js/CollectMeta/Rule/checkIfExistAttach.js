function checkIfExistAttach(value, colname){
	//console.log('附件值：'+value);
	if(value!='undefined'){
		return [ true, "" ];
	}else{
		pass_validate = '0';
		return [ false , "请上传附件！"];
	}
	
}