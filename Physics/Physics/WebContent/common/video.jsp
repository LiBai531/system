<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="width:100%;">
<div>
	<span>视频名称： ${videoName}</span>
</div>
<video width="640" height="480" controls="controls" >
  <source src="${url}" type="video/mp4">
</video>

</body>
</html>