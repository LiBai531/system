<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
	<div class="input-group">
         <span class="input-group-addon">新闻标题</span>
         <input id="newsHead" type="text" value="${content.head}" class="form-control">
    </div>

      
      <div class="input-group">
         <span class="input-group-addon">新闻内容</span>
         <textarea id="newsContent" name="expInfo"  cols="45" rows="10" class="form-control" >${content.content}</textarea>
      </div>
</div>
</body>
</html>