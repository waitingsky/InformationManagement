<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
</head>
<script type="text/javascript">

$(document).ready(function(){
	
	$("#checkedAll").click(function(){
		if($(this).prop("checked")==true){
			$("input[name='infoIds']").prop("checked",true);
		}else{
			$("input[name='infoIds']").prop("checked",false);
		}
	});
	
});

function batchDelete(){
	var chk_value=[];
	$('input[name="infoIds"]:checked').each(function(){
		chk_value.push($(this).val());
	});
	if(chk_value.length==0){
		alert("请选择要删除的数据");
		return;
	}
	var informationIds=chk_value.join(",");
	if(confirm("确认要删除这些资讯吗？")){
		$.post("information?action=delete",{informationIds:informationIds},
			function(result){
				var result=eval('('+result+')');
				if(result.success){
					alert("成功删除"+result.delNums+"条数据");
					window.location.href="${pageContext.request.contextPath}/information?action=adminList";
				}else{
					alert(result.errorMsg);
				}
			}
		);
	}
}

function singleDelete(informationId){
	if(confirm("确认要删除这条资讯吗？")){
		$.post("information?action=delete",{informationIds:informationId},
				function(result){
					var result=eval('('+result+')');
					if(result.success){
						alert("删除成功！");
						window.location.href="${pageContext.request.contextPath}/information?action=adminList";
					}else{
						alert(result.errorMsg);
					}
				}
			);
	}
}
</script>
<body>
<div class="data_list backMain">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="search_content" style="vertical-align: middle;">
		<div style="float: left;padding-top: 10px;">
			<button class="btn btn-mini btn-danger" type="button" onclick="batchDelete()">批量删除</button>
		</div>
		<div style="float: right;">
			<form action="${pageContext.request.contextPath}/information?action=adminList" method="post">
				资讯标题：<input type="text" id="s_title" name="s_title" style="width:180px" value="${s_title }"/>&nbsp;&nbsp;
				发布日期：<input type="text" id="s_bPublishDate" name="s_bPublishDate" class="Wdate" onclick="WdatePicker()" style="width: 100px;" value="${s_bPublishDate }"/>
				&nbsp;到&nbsp;<input type="text" id="s_aPublishDate" name="s_aPublishDate" class="Wdate" onclick="WdatePicker()" style="width: 100px;" value="${s_aPublishDate }"/>
				&nbsp;&nbsp;<button class="btn btn-mini btn-primary" type="submit" style="margin-top: -8px;">查询</button>
			</form>
		</div>
	</div>
	<div class="data_content">
		<table class="table table-hover table-bordered">
			<tr>
				<th><input type="checkbox" id="checkedAll"/></th>
				<th>序号</th>
				<th>资讯标题</th>
				<th>资讯频道</th>
				<th>发布时间</th>
				<th>操作</th>
			</tr>
			<c:forEach var="tmpInfo" items="${informationList }" varStatus="status">
				<tr>
					<td><input type="checkbox" name="infoIds" value="${tmpInfo.informationId}"/></td>
					<td>${status.index+1 }</td>
					<td>${tmpInfo.title }</td>
					<td>${tmpInfo.channelName }</td>
					<td><fmt:formatDate value="${tmpInfo.publishDate }" type="date" pattern="yyyy-MM-dd"/></td>
					<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location.href='information?action=edit&informationId=${tmpInfo.informationId}'">修改</button>&nbsp;<button class="btn btn-mini btn-danger" type="button" onclick="singleDelete(${tmpInfo.informationId})">删除</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="pagination pagination-centered">
  		<ul>
  			${pageCode }
  		</ul>
  	</div>
</div>
</body>
</html>