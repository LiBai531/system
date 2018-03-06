/**
 *  逻辑检查——字符串规则检验js函数
 *  @author mantantan 
 */ 
/**
 * 检查每种记录限填不超过多少
 * @param value
 * @param colname
 * @returns {Array}
 * checkRecordNums
 */
function checkRecordNumsNoMore(paramsAndValues,value, colname){
	//console.log('paramsAndValues:',paramsAndValues,' value:',value,' colname:',colname);
	
	var rowIds = $("#jqGrid_collect_tb").getDataIDs();
	var count = 1;
	var maxNum=paramsAndValues[0].split(",")[1];
	var name =paramsAndValues[1].split(",")[1];
	console.log("currentSaveRowId:"+currentSaveRowId);
	for(var i=0;i<rowIds.length;i++){
		
		var rowData=$("#jqGrid_collect_tb").jqGrid('getRowData',rowIds[i]); 
		console.log("rowData[SEQ_NO]:"+rowData["SEQ_NO"]);
		if((currentSaveRowId)==rowData["SEQ_NO"]) continue;
		if(rowData[name]==value){
			count++;
		}
	}
	if(count<=maxNum){
		return [true,""];
	}else{
		return [false,'每个'+colname+"限填"+maxNum+"项。"];
	}
	
}