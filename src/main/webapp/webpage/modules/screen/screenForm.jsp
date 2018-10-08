<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>屏幕控制管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="screen" action="${ctx}/screen/screen/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">屏幕说明：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">区域：</label></td>
					<td class="width-35">
						<sys:treeselect id="area" name="area.id" value="${screen.area.id}" labelName="area.name" labelValue="${screen.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>默认类型：</label></td>
					<td class="width-35">
						<form:select path="type" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('screen_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">图片或视频：</label></td>
					<td class="width-35">
						<form:hidden id="photoAndVideo" path="photoAndVideo" htmlEscape="false" maxlength="64" class="form-control"/>
						<sys:ckfinder input="photoAndVideo" type="files" uploadPath="/screen/screen" selectMultiple="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">文字：</label></td>
					<td class="width-35">
						<form:textarea path="article" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">背景图：</label></td>
					<td class="width-35">
						<form:hidden id="backgroundPhoto" path="backgroundPhoto" htmlEscape="false" maxlength="64" class="form-control"/>
						<sys:ckfinder input="backgroundPhoto" type="files" uploadPath="/screen/screen" selectMultiple="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">图标：</label></td>
					<td class="width-35">
						<form:hidden id="logo" path="logo" htmlEscape="false" maxlength="64" class="form-control"/>
						<sys:ckfinder input="logo" type="files" uploadPath="/screen/screen" selectMultiple="true"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>