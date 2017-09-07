<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function setDateTime(){
			var date=new Date();
			var day=date.getDay();
			var week;
			switch(day){
			case 0:week="星期日";break;
			case 1:week="星期一";break;
			case 2:week="星期二";break;
			case 3:week="星期三";break;
			case 4:week="星期四";break;
			case 5:week="星期五";break;
			case 6:week="星期六";break;
			}
			var today=date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日  "+week+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
			document.getElementById("today").innerHTML=today;
		}
		
		window.setInterval("setDateTime()", 1000);
		
		function logout(){
			if(confirm('您确定要退出系统吗？')){
				window.location.href='user?action=adminLogout';
			}
		}
	</script>
<div class="row-fluid">
	<div class="span12">
		<div>
			<div class="headLeft logo">
				<img src="config?action=configLogo">
			</div>
			<div class="headRight">
				欢迎管理员：<font color="red">${currentManageUser.userName }</font>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:logout()">[&nbsp;退出系统&nbsp;]</a>&nbsp;&nbsp;&nbsp;&nbsp;<font id="today" class="currentDateTime"></font>
			</div>
		</div>
	</div>
</div>
