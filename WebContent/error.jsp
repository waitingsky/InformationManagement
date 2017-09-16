<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${errorTitle}</title>
<link href="${pageContext.request.contextPath}/resources/css/news.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.js"></script>

<script type="text/javascript">
	function countDown(time,url){
		if(url==''){
		  url="#";
		}
		
		document.getElementById("second").innerHTML = time; 

		if(--time>0){
          setTimeout("countDown("+time+",'"+url+"')",1000);//设定超时时间
  		}else{
   	      location.href=url;//跳转页面
  		}
	} 
</script>
</head>
<body style="padding: 50px;background:#fff">  
	<div class="container" >  
	
      <div class="hero-unit">
        <h3>${errorTitle}</h3>
		<div class="alert alert-error">
			${errorMessage}
		</div>
		
		<c:if test="${!empty url}">
			<c:if test="${autoJump}">
				<p>将在 <span id="second">5</span> 秒钟后返回！<script language='javascript'>countDown(5,'${url}');</script></p> 
			</c:if>
	   		<p><a href="${url}" class="btn btn-link">点击跳转</a></p>
		</c:if>
	  </div>
	</div>
</body>  
</html>