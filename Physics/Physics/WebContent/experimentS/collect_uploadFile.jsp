<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<form>
	
	<table>
		<tr class="hidden">
			<td><input type="hidden" id="sessionid"
				value="${pageContext.session.id}" /></td>
		</tr>
		<tr>
			<td><span class="TextFont">附件上传：</span></td>
			<td colspan="4">
				<input id="file_upload" name="file_upload" type="file" multiple="true">
				<div id="queue"></div> <a class="button"
						href="javascript:$('#file_upload').uploadify('upload')">上传</a>| <a
						class="button"
						href="javascript:$('#file_upload').uploadify('cancel')">取消上传</a></td>
		</tr>
	</table>
</form>

<script type="text/javascript">
		$(function() {
			console.log($('sessionid').val());
			$('#file_upload').uploadify({
				'auto' : false,
				'swf'      : 'js/uploadify/uploadify.swf',
				'uploader' : '/Physics/upload/homework?jsessionid='+$('#sessionid').val()+"&experimentStudentId="+experimentStudentId
			});
		});
</script>
