;(function($){
	$.commonForm=function(tableConfig)
	{										//form
		 var tableHtml ="";
		 $.each(tableConfig.tableTRConfigs,function(i,item){
			 tableHtml+= $.createTrfunc(item);
		 });
		 return tableHtml;
		 
	},
	$.createTrfunc=function(trConfig){
		var tr ="<tr></tr><tr>";//多加一行用于遍历，因为遍历的方法是从第二个开始的
		$.each(trConfig.tdConfigs,function(i,item){
			
			//console.log(item);
			tr+=$.createTdfunc(item);
		});
		tr+="</tr>";
		return tr;
	},
	//answer 2 ready to test length_control
	$.createTrFunc = function(trConfig)
	{
		var TotalDispLen = 1000;
		var LatestLen = 0;
		var tr = "<tr></tr><tr>";
		var ctrltype = "";
		$.each(trConfig.tdConfigs,function(i,item){
			LatestLen += item.displength;
			if(LatestLen <= TotalDispLen){
				if(ctrltype == "I" && item.controltype == "T")
					tr += "</tr><tr>";
				//待完善不兼容控件对
				else{
					tr += $.createTdfunc(item);
					ctrltype = item.controltype;
				}
			}else{
				tr += "</tr><tr>";
			}
		});
		tr +="</tr>";
		return tr;
	}
	// answer 1 try everything
	$.createTrfunc = function(trConfig)
	{
		var tr = "<tr></tr><tr>";
		var totalcol = 0;
		var totalrow = 0;
		$.each(trConfig.tdConfigs,function(i,item){
			if (item.colno > totalcol)
				totalcol = item.colno;
			if (item.rowno > totalrow)
				totalrow = item.rowno;
		});
		$.each(trConfig.tdConfigs,function(i,item){
			tr += $.createTdfunc(item,col,row);
			//createTdfunc改改改
			//createEditTD跟着改改改
		});
		tr+="</tr>";
		return tr;
	}
	
	
	$.createTdfunc = function(item)
	{
		if(tdConfig.isnull==true){
			return $.createNull();
		}
		else{
			var td = $.createLabel(tdConfig);
			td+=$.createEditTD(tdConfig);
			return td;
		}
	}
	if(tdConfig.isnull==true){
		td += $.createNull();
	}
	else{
		var td = $.createLabel(tdConfig);
		td+=$.createEditTD(tdConfig);
		//return td;
	}
	LatestLen += tdConfig.displength;
	while(LatestLen <= TotalDispLen)
	
	
	
	
	
	
	$.createTdfunc=function(tdConfig){
		if(tdConfig.isnull==true){
			return $.createNull();
		}else{
			var td = $.createLabel(tdConfig);
			td+=$.createEditTD(tdConfig);
			return td;
		}
	},
	$.createNull=function(){
		var td = "<td></td><td></td>";
		return td;
	},
	$.createLabel=function(tdConfig){
		var td = "<td><label hidden='true' >"+tdConfig.label+"</label></td>";
		if(tdConfig.hidden==false)
			td = $.changeHidden2False(td);
		return td;
	},
	$.changeHidden2False=function(str){
		 re=new RegExp("hidden='true'","g");
		 var newStr=str.replace(re," ");
		 return newStr;
		//return str;
		
	},
	$.createEditTD=function(tdConfig){
		var inputTd="<td><input id='"+tdConfig.name+"' name='"+tdConfig.name+"'  disabled='true' hidden='true' editable='"+tdConfig.editable+"' ";
		var selectTd="<td><select id='"+tdConfig.name+"' name='"+tdConfig.name+"' disabled='true' hidden='true' editable='"+tdConfig.editable+"'>";
		var radioInput = "<input type=radio name='"+ tdConfig.name+"' disabled='true' hidden='true' editable='"+tdConfig.editable+"' ";
		var checkBoxInput = "<input type=checkbox name='"+ tdConfig.name+"' disabled='true' hidden='true' editable='"+tdConfig.editable+"' ";
		var textArea = "<td><textarea id='"+tdConfig.name+"'name='"+tdConfig.name+"' rows='3' cols='20' disabled='true' hidden='true' editable='"+tdConfig.editable+"'></textarea></td>";
		switch(tdConfig.edittype){
			case 'button':
				inputTd+=$.addButton();
				if(tdConfig.hidden==false)
					inputTd = $.changeHidden2False(inputTd);
				return inputTd;
				break;
			case 'select':
				selectTd+=$.addSelect(tdConfig);
				if(tdConfig.hidden==false)
					selectTd = $.changeHidden2False(selectTd);
				return selectTd;
				break;
			case 'checkbox':
				var tdCheckBox = $.addCheckBox(tdConfig,checkBoxInput);
				if(tdConfig.hidden==false)
					tdCheckBox = $.changeHidden2False(tdCheckBox);
				return tdCheckBox;
				break;
			case 'radio':
				var tdRadio = $.addRadio(tdConfig,radioInput);
				if(tdConfig.hidden==false)
					tdRadio = $.changeHidden2False(tdRadio);
				return tdRadio;
				break;
			case 'textarea':
				var textArea =  $.addTextArea(tdConfig,textArea);
				if(tdConfig.hidden==false)
					textArea = $.changeHidden2False(textArea);
				return textArea;
				break;
			default:
				inputTd+=$.addInput();
				alert(tdConfig.hidden);
				if(tdConfig.hidden==false)
					inputTd = $.changeHidden2False(inputTd);
				return inputTd;
			break;
		}
		
	},
	$.addButton=function(){
		return "type='button'/></td>";
	},
	$.addInput=function(){
		return "type='text'/></td>";
	},
	$.addTextArea=function(tdConfig,textArea){
		return textArea;
	},
	$.addSelect=function(tdConfig){
		 var options ="<option></option>";
		 $.each(tdConfig.editoptions,function(key,value){
			 options+="<option value=\""+key+"\">"+value+"<option/>";
		 });
		 options+="</select>";
		 return options;
	},
	$.addRadio=function(tdConfig,inputRadio){
		var tdRadio="<td>";
		$.each(tdConfig.editoptions,function(key,value){
			tdRadio+= inputRadio+"value='"+key+"'/>"+value;
		});
		tdRadio+="</td>";
		return tdRadio;
	},
	$.addCheckBox=function(tdConfig,checkBoxInput){
		var tdCheckBox="<td>";
		$.each(tdConfig.editoptions,function(key,value){
			tdCheckBox+= checkBoxInput+"value='"+key+"'/>"+value;
		});
		tdCheckBox+="</td>";
		return tdCheckBox;
	},
	$.editForm = function(elems){
		$.each(elems,function(index,elem){
			//console.log(elem.attr('editable'));
			if(elem.attr('editable')=='true'){
				//console.log(elem.attr('name'));
				elem.removeAttr('disabled');
			}else{
				
			}
		});
	},
	$.lockForm=function(elems){
		$.each(elems,function(index,elem){
			//console.log(elem.attr('editable'));
			elem.attr('disabled',true);
		});
	},
	$.saveForm=function(formName,elems,url){
		var fData = $("#"+formName).serialize();
		$.commonRequest({
			url:url,
			data:fData,
			dataType:"text",
			type:'post',
			success:function(data){
				  if($.trim(data)!=''&&data=='success'){
	            	  alert_dialog('保存成功！');
	            	  lockForm(elems);
	              }else{
	            	  alert_dialog('保存失败，请重新提交！');
	              }
			}
		});
		/*$("#"+formName).submitForm({
			 url: url,
	         dataType: "json",
	         callback: function (data) {
	              if($.trim(data)!=''&&data=='success'){
	            	  alert_dialog('保存成功！');
	            	  lockForm(elems);
	              }else{
	            	  alert_dialog('保存失败，请重新提交！');
	              }
	          },
	          before: function () {
	                
	          }
		}).submit();*/
	},
	$.fillData=function(elems,datas){
		//console.log(elems);
		//console.log(datas);
		$.each(datas,function(key,value){
			$.each(elems,function(i,elem){
				if(elem.attr('name')==key){
					elemtype = elem[0].type;
					switch(elemtype){
					 case 'checkbox':
						 subvals = value.split(',');
						 $.each(subvals,function(i,v){
							 $("[name = "+key+"]:checkbox").each(function () {
								 if(($(this).val()==v)){
				                    	$(this).attr("checked",true);
				                    }
							 });
						 });
						 break;
					 case 'radio':
						 $("[name = "+key+"]:radio").each(function () {
			                    if(($(this).val()==value)){
			                    	$(this).attr("checked",true);
			                    }
			             });
						 break;
					 default:
						 elem.val(value);
						 break;
					}
				}
			});
		});
	};
})(jQuery);