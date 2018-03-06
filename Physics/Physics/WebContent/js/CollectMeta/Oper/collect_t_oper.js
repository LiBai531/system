/**
 * 按钮集
 */
var id;
/**
 * 
 * @param id 行号
 * @param operType，操作类型，增删改查
 * @param type，表格、表单类型
 */
function buildLink(id,operType,type)
{
	var link='';
	if(operType=='save'){
		 link="<a class='' href='#' onclick='saveCollectItem("+id+","+type+")'>保存</a>";
	}
	if(operType=='edit'){
		 link="<a class='' href='#' onclick='editCollectItem("+id+","+type+")'>编辑</a>";
	}
	if(operType=='del')
	{
		 link="<a class='' href='#' onclick='delCollectItem("+id+","+type+")'>删除</a>";
	}
	if(operType=='cancel')
	{
		 link="<a class='' href='#' onclick='cancelCollectItem("+id+","+type+")'>取消</a>";
	}
	if(operType=='newCancel'){
		link="<a class='' href='#' onclick='cancelNewItem("+id+","+type+")'>取消</a>";
	}
	return link;	
}
//表格保按钮存
var save_link_tb = "<a class='' href='#' onclick='saveCollectItem("+id+","+'0'+")'>保存</a>"; 
//表格取消按钮
var cancel_link_tb="<a class='' href='#' onclick='cancelCollectItem("+id+","+'0'+")'>取消</a>";
//表格删除按钮
var del_link_tb="<a class='' href='#' onclick='delCollectItem("+id+","+'0'+")'>删除</a>";
//表格编辑按钮
var edit_link_tb = "<a class='' href='#' onclick='editCollectItem("+id+","+'0'+")'>编辑</a>"; 
//表单保存按钮
var save_link_fm = "<a class='' href='#' onclick='saveCollectItem("+id+","+'1'+")'>保存</a>"; 
//表单取消按钮
var cancel_link_fm="<a class='' href='#' onclick='cancelCollectItem("+id+","+'1'+")'>取消</a>";
//表单删除按钮
var del_link_fm="<a class='' href='#' onclick='delCollectItem("+id+","+'1'+")'>删除</a>";
//表单编辑按钮
var edit_link_fm = "<a class='' href='#' onclick='editCollectItem("+id+","+'1'+")'>编辑</a>"; 

function initDefaultValues(data)
{
	var defaultData='{';
	$.each(data.colConfigs,function(i,item){
		if(i>0){
			if(i==data.colConfigs.length-1)
				defaultData+='\"'+item.name+'\":\"'+''+'\"';
			else
				defaultData+='\"'+item.name+'\":\"'+''+'\",';
		}
	});
	defaultData+='}';
	//console.log(defaultData);
	return JSON.parse(defaultData);
}
function addCollectItemPart(type){
	var initData =initDefaultValues(jqGridConfig);
	//console.log('初始化数据！');
	//console.log(initData);
	var eleId ='';
	if(type=='0')
	{
		eleId='jqGrid_collect_tb';
	}else if(type=='1'){
		eleId='jqGrid_collect_fm';
	}else if(type=='2'){
		eleId = 'jqGrid_collect_initJq';
		initedJqrowData[id] = $("#"+eleId).jqGrid("getRowData",id);
	}
	newAddSeqNos.push(++records);
	//alert('addRow');
	$("#"+eleId).jqGrid('addRow',{  
        rowID : records, 
            position :"first",  
            useDefValues : true,  
            useFormatter : true,  
         	initdata :initData,
        }); 
	$("#"+eleId).jqGrid('editRow',records,false,setRowControllers);
	var save_link= buildLink(records,'save',type);
	//var del_link=buildLink(records,'del',type);
	var cancel_link = buildLink(records,'newCancel',type);
	jQuery("#"+eleId).jqGrid('setRowData',records,{oper :save_link+' | '+cancel_link});
	//console.log(records);
	jQuery("#"+eleId).jqGrid('setRowData',records,{SEQ_NO :records});
}
function addCollectItem(type){
	var eleId ='';
	if(type=='0')
	{
		eleId='jqGrid_collect_tb';
	}else if(type=='1'){
		eleId='jqGrid_collect_fm';
	}else if(type=='2'){
		eleId = 'jqGrid_collect_initJq';
		initedJqrowData[id] = $("#"+eleId).jqGrid("getRowData",id);
	}
	//alert(isAllData);
	if(!isAllData){
		isAllData=true;
		newAddSeqNos.splice(0,newAddSeqNos.length);
		addNewing = true;
		$("#fbox_"+eleId+"_reset").trigger("click");
	}else{
		addCollectItemPart(type);
	}
}
/**
 * 编辑
 * @param id
 * @param type,0是table,1是form类型
 */
function editCollectItem(id,type)
{	
	////alert('edit');
	var eleId='';
	if(type=='0')
	{
		eleId='jqGrid_collect_tb';
	}else if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	
	
	
	jQuery('#'+eleId).jqGrid('editRow',id,false,setRowControllers);
	var save_link= buildLink(id,'save',type);
	var cancel_link = buildLink(id,'cancel',type);

	jQuery("#"+eleId).jqGrid('setRowData',id,{oper :save_link+' | '+cancel_link});
}
/**
 * 设置相关控件列
 * @param id
 */
function setRowControllers(id){
	//设置日期控件
	setDateCol(controllerDic.DATE,id);	
	//设置percent模板列
	setPercentCol(controllerDic.PERCENT,id);
	//设置年月控件
	//console.log(controllerDic.YEARMONTH);
	setYearMonthCol(controllerDic.YEARMONTH,id);
	//console.log(controllerDic.LINKSELECT);
	setLinkSelectCol(controllerDic.LINKSELECT,id);
	//上传文件附件
	setUploadFileCol(controllerDic.FILE,id);
	setDate2Col(controllerDic.DATE2,id);
	setSearchSelectCol(controllerDic.SEARCHSELECT,id);
}

/**
 * 保存
 * @param id
 */
function saveCollectItem(id,type)
{
	var eleId='';
	var edit_link='';
	var del_link='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';	
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	currentSaveRowId = id;
	currentJqGridId = eleId; 
	edit_link=buildLink(id,'edit',type);
	del_link=buildLink(id,'del',type);	
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	
	//console.log(rowData);
	
	primaryKey=rowData['ID'];
	//console.log('pk : '+primaryKey);
	if(primaryKey==''){
		primaryKey ='0';
	}
	//var collegeId='10006';
	//var disciplineId='0835';
	//var userId = rowData['ACHIEVE_OF_PERSON_ID'];
	jQuery("#"+eleId).jqGrid('saveRow',id,
	  {
		url:contextPath+'/TCollect/toTCollect/JqOper/collectionTEdit/'+ tableId + '/' + primaryKey +'/' + titleValues,
		aftersavefunc:function(data){
			//alert(data);
			//console.log(data);
		if(data){
			jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link+' | '+del_link});	
		}else
			alert_dialog('保存失败！');
		},
		successfunc:function(data){
			//console.log("-------------");
			//console.log(data.responseText);
			if(data.responseText!='SAVE_ERROR'&&data.responseText!='UPDATE_ERROR'){
				//alert_dialog('编辑成功！');
				//如果主键为0，表示数据为前台生成，需重新加载数据的主键
				newAddSeqNos.splice($.inArray(id,newAddSeqNos),1);
				if(primaryKey=='0')
				{
					if(type=='2'){
						$("#jqGrid_collect_initJq_hidden").jqGrid('setGridParam').trigger("reloadGrid");
					}else{
						jQuery("#"+eleId).jqGrid('setRowData',id,{ID:data.responseText});
						//$("#"+eleId).jqGrid('setGridParam').trigger("reloadGrid");
					}
				}
				jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link+' | '+del_link});	
				return true;
			}else
				{
					alert_dialog('编辑失败！');
					cancelCollectItem(id,type);
					return false;
				}
				
		}});
}
/**
 * 取消
 * @param id
 */
function cancelCollectItem(id,type)
{
	//console.log('cancel!');
	var eleId='';
	var del_link='';
	var edit_link='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
		
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	edit_link=buildLink(id,'edit',type);
	del_link=buildLink(id,'del',type);	
	jQuery('#'+eleId).jqGrid('restoreRow',id);
	jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link+' | '+del_link});
	
}
/**
 * 删除
 * @param id
 */
function delCollectItem(id,type)
{
	var eleId='';
	var delOptions={
			caption: "删除操作",
			msg: "<p>删除操作会导致未保存数据丢失</p>" +
					"<p>删除前请保存数据！</p>",
			bSubmit: "删除",
			bCancel: "取消"
	};
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		delOptions={
				caption: "清空操作",
				msg: "你确定要清空此条数据吗?",
				bSubmit: "清空",
				bCancel: "取消"
		};
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		delOptions={
				caption: "清空操作",
				msg: "你确定要清空此条数据吗?",
				bSubmit: "清空",
				bCancel: "取消"
		};
		eleId ='jqGrid_collect_initJq';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	if(primaryKey==''){
		primaryKey='-1';
	}
	$("#"+eleId).jqGrid('setGridParam',{msg: delOptions.msg});
	jQuery('#'+eleId).jqGrid('delGridRow',id,
	{
		url:contextPath+'/TCollect/toTCollect/JqOper/collectionTEdit/'
		+ tableId
		+ '/'
		+primaryKey
		+'/'
		+ titleValues,
		caption: delOptions.caption,
		msg: delOptions.msg,
		bSubmit: delOptions.bSubmit,
		bCancel: delOptions.bCancel,
		width:250,
		height:180,
		reloadAfterSubmit:true,
		afterSubmit:function(data){
			if(data.responseText=='success')
			{
				newAddSeqNos.splice(0,newAddSeqNos.length);
				//alert_dialog('删除成功！');
				/*if(type==2){
					$("#jqGrid_collect_initJq_hidden").jqGrid('setGridParam').trigger("reloadGrid");
				}*/
				return [true,'删除成功！'];
			}else
				{
					return [false,'删除失败！'];
				}
	}});
	/*
	var eleId='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	//seqNo=rowData['SEQ_NO'];
	//console.log('seqNo : '+seqNo);
	seqNo=-1;
	var userId = rowData['ACHIEVE_OF_PERSON_ID'];
	if(typeof(seqNo)=='undefined')
	{
		seqNo=-1;
	}
	jQuery('#'+eleId).jqGrid('delGridRow',id,
	{
		url:contextPath+'/TCollect/toTCollect/JqOper/collectionTEdit/'
		+ tableId
		+ '/'
		+primaryKey
		+'/'
		+ titleValues
		+ '/'
		+  userId
		+'/'
		+ seqNo,
		afterSubmit:function(data){
			if(data.responseText=='success')
			{
				//alert_dialog('删除成功！');
				return [true,'删除成功！'];
			}else
				{
					return [false,'删除失败！'];
				}
	}});*/
}

function excel_output(tableId,sortOrder,sortName,searchRule)
{
	var url=contextPath+'/TCollect/toTCollect/export/excel/'
			+ tableId
			+ '/'
			+ sortOrder
			+'/'
			+ sortName
			;
	if(searchRule==''){
		document.getElementById("excel_output").href=url;
		document.getElementById("excel_baseSearch_output").href=url;
	}else if (typeof(searchRule) == "undefined"){
		document.getElementById("excel_baseSearch_output").href=url+"?filters="+urlDeal(JSON.stringify(searchRule));
	}else{
		//alert(urlDeal(JSON.stringify(searchRule)));
		document.getElementById("excel_baseSearch_output").href=url+"?filters="+urlDeal(JSON.stringify(searchRule));
	}	
}
/**
 * 带有提示信息的批量删除
 * @param viewType
 */
function batchDelItems2(viewType){
	//alert('新添加记录数： '+newAddSeqNos.length);
	//alert('批量添加！');
	if(newAddSeqNos.length>0){
		$("#dialog_confirm").empty().append("<p>有数据还没有保存,</p>" +
				"<p>删除操作可能会引起未保存数据的丢失，</p>"+
				"你确定要删除吗？</p>");
		$("#dialog_confirm").dialog({
			height:200,
			width:300,
			buttons:{
				"确定":function(){
					newAddSeqNos.splice(0,newAddSeqNos.length);
					batchDelItems(viewType);
				},
				"取消":function(){
					$(this).dialog("close");
				}
			}
		});
	}else{
		//batchDelItems(viewType);
		$("#dialog_confirm").empty().append("<p>你确定要删除吗？</p>");
		$("#dialog_confirm").dialog({
			height:150,
			buttons:{
				"确定":function(){
					batchDelItems(viewType);
					$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
			}
		});
	}
}
/**
 * 批量删除
 * @param viewType
 */
function batchDelItems(viewType){
	var itemId='';
	//console.log(viewType);
	if(viewType=='JQGRID'||viewType == 'JQSHOW'){
		itemId='jqGrid_collect_tb';
	}else{
		itemId='jqGrid_collect_fm';
	}
	//console.log(itemId);
	var ids=$('#'+itemId).jqGrid('getGridParam','selarrrow');
	var pkValues= new Array();//主键集合
	if(ids.length>0){
		$.each(ids,function(i,item){
			//console.log(i+' : '+ item);
			var rowData = $("#"+itemId).jqGrid("getRowData",ids[i]);
			var pkvalue = rowData['ID'];
			//var seqNo = rowData['SEQ_NO'];
			pkValues.push(pkvalue);
		});
		$.commonRequest({
			url:contextPath+"/TCollect/toTCollect/JqOper/batchDel/"+tableId+"/"+pkValues,
			dataType:'text',
			success:function(data){
				if(data=='success'){
					alert_dialog('删除成功！');
					$("#"+itemId).jqGrid('setGridParam').trigger("reloadGrid");
				}else{
					alert_dialog('删除失败！');
				}
			},
			error:function(data){
				
			}
		});
	}else{
		alert_dialog('请选择删除的行！');
	}
	/*
	var itemId='';
	//console.log(viewType);
	if(viewType=='JQGRID'||viewType == 'JQSHOW'){
		itemId='jqGrid_collect_tb';
	}else{
		itemId='jqGrid_collect_fm';
	}
	//console.log(itemId);
	var ids=$('#'+itemId).jqGrid('getGridParam','selarrrow');
	var pkValues= new Array();//主键集合
	if(ids.length>0){
		$("#dialog_confirm").empty().append("<p>你确定要删除吗？</p>");
		$("#dialog_confirm").dialog({
			height:150,
			buttons:{
				"确定":function(){
					$.each(ids,function(i,item){
						//console.log(i+' : '+ item);
						var rowData = $("#"+itemId).jqGrid("getRowData",ids[i]);
						var pkvalue = rowData['ID'];
						pkValues.push(pkvalue);
					});
					$.commonRequest({
						url:contextPath+"/TCollect/toTCollect/JqOper/batchDel/"+tableId+"/"+pkValues,
						dataType:'text',
						success:function(data){
							if(data=='success'){
								alert_dialog('删除成功！');
								$("#"+itemId).jqGrid('setGridParam').trigger("reloadGrid");
							}else{
								alert_dialog('删除失败！');
							}
						},
						error:function(data){
							
						}
					});
					$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
			}
		});
	}else{
		alert_dialog('请选择删除的行！');
	}*/
}
/*
 * 下载附件
 */
function downLoadAttach(){
	
	var url=contextPath+"/Collect/toCollect/JqOper/toLoadTemplateName/"+attachId;
	document.location.href = url;
}

/**
 * 由于增加了参数，用于专门下载教师用户上传的附件
 * @param downLoadAttach
 */
function downLoadAttach(downLoadAttach){
	var templateId =attachId;
	var url=contextPath+"/TCollect/toTCollect/JqOper/downLoadTemplate/"+attachId;
	//console.log(downLoadAttach);
	document.getElementById("downLoadAttach").href=url;
}
/**
 * 取消新增行数据
 * @param id
 */
var iscancelNewItem = true;
function cancelNewItem(id,type)
{
	//alert("new Cancel!");
	if(iscancelNewItem){
	iscancelNewItem = false;
	if(isOpen){
		$("#yy_mm_dv").dialog("close");
	}
	var eleId='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}	
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		eleId ='jqGrid_collect_initJq';
	}
	$.each(newAddSeqNos,function(i,item){
		if(item>id)
		{
			var rowData = $("#"+eleId).jqGrid("getRowData",item);
			jQuery("#"+eleId).jqGrid('setRowData',item,{SEQ_NO :rowData['SEQ_NO']-1});		
		}
	});
	jQuery('#'+eleId).jqGrid('delRowData',id);
	newAddSeqNos.splice($.inArray(id,newAddSeqNos),1);
	iscancelNewItem = true;
	}
}
