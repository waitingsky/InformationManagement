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
	function checkTypeChange(){
		var type=$('input[name="optionsRadios"]:checked').val();

		if(type!=null||type!=""){
			if(type=="0"){
				$("#typeNormalContent").show();
				$("#typeNormalAttachment").show();
				
				$("#typeFile").hide();
				$("#typeLink").hide();
			}else if(type=="1"){
				$("#typeFile").show();
				
				$("#typeNormalContent").hide();
				$("#typeNormalAttachment").hide();
				$("#typeLink").hide();
			}else if(type=="2"){
				$("#typeLink").show();
				
				$("#typeNormalContent").hide();
				$("#typeNormalAttachment").hide();
				$("#typeFile").hide();
			}
		}
	}

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
		var title=document.getElementById("title").value;
		var author=document.getElementById("author").value;
		var channelId=document.getElementById("channelId").value;
		var content=CKEDITOR.instances.content.getData();
		var type=$('input[name="optionsRadios"]:checked').val();

		if(title==null||title==""){
			document.getElementById("error").innerHTML="资讯标题不能为空！";
			return false;
		}
		if(author==null||author==""){
			document.getElementById("error").innerHTML="作者不能为空！";
			return false;
		}
		if(channelId==null||channelId==""){
			document.getElementById("error").innerHTML="请选择频道！";
			return false;
		}
		if(type==null||type==""){
			document.getElementById("error").innerHTML="请选择资讯类别！";
			return false;
		}
		
		if(type=="0"){
			// normal
			if(content==null||content==""){
				document.getElementById("error").innerHTML="资讯内容不能为空！";
				return false;
			}
		}else if(type=="1"){
			// attachment
		}else if(type=="2"){
			// link
		}
		
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
		<form action="information?action=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
			<table cellpadding="5" width="100%">
				<tr>
					<td width="80px">
						<label>资讯标题：</label>
					</td>
					<td>
						<input type="text" id="title" name="title" class="input-xxlarge" value="${information.title }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>资讯作者：</label>
					</td>
					<td>
						<input type="text" id="author" name="author" value="${information.author }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>资讯频道：</label>
					</td>
					<td>
						<select id="channelId" name="channelId">
							<option value="">请选择资讯频道</option>
							<c:forEach var="tmpChannel" items="${channelList }">
								<option value="${tmpChannel.channelId }" ${tmpChannel.channelId==information.channelId?'selected':'' }>${tmpChannel.channelName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>资讯属性：</label>
					</td>
					<td>
						<label class="radio inline">
						  <input type="radio" name="optionsRadios" id="typeInfoNormal" value="0" onclick="checkTypeChange()" ${information.type==0?'checked':''}>
						  新闻类
						</label>
						<label class="radio inline">
						  <input type="radio" name="optionsRadios" id="typeInfoAttach" value="1" onclick="checkTypeChange()" ${information.type==1?'checked':''}>
						  档案类
						</label>
						<label class="radio inline">
						  <input type="radio" name="optionsRadios" id="typeInfoLink" value="2" onclick="checkTypeChange()" ${information.type==2?'checked':''}>
						  链接类
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>资讯类别：</label>
					</td>
					<td>
<!-- 						<label class="checkbox inline"> -->
<%-- 						  <input type="checkbox" id="isHead" name="isHead" value="1" ${information.isHead==1?'checked':'' }>头条 --%>
<!-- 						</label> -->
<!-- 						<label class="checkbox inline"> -->
<%-- 						  <input type="checkbox" id="isHot" name="isHot" value="1" ${information.isHot==1?'checked':'' }>热点 --%>
<!-- 						</label> -->

						<label class="checkbox inline">
						  <input type="checkbox" id="isHead" name="isHead" value="1" ${information.head?'checked':'' }>头条
						</label>
						<label class="checkbox inline">
						  <input type="checkbox" id="isHot" name="isHot" value="1" ${information.hot?'checked':'' }>热点
						</label>

					</td>
				</tr>
				
				<tr id="typeNormalContent">
					<td valign="top">
						<label>资讯内容：</label>
					</td>
					<td>
						<textarea class="ckeditor" id="content" name="content">${information.type==0?information.content:'' }</textarea>
					</td>
				</tr>
				
				<tr id="typeNormalAttachment" style="">
					<td>
						<label>附件：</label>
					</td>
					<td>
						<input type="hidden" id="attachmentTmpId" name="attachmentTmpId" value="-1"/>
						<ul class="nav nav-list" id="attachment">
						</ul>
						<button class="btn" type="button" onclick="addAttachment()" style="margin-left:15px">+</button>
					</td>
				</tr>
				
				<tr id="typeFile" style="display: none">
					<td>
						<label>文件：</label>
					</td>
					<td>
						<ul class="nav nav-list" id="attachment">
						  <li class="">
						  	<span class="add-on"><i class="icon-file"></i></span>
						  	<input type="file" id="attachment" name="attachment" />
						  </li>
						</ul>
						
<!-- 						<input type="file" id="picFile" name="picFile" /> -->
<%-- 					<input type="text" id="imageName" name="imageName" value="${information.imageName }"/> --%>
					</td>
				</tr>
				
				<tr id="typeLink" style="display: none">
					<td>
						<label>链接地址：</label>
					</td>
					<td>
						<input type="text" id="infoLink" name="infoLink" value="${information.type==2?information.content:'' }"  class="input-xxlarge"/>
					</td>
				</tr>
				
				<tr>
					<td>
					</td>
					<td>
						<font id="error" color="red">${error }</font>
					</td>
				</tr>
				
				<tr>
					<td>
						<input type="hidden" id="informationId" name="informationId" value="${information.informationId }"/>&nbsp;
					</td>
					<td>
						<input type="submit" class="btn btn-primary" value="保存资讯"/>&nbsp;&nbsp;
						<input type="button" class="btn btn-primary" value="返回" onclick="javascript:history.back()"/>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
</body>
</html>
<script>
	if('${information.type}'==0){
		$("#typeNormalContent").show();
		$("#typeNormalAttachment").show();
		
		$("#typeFile").hide();
		$("#typeLink").hide();
	}else if('${information.type}'==1){
		$("#typeFile").show();
		
		$("#typeNormalContent").hide();
		$("#typeNormalAttachment").hide();
		$("#typeLink").hide();
	}else if('${information.type}'==2){
		$("#typeLink").show();
		
		$("#typeNormalContent").hide();
		$("#typeNormalAttachment").hide();
		$("#typeFile").hide();
	}
</script>