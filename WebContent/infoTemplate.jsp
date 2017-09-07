<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${news.title }</title>

<link href="${pageContext.request.contextPath}/resources/css/news.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jQuery.js"></script>
</head>
<body>
<div class="container">
<jsp:include page="/common/head.jsp"/>

<div class="row-fluid">
	<div class="span8">
		<jsp:include page="${mainPage }"/>
	</div>
	<div class="span4">
		<div class="data_list right_news_list">
			<div class="dataHeader">最新资讯</div>
			<div class="datas">
				<ul>
					<c:forEach var="tmpInfo" items="${latestInfoList }">
						<li><a href="information?action=show&informationId=${tmpInfo.informationId }" title="${tmpInfo.title }">${fn:substring(tmpInfo.title,0,22) }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="data_list right_news_list">
			<div class="dataHeader">热门资讯</div>
			<div class="datas">
				<ul>
					<c:forEach var="tmpInfo" items="${popularList }">
						<li><a href="information?action=show&informationId=${tmpInfo.informationId }" title="${tmpInfo.title }">${fn:substring(tmpInfo.title,0,22) }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/common/foot.jsp"/>
</div>
</body>
</html>