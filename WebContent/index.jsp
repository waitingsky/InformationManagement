<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表系统门户</title>
<link href="${pageContext.request.contextPath}/style/news.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
<jsp:include page="/foreground/common/head.jsp"/>

<div class="row-fluid">
	<div class="span3 custom-system" >
		<ul class="nav nav-list">
			<li class="nav-header">系统清单</li>
			<li class="custom-system-item">
				<a target="_blank" href="information?action=show&informationId=${newestNews.informationId }" title="海波龙预算系统">
					<i class="icon-th-large"></i>
					海波龙预算系统
				</a>
			</li>
<!-- 			<li class="custom-system-item"> -->
<%-- 				<a target="_blank" href="information?action=show&informationId=${newestNews.informationId }" title="海波龙合并系统"> --%>
<!-- 				<i class="icon-th-large"></i> -->
<!-- 				海波龙合并系统</a> -->
<!-- 			</li> -->
<!-- 			<li class="custom-system-item"> -->
<%-- 				<a target="_blank" href="information?action=show&informationId=${newestNews.informationId }" title="企业合并报表系统">	 --%>
<!-- 				<i class="icon-th-large"></i> -->
<!-- 				企业合并报表系统</a> -->
<!-- 			</li> -->
			<li class="custom-system-item">
				<a target="_blank" href="information?action=show&informationId=${newestNews.informationId }" title="企业预算报表系统">	
				<i class="icon-th-large"></i>
				企业预算报表系统</a>
			</li>
		</ul>
	</div>
	<div class="span6 newsHeader_list">
		<div class="newsHeader">
			<h3><a href="information?action=show&informationId=${headInfo.informationId }" title="${headInfo.title }">${fn:substring(headInfo.title,0,10) }</a></h3>
			<p>${fn:substring(headInfo.content,0,40) }...<a href="information?action=show&informationId=${headInfo.informationId }" >[查看全文]</a></p>
		</div>
		<div class="currentUpdate">
			<div class="currentUpdateHeader">最近更新</div>
			<div class="currentUpdateDatas">
			<table width="100%">
				<c:forEach var="tmpInfo" items="${latestInfoList }" varStatus="status">
					<c:if test="${status.index%2==0 }">
						<tr>
					</c:if>
					<td width="50%">
						<a href="information?action=show&informationId=${tmpInfo.informationId }" title="${tmpInfo.title }">${fn:substring(tmpInfo.title,0,12) }</a>
					</td>
					<c:if test="${status.index%2==1 }">
						</tr>
					</c:if>
				</c:forEach>
			</table>
			</div>
		</div>
	</div>
	<div class="span3">
		<div class="data_list hotspot_news_list">
			<div class="dataHeader">热点资讯</div>
			<div class="datas">
				<ul>
					<c:forEach var="tmpInfo" items="${hotInfoList }">
						<li><a href="information?action=show&informationId=${tmpInfo.informationId }" title="${tmpInfo.title }">${fn:substring(tmpInfo.title,0,15) }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>


<c:forEach var="allIndexInfo" items="${allIndexInfoList }" varStatus="allStatus">
	<c:if test="${allStatus.index%3==0 }">
		<div class="row-fluid">
	</c:if>
	<c:forEach var="tmpInfo" items="${allIndexInfo }" varStatus="oneStatus">
		<c:if test="${oneStatus.first }">
			<div class="span4">
			<div class="data_list news_list" >
				<div class="dataHeader">${channelList.get(allStatus.index).channelName }<span class="more"><a href="information?action=list&typeId=${channelList.get(allStatus.index).channelId }">更多...</a></span></div>
				<div class="datas">
					<ul>
		</c:if>
		
<%-- 		<c:if test="${tmpInfo.type==0}"> --%>
				<li><fmt:formatDate value="${tmpInfo.publishDate }" type="date" pattern="MM-dd"/>&nbsp;<a href="information?action=show&informationId=${tmpInfo.informationId }" title="${tmpInfo.title }" target="_blank">${fn:substring(tmpInfo.title,0,18) }</a></li>
<%-- 		</c:if> --%>
<%-- 		<c:if test="${tmpInfo.type==1}"> --%>
<%-- 				<li><fmt:formatDate value="${tmpInfo.publishDate }" type="date" pattern="MM-dd"/>&nbsp;<a href="file?action=fileDownload&fileName=${tmpInfo.content }" title="${tmpInfo.title }">${fn:substring(tmpInfo.title,0,18) }</a></li> --%>
<%-- 		</c:if> --%>
<%-- 		<c:if test="${tmpInfo.type==2}"> --%>
<%-- 				<li><fmt:formatDate value="${tmpInfo.publishDate }" type="date" pattern="MM-dd"/>&nbsp;<a href="${tmpInfo.content }" title="${tmpInfo.title }">${fn:substring(tmpInfo.title,0,18) }</a></li> --%>
<%-- 		</c:if> --%>
		
		<c:if test="${oneStatus.last }">
					</ul>
						</div>
					</div>
				</div>
		</c:if>
	</c:forEach>
	<c:if test="${allStatus.index%3==2 || allStatus.last }">
		</div>
	</c:if>
</c:forEach>

<jsp:include page="/foreground/common/link.jsp"/>
<jsp:include page="/foreground/common/foot.jsp"/>
</div>
</body>
</html>