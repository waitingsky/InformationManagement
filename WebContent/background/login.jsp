<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>报表门户系统</title>
    
    <!-- Le styles -->
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

	<!-- jQuery -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/background/resources/scripts/jquery-1.3.2.min.js"></script>
	<!-- jQuery Configuration -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/background/resources/scripts/simpla.jquery.configuration.js"></script>
	<!-- Facebox jQuery Plugin -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/background/resources/scripts/facebox.js"></script>
	<!-- jQuery WYSIWYG Plugin -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/background/resources/scripts/jquery.wysiwyg.js"></script>

    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        /*border: 1px solid #e5e5e5;*/
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
    </style>

</head>
<script type="text/javascript">
	function checkForm(){
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("password").value;
		if(userName==null||userName==""){
			document.getElementById("error").innerHTML="用户名不能为空！";
			return false;
		}
		if(password==null||password==""){
			document.getElementById("error").innerHTML="密码不能为空！";
			return false;
		}
		return true;
	}
</script>
<body>
	<div class="container">
	    <form class="form-signin" action="${pageContext.request.contextPath}/user?action=adminLogin" method="post" onsubmit="return checkForm()">
		    <div class="page-header">
		 		<h2>报表门户系统 </h2>
		 		<h3 class="pull-right"><small>后台管理</small></h3>
			</div>
			<div class="form-group">
			      	<input type="text" class="input-block-level" id="userName" name="userName" value="${userName }" placeholder="用户名">
			</div>
			<div class="form-group">
				<input type="password" class="input-block-level" id="password" name="password" value="${password }" placeholder="密码">
			</div>
			<div class="form-group">
			 	<font id="error" color="red">${error }</font>   
			</div>
			<div class="form-group">
			  	<button class="btn btn-large btn-primary" type="submit">登录</button>
			</div>
		</form>
	</div>
</body>
</html>
