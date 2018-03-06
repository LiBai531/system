/*根据类型动态添加编辑列*/
	function addCheckCol(data)
	{
		//console.log(data);
		var checkCol={  
			  label : '内容',  
			  name : 'info',  
			  sortable : false,  
			  width : 80,  
			  fixed : false,  
			  align : "center",
			  search:false,
		};
		data.colConfigs.unshift(checkCol);
		return data;
	
	}
	//查看在线编辑框信息
	function addJQCheckBt(jqgrid_id,viewType,tableName){
		if(viewType == 'JQSHOW'){
			jQuery("#"+jqgrid_id).setGridParam().showCol("info");
			jQuery("#"+jqgrid_id).setGridParam().hideCol("CONTENT_ID");
		}else{
			jQuery("#"+jqgrid_id).setGridParam().hideCol("info");
		}	
		var ids = jQuery("#"+jqgrid_id).jqGrid('getDataIDs');
		for(var i=0;i < ids.length;i++){
			var elementId = jqgrid_id;
			var check_link="<a class='' href='#' onclick='checkObjectCollectItem("+ids[i]+",\""+tableName+"\",\""+elementId+"\")'>查看</a>";
			jQuery("#"+jqgrid_id).jqGrid('setRowData',ids[i],{info :check_link});
		}	
	}
function showSubGridData(subgrid_id, row_id,configUrl,dataUrl){
	var entityId = $(jqTable).getCell(row_id, 'problemCollectEntityId');
	var itemId = $(jqTable).getCell(row_id, 'problemCollectItemId');
	var subgrid_table_id;
	var viewType="";
	var tableName="";
	subgrid_table_id = subgrid_id+"_t";
	
	$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
	
	$.getJSON(configUrl+"?entityId="+entityId, //获取某entity的元数据信息
		function initJqTable(data) {
		data = addCheckCol(data);
		viewType=data.type;
		tableName = data.name;
		tableId = data.id;
		var itemIds = [];
			itemIds.push(itemId);
			$("#"+subgrid_table_id).jqGrid({
					url :dataUrl+
						'?entityId='+ entityId+
						'&itemIds=' + itemIds+
						'&backupVersionId='+backupVersionId,//取数据
				datatype : 'json',
				mtype : 'POST',
				colModel : data.colConfigs,
				height : "100%",
				autowidth : true,
				shrinkToFit : false,
				/*width:$("#content").width()-250,*/
				rowNum : 10,
				rowList : [ 10],
				viewrecords : true,
				sortname : data.defaultSortCol,
				sortorder : "asc",
				caption : data.name,
				jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
					root : "rows", //包含实际数据的数组  
					page : "pageIndex", //当前页  
					total : "totalPage",//总页数  
					records : "totalCount", //查询出的记录数  
					repeatitems : false,
				},
				prmNames : {
					page : "page",
					rows : "rows",
					sort : "sidx",
					order : "sord"
				},
				gridComplete:function(){
					addJQCheckBt(subgrid_table_id,viewType,tableName);
				}
			});
		});
}


function showSimilarityData(subgrid_id, row_id,configUrl,dataUrl){
	var entityId = $(jqTable).getCell(row_id, 'problemCollectEntityId');
	var itemId = $(jqTable).getCell(row_id, 'problemCollectItemId');
	var similarityIds = $(jqTable).getCell(row_id,'problemContent');
	var subgrid_table_id;
	subgrid_table_id = subgrid_id+"_t";
	var viewType = "";
	var tableName = "";
	$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
	
	$.getJSON(configUrl+"?entityId="+entityId, //获取某entity的元数据信息
		function initJqTable(data) {
			var itemIds = similarityIds.split(',');
			tableName = data.name;
			viewType= data.type;
			tableId = data.id;
			data = addCheckCol(data);
			itemIds.push(itemId);
			//console.log(itemIds);
			$("#"+subgrid_table_id).jqGrid({
					url :dataUrl+
						'?entityId='+ entityId+
						'&itemIds=' + itemIds+
						'&backupVersionId='+backupVersionId,//取数据
				datatype : 'json',
				mtype : 'POST',
				colModel : data.colConfigs,
				height : "100%",
				autowidth : true,
				shrinkToFit : false,
				/*width:$("#content").width()-250,*/
				rowNum : 10,
				rowList : [ 10],
				viewrecords : true,
				sortname : data.defaultSortCol,
				sortorder : "asc",
				caption : data.name,
				jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
					root : "rows", //包含实际数据的数组  
					page : "pageIndex", //当前页  
					total : "totalPage",//总页数  
					records : "totalCount", //查询出的记录数  
					repeatitems : false,
				},
				prmNames : {
					page : "page",
					rows : "rows",
					sort : "sidx",
					order : "sord"
				},
				gridComplete:function(){
					addJQCheckBt(subgrid_table_id,viewType,tableName);
				}
			});
		});
}

function showSubGridHistoryData(subgrid_id, row_id,configUrl,dataUrl,theEntityId,entityItemId,backupVersionId){
	var entityId = $(jqTable).getCell(row_id, theEntityId);
	var itemId = $(jqTable).getCell(row_id, entityItemId);
	var subgrid_table_id;
	subgrid_table_id = subgrid_id+"_t";

	$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
	
	$.getJSON(configUrl+"?entityId="+entityId, //获取某entity的元数据信息
		function initJqTable(data) {
			var itemIds = [];
			itemIds.push(itemId);
			$("#"+subgrid_table_id).jqGrid({
					url :dataUrl+
						'?entityId='+ entityId+
						'&itemIds=' + itemIds+
						'&backupVersionId='+backupVersionId,//取数据
				datatype : 'json',
				mtype : 'POST',
				colModel : data.colConfigs,
				height : "100%",
				autowidth : false,
				shrinkToFit : true,
				/*width:$("#content").width()-250,*/
				rowNum : 10,
				rowList : [ 10],
				viewrecords : true,
				sortname : data.defaultSortCol,
				sortorder : "asc",
				caption : data.name,
				jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
					root : "rows", //包含实际数据的数组  
					page : "pageIndex", //当前页  
					total : "totalPage",//总页数  
					records : "totalCount", //查询出的记录数  
					repeatitems : false,
				},
				prmNames : {
					page : "page",
					rows : "rows",
					sort : "sidx",
					order : "sord"
				}
			});
		}
	);
}			

/**
 * 当jqGrid无数据时作特殊显示
 * @param listGrid
 */
function showNoRecords(listGrid){
	var re_records = $(listGrid).getGridParam('records');
	if(re_records == 0 || re_records == null){
		if($(".norecords").html() == null){
			$(listGrid).parent().append("<div class=\"norecords\">暂无数据</div>");
		}
		$(".norecords").show();
		return false;
	}
	else{
		$(".norecords").hide();
		return true;
	}
}
