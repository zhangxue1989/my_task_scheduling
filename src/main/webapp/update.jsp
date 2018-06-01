<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>任务管理</title>
	<style type="text/css">
		table{text-align: center;}
	</style>
</head>
<body>
	<form action="/updateJob" method="get">
		<table border="1">
			<caption><h1>任务详情</h1></caption>
			<tr>
				<td>任务名称</td>
				<td>
					${jobInfo.jobName}
					<input name="jobName" type="hidden" value="${jobInfo.jobName}"/>
				</td>
			</tr>
			<tr>
				<td>任务分组</td>
				<td>${jobInfo.jobGroupName}</td>
			</tr>
			<tr>
				<td>触发器名称</td>
				<td>${jobInfo.triggerName}</td>
			</tr>
			<tr>
				<td>触发器分组</td>
				<td>${jobInfo.triggerGroupName}</td>
			</tr>
			<tr>
				<td>表达式</td>
				<td><input name="corn" value="${jobInfo.corn}"/></td>
			</tr>
			<tr>
				<td>任务类</td>
				<td>${jobInfo.jobClass}</td>
			</tr>
			<tr>
				<td>任务类名称</td>
				<td>${jobInfo.jobClassSimleName}</td>
			</tr>
			<tr>
				<td>参数</td>
				<td>
					<c:forEach var="para" items="${jobInfo.params}">
						${para.key } = ${para.value }<br />
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>任务状态</td>
				<td>${job.jobStatus}</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="提交" />
				</td>
			</tr>
		</table>
	</form>
	<h1>${requestScope.msg }</h1>
	<a href="http://www.pppet.net/" target="_black">corn生成与教程</a>
</body>
</html>




