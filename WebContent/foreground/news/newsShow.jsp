<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div>
		<div class="news_title"><h3>${information.title }</h3></div>
		<div class="news_info">
			发布时间：[<fmt:formatDate value="${information.publishDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;作者：${information.author }
			&nbsp;&nbsp;频道：[${information.channelName }]&nbsp;&nbsp;阅读次数：${information.click }
		</div>
		<div class="news_content">
			${information.content }
		</div>
	</div>
	<div class="upDownPage">
		${pageCode }
	</div>
</div>