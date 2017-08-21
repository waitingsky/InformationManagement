<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">


	function checkChange(){
		if(document.getElementById("isImage").checked){
			$("#hdtp").show();
		}else{
			$("#hdtp").hide();
		}
	}
	
	function checkPropertyChange(){
		var newProperty=$('input[name="optionsRadios"]:checked').val();

		if(newProperty!=null||newProperty!=""){
			if(newProperty=="0"){
				$("#normalNewsContent").show();
				$("#normalNewsAttachment").show();
				
				$("#normalNewsFile").hide();
				$("#normalNewsLink").hide();
			}else if(newProperty=="1"){
				$("#normalNewsFile").show();
				
				$("#normalNewsContent").hide();
				$("#normalNewsAttachment").hide();
				$("#normalNewsLink").hide();
			}else if(newProperty=="2"){
				$("#normalNewsLink").show();
				
				$("#normalNewsContent").hide();
				$("#normalNewsAttachment").hide();
				$("#normalNewsFile").hide();
			}
		}
	}
//     $(".switch").bootstrapSwitch();
	function addAttachment(){
		var tmpId = document.getElementById("attachmentTmpId").value;
		tmpId++;
		document.getElementById("attachmentTmpId").value = tmpId;
		
		var newAttach="<li id='li"+tmpId+"' class=''>"
		+"<span class='add-on'><i class='icon-file'></i></span>"
		+"<input type='file'/>"
		+"<button class='btn' type='button' onclick='removeAttachment("+tmpId+")'>-</button>"
		+"</li>";              
		$("#attachment").append(newAttach); 
	}
	
	function removeAttachment(attachId){
		$("#li"+attachId).remove();
	}
	
	function checkForm(){
// 		var title=document.getElementById("title").value;
// 		var author=document.getElementById("author").value;
// 		var typeId=document.getElementById("typeId").value;
// 		var content=CKEDITOR.instances.content.getData();
// 		var newProperty=$('input[name="optionsRadios"]:checked').val();

// 		if(title==null||title==""){
// 			document.getElementById("error").innerHTML="新闻标题不能为空！";
// 			return false;
// 		}
// 		if(author==null||author==""){
// 			document.getElementById("error").innerHTML="作者不能为空！";
// 			return false;
// 		}
// 		if(typeId==null||typeId==""){
// 			document.getElementById("error").innerHTML="请选择新闻类别！";
// 			return false;
// 		}
// 		if(newProperty==null||newProperty==""){
// 			document.getElementById("error").innerHTML="请选择新闻属性！";
// 			return false;
// 		}
		
// 		if(newProperty=="0"){
// 			// normal
// 			if(content==null||content==""){
// 				document.getElementById("error").innerHTML="新闻内容不能为空！";
// 				return false;
// 			}
// 		}else if(newProperty=="1"){
// 			// attachment
// 		}else if(newProperty=="2"){
// 			// link
// 		}
		
		return true;
	}
</script>
</head>
<body>
<div class="data_list backMain">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="data_content">
		<form action="config?action=configSave" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
			<table cellpadding="5" width="100%">
				<tr id="">
					<td valign="top">
						<label>服务与支持：</label>
					</td>
					<td>
						<textarea class="ckeditor" id=serviceSupport name="serviceSupport">${config.serviceSupport }</textarea>
					</td>
				</tr>
				
				<tr>
					<td>
<%-- 						<input type="hidden" id="newsId" name="newsId" value="${news.newsId }"/>&nbsp; --%>
					</td>
					<td>
						<input type="submit" class="btn btn-primary" value="保存配置"/>&nbsp;&nbsp;
						<input type="button" class="btn btn-primary" value="返回" onclick="javascript:history.back()"/>&nbsp;&nbsp;<font id="error" color="red">${error }</font>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
</body>
</html>