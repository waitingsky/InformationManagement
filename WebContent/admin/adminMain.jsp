<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表系统门户</title>
<link href="${pageContext.request.contextPath}/resources/css/news.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jQuery.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/My97DatePicker/WdatePicker.js"></script>
<%
	String mainPage=(String)request.getAttribute("mainPage");
	if(mainPage==null || mainPage.equals("")){
		mainPage="/admin/default.jsp";
	}
%>
</head>
<script type="text/javascript">
	function refreshSystem(){
		$.post("init",{},
			function(flag){
				var flag=eval('('+flag+')');
				if(flag){
					alert("系统刷新成功！");
				}else{
					alert("系统刷新失败！");
				}
			}
		);
	}
</script>
<body>
<div class="container">
<jsp:include page="/admin/common/head.jsp"/>

<div class="row-fluid">
	<div class="span3">
		<div class="newsMenu">
			<ul class="nav nav-tabs nav-stacked">
				  <li><a href="${pageContext.request.contextPath}/admin/adminTemplate.jsp"><strong>主页</strong></a></li>
				  <li><a href="#"><strong>资讯管理</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/information?action=edit">&nbsp;&nbsp;资讯添加</a></li>
				  <li><a href="${pageContext.request.contextPath}/information?action=adminList">&nbsp;&nbsp;资讯维护</a></li>
				  <li><a href=""><strong>资讯频道管理</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/channel?action=edit">&nbsp;&nbsp;资讯频道添加</a></li>
				  <li><a href="${pageContext.request.contextPath}/channel?action=adminList">&nbsp;&nbsp;资讯频道维护</a></li>
				  <li><a href=""><strong>系统管理</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/config?action=config">&nbsp;&nbsp;系统配置</a></li>
			</ul>
		</div>
	</div>
	<div class="span9">
		<jsp:include page="<%=mainPage %>"></jsp:include>
	</div>
</div>
<jsp:include page="/admin/common/foot.jsp"/>
</div>
</body>
</html>