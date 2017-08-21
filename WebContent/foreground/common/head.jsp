<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row-fluid">
	<div class="span12 logo">
		<img src="${pageContext.request.contextPath}/images/logo.png">
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="navbar">
		  <div class="navbar-inner">
		    <a class="brand" href="goIndex">首页</a>
		    <ul class="nav">
		       <c:forEach var="tmpChannel" items="${channelList}">
		       		<li><a href="information?action=list&channelId=${tmpChannel.channelId }">${tmpChannel.channelName }</a></li>
		       </c:forEach>
		    </ul>
		    
		    <form class="navbar-form pull-right" style="display:none">
		     	<input type="text" class="input-small" placeholder="用户名">
				<input type="password" class="input-small" placeholder="密码">
				<button type="submit" class="btn btn-default">登录</button>
			</form>
			
			<p class="navbar-text pull-right">
				欢迎：<font color="">${currentUser.userName }</font>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:logout()">[&nbsp;退出系统&nbsp;]</a>
			</p>
		  </div>
		</div>
	</div>
</div>