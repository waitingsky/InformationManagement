<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	function checkUserForm(){
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("password").value;
		if(userName==null||userName==""){
			/* document.getElementById("error").innerHTML="用户名不能为空！"; */
			return false;
		}
		if(password==null||password==""){
			/* document.getElementById("error").innerHTML="密码不能为空！"; */
			return false;
		}
		return true;
	}
	
	function logout(){
		if(confirm('您确定要退出系统吗？')){
			window.location.href='user?action=logout';
		}
	}

</script>
<div class="row-fluid">
	<div class="span12 logo">
		<img src="config?action=configLogo">
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
		    
			<c:if test="${empty currentUser}">
				<form action="${pageContext.request.contextPath}/user?action=login" method="post" onsubmit="return checkUserForm()" class="navbar-form pull-right">
			     	<input type="text" id="userName" name="userName" class="input-small" placeholder="用户名"/>
					<input type="password" id="password" name="password" class="input-small" placeholder="密码"/>
					 <font id="error" color="red">${error }</font>
					<button type="submit" class="btn btn-default">登录</button>
				</form>
			</c:if>
			<c:if test="${!empty currentUser}">
				<p class="navbar-text pull-right">
					欢迎：<font color="">${currentUser.userName }</font>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:logout()">[&nbsp;退出系统&nbsp;]</a>
				</p>
			</c:if>
		  </div>
		</div>
	</div>
</div>