<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>文件上传成功</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
</head>

<body>
	${fileUrl} 文件上传成功！
	<br>
	<a target="_blank" href="<%=basePath%>download?fileId=${fileUrl}">下载
		${fileUrl}</a>&nbsp;&nbsp;<br><br>
	<a target="_blank" href="http://tracker2/${fileUrl}">查看
		${fileUrl}</a>&nbsp;&nbsp;
	<a id="delete" href="javascript:void(0)" onclick="deleteFile('${fileUrl}');">删除文件</a>
<script>
	
	function deleteFile(fileId) {
		$.ajax({
			type : 'post',
			url : 'delete!deleteFile.action',
			dataType : 'json',
			data : {
				'fileId' : fileId
			},
			success : function(data) {
				alert(data.msg);
			}
		});
	}
</script>
</body>
</html>
