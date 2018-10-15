<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>设备表功能管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>设备表功能列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="facility" action="${ctx}/tb_facility/facility/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>设备区域：</span>
				<sys:treeselect id="area" name="area.id" value="${facility.area.id}" labelName="area.name" labelValue="${facility.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="false"/>
			<span>设备类型：</span>
				<form:select path="type"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('facility_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="tb_facility:facility:add">
				<table:addRow url="${ctx}/tb_facility/facility/form" title="设备表功能"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tb_facility:facility:edit">
			    <table:editRow url="${ctx}/tb_facility/facility/form" title="设备表功能" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tb_facility:facility:del">
				<table:delRow url="${ctx}/tb_facility/facility/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tb_facility:facility:import">
				<table:importExcel url="${ctx}/tb_facility/facility/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tb_facility:facility:export">
	       		<table:exportExcel url="${ctx}/tb_facility/facility/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<%--<th  class="sort-column remarks">备注信息</th>--%>
				<th  class="sort-column user.name">设备管理员</th>
				<th  class="sort-column number">设备编号</th>
				<th  class="sort-column name">设备名</th>
				<th  class="sort-column area.name">设备区域</th>

				<th  class="sort-column type">设备类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="facility">
			<tr>
				<td> <input type="checkbox" id="${facility.id}" class="i-checks"></td>
					<%--<td><a  href="#" onclick="openDialogView('查看设备表功能', '${ctx}/tb_facility/facility/form?id=${facility.id}','800px', '500px')">
                        ${facility.remarks}
                    </a></td>--%>
				<td>
						${facility.user.name}
				</td>
				<td>
					${facility.number}
				</td>
				<td>
					${facility.name}
				</td>
				<td>
					${facility.area.name}
				</td>

				<td>
					${fns:getDictLabel(facility.type, 'facility_type', '')}
				</td>
				<td>
					<shiro:hasPermission name="tb_facility:facility:view">
						<a href="#" onclick="openDialogView('查看设备表功能', '${ctx}/tb_facility/facility/form?id=${facility.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="tb_facility:facility:edit">
    					<a href="#" onclick="openDialog('修改设备表功能', '${ctx}/tb_facility/facility/form?id=${facility.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="tb_facility:facility:del">
						<a href="${ctx}/tb_facility/facility/delete?id=${facility.id}" onclick="return confirmx('确认要删除该设备表功能吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>