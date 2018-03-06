function outputJS(url){
	jQuery.ajax({
		url:url,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		type:"post",
		async: true,
		data:{param:"1"},
		dataType:"json",
		beforeSend:function(){
			if($("#outputModal").length == 0){
				$("html").append('<div id="outputModal" style="display:none;"></div>');
			}
			$("#outputModal").dialog({
				title:"正在导出文件，请稍等",
				height:'0',
				width:'200',
				position:'center',
				draggable:false,
				modal:true
			}).show();
		},
		success:function(data){
			if(data == false){
				alert_dialog("没有数据可以导出！");
			}else{
				var filePath = data.filepath;
				
				//base64加密
				var baseHelper = new Base64();  
				filePath = baseHelper.encode(filePath);
				
				var link = contextPath + '/download.jsp?'+"filepath="+filePath+"&"+"filename="+data.filename;	
				window.open(link,"_self");
			}
		},
		complete:function(){
			$("#outputModal").dialog("close");
			$("#logic_check_download").dialog("close");
		}
	});
}
//异议信息下载-2015-9-11
function pageDownloadNew(proveMaterialId,dataUrl){
	$.ajax({
		type:'POST',
		dataType:'json',
		url:dataUrl,
		data:'proveMaterialId='+proveMaterialId,
		success : function(data) {
			if( data.result == "failure" ){
				alert("文件不存在！");
			}
			else{
				var id = data;
				var commonPath =contextPath+'/publicity/viewObjection_getDownloadPathTure?proveMaterialId='+id;
				window.open(commonPath,"_self");
			}
		}
	});
}

function briefDown(commonPathnew){
	window.open(commonPathnew,"_self");
}

//下载证明材料
function downloadProveMaterial(path){
	var fileName = getFileName(path, "/");
	var filePath = getFilePath(path, "/");
	//base64加密
	var baseHelper = new Base64();  
	filePath = baseHelper.encode(filePath);
	
	var link = contextPath+'/download.jsp?'+"filepath="+filePath+"&filename="+fileName;
	window.open(link,"_self");
}

//下载模板
function downloadTemplate(url,ctx){
	jQuery.ajax({
		url:url,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		type:"get",
		async: true,
		dataType:"json",
		success:function(data){
			//console.log(data);
			if(data.result == "fail"){
				alert_dialog("下载文件失败");
				$("#outputModal").dialog("close");
			}else{
				var fileName= name = encodeURI(encodeURI(data.data.fileName));
				var link = ctx + '/downloadnew.jsp?'+"filepath="+data.data.filePath+"&"+"filename="+fileName;		
				window.open(link,"_self");
				$("#outputModal").dialog("close");
			}
		},
		complete:function(){
			
		}
	});
	var fileName = getFileName(path, "/");
	var filePath = getFilePath(path, "/");
	//base64加密
	var baseHelper = new Base64();  
	filePath = baseHelper.encode(filePath);
	
	var link = contextPath+'/download.jsp?'+"filepath="+filePath+"&filename="+fileName;
	window.open(link,"_self");
}

function outputJSWithParam(url,dataParam){
	jQuery.ajax({
		url:url,
		type:"post",
		data:dataParam,
		async: false,
		dataType:"json",
		beforeSend:function(){
			if($("#outputModal").length == 0){
				//console.log("create div");
				$("html").append('<div id="outputModal" style="display:none;"></div>');
			}
			$("#outputModal").dialog({
				title:"正在导出文件，请稍等",
				height:'0',
				width:'200',
				position:'center',
				draggable:false,
				modal:true
			}).show();
			//console.log("dialog show");
		},
		success:function(data){
			if(data){
				
				var filePath = data.filepath;
				
				//base64加密
				var baseHelper = new Base64();  
				filePath = baseHelper.encode(filePath);
				//var commonPath =""+/id;
				var link = contextPath + '/download.jsp?'+"filepath="+filePath+"&"+"filename="+data.filename;		
				window.open(commonPath,"_self");
			}
		},
		complete:function(){
			$("#outputModal").dialog("close");
		}
	});
}