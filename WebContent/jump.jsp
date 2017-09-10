<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
<%
	String url = (String)request.getAttribute("login_sso_url");
	/*request.removeAttribute("login_sso_url");*/
	HashMap<String,Object> params = (HashMap<String,Object>)request.getAttribute("login_sso_params");
	/*session.removeAttribute("login_sso_params");*/
	if(url!=null&&url.length()>0&&params!=null){
%>
<form name="ssoForm" action="<%=url %>" method="post">
<%
	for (String key : params.keySet()) {
%>
	<input type="hidden" name="<%=key %>"  value="<%=params.get(key) %>">
<%
	}
%>
</form>
<script>window.document.ssoForm.submit();</script>
<%
	}
%>
</body>
</html>