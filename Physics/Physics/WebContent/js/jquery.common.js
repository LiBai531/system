;(function($){
	 $.commonRequest=function(config)
	 {
		 var default_config={
						async : false,
						cache : false,
						type : 'GET',
						dataType:'json',
						url : '',
						error :function(data){
							alert_dialog(' 操作出现错误，请联系管理员！');
						},
						success : function(data){
							if(data=='success')
								alert_dialog('操作成功！');
							else
								alert_dialog('操作失败！');
						},
		 };
		 var config=$.extend(default_config,config);
		 $.ajax(config);
	 }
})(jQuery);
/*状态由数字转为中文*/
function convertStatus(data,domainId)
{
	//console.log('状态 ： '+data);
	//console.log(domainId);
	var status='';
	if(domainId == "D201301"){
		switch (data) {
		case "0":
			status = "学科正在修改";
			break;
		case '1':
			status = "已提交至学校";
			break;
		case "2":
			status = "已提交至学位中心";
			break;
		case "3":
			status = "退回至学科";
			break;
		case "4":
			status = "学校撤销提交";
			break;
		case "5":
			status = "提交终止";
			break;
		case "6":
			status = "学校正在编辑";
			break;
		default:
			status = '该学科未参评！';
			break;
		}
	}	
	else{
		switch (data) {
		case "0":
			status = "学位点正在修改";
			break;
		case '1':
			status = "已提交至学校";
			break;
		case "2":
			status = "已提交至学位中心";
			break;
		case "3":
			status = "退回至学位点";
			break;
		case "4":
			status = "学校撤销提交";
			break;
		case "5":
			status = "提交终止";
			break;
		case "6":
			status = "学校正在编辑";
			break;
		default:
			status = '该学位点未参评！';
			break;
		}
	}
	return status;
}
/**
 * 主流程状态转化
 * @param state
 * @returns {String}
 */
function convertMainFlow(state){
	/*
	获取当前主流程状态
		* 0，本轮参评未开始进行
		* 1，正在预参评
		* 2，预参评结束
		* 3，正在申报
		* 4，申报结束
		* 5，预公示
		* 6，正在公示
		* 7，公示结束
		* 8，正在反馈
		* 9，反馈结束
		* 10,学科报告
		* 11,本轮参评结束 */
	switch(state)
	{
		case "0":
			return "本轮参评未开始进行";
		case "1":
			return "正在预参评";
		case "2":
			return "预参评结束";
		case "3":
			return "正在申报";
		case "4":
			return "申报结束";
		case "5":
			return "预公示";
		case "6":
			return "正在公示";
		case "7":
			return "公示结束";
		case "8":
			return "正在反馈";
		case "9": 
			return "反馈结束";
		case "10":
			return "学科报告";
		case "11":
			return "本轮参评结束";
	}
}
/**
 * 处理掉URL里面的特殊字符
 * 
 * @param url
 */
function urlDeal(url){
	/*+    URL 中+号表示空格                      %2B   
	空格 URL中的空格可以用+号或者编码           %20 
	/   分隔目录和子目录                                     %2F     
	?    分隔实际的URL和参数                             %3F     
	%    指定特殊字符                                          %25     
	#    表示书签                                                  %23     
	&    URL 中指定的参数间的分隔符                  %26     
	=    URL 中指定参数的值                                %3D
	/word/g
	*/
	url = url.replace(/\+/g,'%2B');
	url = url.replace(/\s/g,'%20');
	url = url.replace(/\//g,'%2F');
	url = url.replace(/\?/g,'%3F');
	//url = url.replace(/%/g,'%25');
	url = url.replace(/#/g,'%23');
	url = url.replace(/&/g,'%26');
	url = url.replace(/=/g,'%3D');
	return url;
}
