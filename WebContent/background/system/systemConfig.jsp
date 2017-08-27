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
	function onSystemTypeChange(systemType,systemDesc,systemUrl){
		 var obj = document.getElementById(systemType);
		 var index = obj.selectedIndex; // 选中索引
		 var text = obj.options[index].text; // 选中文本
		 var value = obj.options[index].value; // 选中值

		 var txtDesc = document.getElementById(systemDesc);
		 var txtUrl = document.getElementById(systemUrl);
		 if(value==-1){
			 txtDesc.readOnly=true;
			 txtUrl.readOnly=true;
		 }else{
			 txtDesc.readOnly=false;
			 txtUrl.readOnly=false;
		 }
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
				<tr>
					<td width="80px" valign="top">
						<label>Logo 配置：</label>
					</td>
					<td>
<!-- 						<ul class="nav nav-list" id="attachment"> -->
<!-- 						  <li class=""> -->
<!-- 						  	<span class="add-on"><i class="icon-file"></i></span> -->
<!-- 						  	<input type="file" id="logoImage" name="logoImage" value=""></input> -->
<!-- 						  </li> -->
<!-- 						</ul> -->

						<input type="hidden" id="configLogoId" name="configLogoId" value=""/>&nbsp;
						<input type="file" id="fileLogoImage" name="fileLogoImage" onchange="xmTanUploadImg(this)" accept="image/*"/>
            			<img id="imgLogo" name="imgLogo" src="config?action=configLogo"/>
<!--             			<img id="imgPreLogo" name="imgPreLogo"/> -->
<!--            				<div id="divPreLogo" name="divPreLogo"></div> -->
					</td>
				</tr>
				
				<tr id="trPreLogo" style="display: none">
					<td width="80px" valign="top">
						<label>&nbsp;&nbsp;&nbsp;&nbsp; 预览：</label>
					</td>
					<td>
            			<img id="imgPreLogo" name="imgPreLogo"/>
           				<div id="divPreLogo" name="divPreLogo"></div>
					</td>
				</tr>
				
				<tr>
					<td valign="top">
						<label>系统1：</label>
					</td>
					<td>
						<table cellpadding="0" width="100%">
							<tr>
								<td>
									<input type="hidden" id="systemId1" name="systemId1" value="${systemConfigs.customSystem1.configId}"/>
									<select id="systemType1" name="systemType1" onchange="onSystemTypeChange('systemType1','systemDesc1','systemUrl1')">
										<option value="-1" ${systemConfigs.customSystem1.options==-1?'selected':''}>不启用</option>
										<option value="0" ${systemConfigs.customSystem1.options==0?'selected':''}>启用：CFS</option>
										<option value="1" ${systemConfigs.customSystem1.options==1?'selected':''}>启用：HFM</option>
										<option value="1" ${systemConfigs.customSystem1.options==2?'selected':''}>启用：Planning</option>
									</select>
								</td>
								<td>
									<input type="text" id="systemDesc1" name="systemDesc1" class="input-xlarge" value="${systemConfigs.customSystem1.description}" placeholder="系统名称" ${systemConfigs.customSystem1.options==-1?'readonly':''}/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input width="100%" type="text" id="systemUrl1" name="systemUrl1" class="input-xxlarge" value="${systemConfigs.customSystem1.content}" placeholder="系统URL" ${systemConfigs.customSystem1.options==-1?'readonly':''}/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<label>系统2：</label>
					</td>
					<td>
						<table cellpadding="0" width="100%">
							<tr>
								<td>
									<input type="hidden" id="systemId2" name="systemId2" value="${systemConfigs.customSystem2.configId}"/>
									<select  id="systemType2" name="systemType2" onchange="onSystemTypeChange('systemType2','systemDesc2','systemUrl2')">
										<option value="-1" ${systemConfigs.customSystem2.options==-1?'selected':''}>不启用</option>
										<option value="0" ${systemConfigs.customSystem2.options==0?'selected':''}>启用：CFS</option>
										<option value="1" ${systemConfigs.customSystem2.options==1?'selected':''}>启用：HFM</option>
										<option value="1" ${systemConfigs.customSystem2.options==2?'selected':''}>启用：Planning</option>
									</select>
								</td>
								<td>
									<input type="text" id="systemDesc2" name="systemDesc2" class="input-xlarge" value="${systemConfigs.customSystem2.description}" placeholder="系统名称" ${systemConfigs.customSystem2.options==-1?'readonly':''}/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input width="100%" type="text" id="systemUrl2" name="systemUrl2" class="input-xxlarge" value="${systemConfigs.customSystem2.content}" placeholder="系统URL" ${systemConfigs.customSystem2.options==-1?'readonly':''}/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<label>系统3：</label>
					</td>
					<td>
						<table cellpadding="0" width="100%">
							<tr>
								<td>
									<input type="hidden" id="systemId3" name="systemId3" value="${systemConfigs.customSystem3.configId}"/>
									<select  id="systemType3" name="systemType3" onchange="onSystemTypeChange('systemType3','systemDesc3','systemUrl3')">
										<option value="-1" ${systemConfigs.customSystem3.options==-1?'selected':''}>不启用</option>
										<option value="0" ${systemConfigs.customSystem3.options==0?'selected':''}>启用：CFS</option>
										<option value="1" ${systemConfigs.customSystem3.options==1?'selected':''}>启用：HFM</option>
										<option value="1" ${systemConfigs.customSystem3.options==2?'selected':''}>启用：Planning</option>
									</select>
								</td>
								<td>
									<input type="text" id="systemDesc3" name="systemDesc3" class="input-xlarge" value="${systemConfigs.customSystem3.description}" placeholder="系统名称" ${systemConfigs.customSystem3.options==-1?'readonly':''}/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input width="100%" type="text" id="systemUrl3" name="systemUrl3" class="input-xxlarge" value="${systemConfigs.customSystem3.content}" placeholder="系统URL" ${systemConfigs.customSystem3.options==-1?'readonly':''}/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<label>系统4：</label>
					</td>
					<td>
						<table cellpadding="0" width="100%">
							<tr>
								<td>
									<input type="hidden" id="systemId4" name="systemId4" value="${systemConfigs.customSystem4.configId}"/>
									<select  id="systemType4" name="systemType4" onchange="onSystemTypeChange('systemType4','systemDesc4','systemUrl4')">
										<option value="-1" ${systemConfigs.customSystem4.options==-1?'selected':''}>不启用</option>
										<option value="0" ${systemConfigs.customSystem4.options==0?'selected':''}>启用：CFS</option>
										<option value="1" ${systemConfigs.customSystem4.options==1?'selected':''}>启用：HFM</option>
										<option value="2" ${systemConfigs.customSystem4.options==2?'selected':''}>启用：Planning</option>
									</select>
								</td>
								<td>
									<input type="text" id="systemDesc4" name="systemDesc4" class="input-xlarge" value="${systemConfigs.customSystem4.description}" placeholder="系统名称" ${systemConfigs.customSystem4.options==-1?'readonly':''}/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input width="100%" type="text" id="systemUrl4" name="systemUrl4" class="input-xxlarge" value="${systemConfigs.customSystem4.content}" placeholder="系统URL" ${systemConfigs.customSystem4.options==-1?'readonly':''}/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<label>服务与支持：</label>
					</td>
					<td>
						<input type="hidden" id="serviceSupportId" name="serviceSupportId" value="${systemConfigs.serviceSupport.configId}"/>
						<textarea class="ckeditor" id=serviceSupport name="serviceSupport">${systemConfigs.serviceSupport.content}</textarea>
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
    <script type="text/javascript">            
            //判断浏览器是否支持FileReader接口
            if (typeof FileReader == 'undefined') {
                document.getElementById("divPreLogo").innerHTML = "<h3>当前浏览器不支持图片预览接口</h3>";
                // 使选择控件不可操作
                // document.getElementById("fileLogoImage").setAttribute("disabled", "disabled");
            }

            //选择图片，马上预览
            function xmTanUploadImg(obj) {
            	 //var preLogo = document.getElementById("trPreLogo");
            	
            	 $("#trPreLogo").show();
            	 
                var file = obj.files[0];
                
                console.log(obj);console.log(file);
                console.log("file.size = " + file.size);  //file.size 单位为byte

                var reader = new FileReader();

                //读取文件过程方法
                reader.onloadstart = function (e) {
                    console.log("开始读取....");
                }
                reader.onprogress = function (e) {
                    console.log("正在读取中....");
                }
                reader.onabort = function (e) {
                    console.log("中断读取....");
                }
                reader.onerror = function (e) {
                    console.log("读取异常....");
                }
                reader.onload = function (e) {
                    console.log("成功读取....");

                    var img = document.getElementById("imgPreLogo");
                    img.src = e.target.result;
                    //或者 img.src = this.result;  //e.target == this
                }

                reader.readAsDataURL(file)
            }
        </script>
</body>
</html>