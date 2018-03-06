$(document).ready(function () {
	
	/*菜单栏初始化*/
	//$( ".menu_lv1" ).accordion();
	
	/*横向菜单*/
	$("#menu_holder>li").find('ul').addClass('hidden');
	
	$("#menu_holder>li").hover(
		function(){
			$(this).find('ul').removeClass('hidden');
			$(this).find('ul').addClass('display');
		}
		,
		function(){
			$(this).find('ul').removeClass('display');
			$(this).find('ul').addClass('hidden');
		}
	);
	
	/* Home 键跳转*/
	$('span.home').click(function(){
		var url = '#';
		$.each($('a.hori_lvl1') , function(index, item){
			if($(item).find('span').html() == '首页')
				url =  $(item).attr('href');
		});
		
		$.get(url, function(data){
			  $( "#mainContent" ).empty();
			  $( "#mainContent" ).append( data );
		  }, 'html');
		event.preventDefault();
	});
	
	
	
	/*点击菜单打开标签页*/
	$('.hori_lvl2 a').click(function(event){
		//activeTab(this);
		if(this.href == "#") return;
		$.get(this.href, function(data){
			  $("#mainContent").empty();
			  $( "#mainContent" ).append( data );
		  }, 'html');
		event.preventDefault();
	});
	
	
	
	$('.hori_lvl1').click(function(event){
		if(this.href == "#") return;
		$.get(this.href, function(data){
			  $( "#mainContent" ).empty();
			  $( "#mainContent" ).append( data);
		  }, 'html');
		event.preventDefault();
	});
	
	
	/* 表格化表单布局*/
	$(".fr_table td:nth-child(even)").addClass("fr_left");
	$(".fr_table td:nth-child(odd)").addClass("fr_right");
	  
});