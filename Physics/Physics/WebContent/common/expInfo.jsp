<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<style>
	#news_panel{margin:0px auto;width:95%;}
	#news_title{height:40px;border-bottom:5px solid #33A1C9;margin:15px 0px;}
	#news_title p{margin:0px auto;text-align: center;font-size:30px;font-weight:bold;font-family:黑体;}
	#news_state{font-size:15px;text-align: center;color:#bbb;margin:10px;font-weight:bold;}
	#news_content{margin:20px;padding:10px 20px;min-height:300px;}
	#news_file{height:40px;border-top:2px solid #33A1C9;border-bottom:2px solid #33A1C9;margin:15px 0px;}
	#news_file p{margin:0px 20px;padding:10px 20px;font-size:15px;font-weight:bold;color:#bbb;}
</style>

<div id="news_panel">
	<div id="news_title"><p>${experimentInfo.name}</p></div>
	<textarea name="baidu_new_txa" id="baidu_new_txa" style="width:100%;height:300px;" readOnly>${experimentInfo.info}</textarea>
</div>