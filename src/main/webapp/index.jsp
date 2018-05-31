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
	<table border="1">
		<caption><h1>表达式任务</h1></caption>
		<tr>
			<td>序号</td>
			<td>任务名称</td>
			<td>任务分组</td>
			<td>触发器名称</td>
			<td>触发器分组</td>
			<td>表达式</td>
			<td>任务类</td>
			<td>任务类名称</td>
			<td>下次任务执行时间</td>
			<td>参数</td>
			<td>任务状态</td>
			<td>操作</td>
		</tr>
		<c:forEach var="job" items="${allJob }" varStatus="i">
			<tr>
				<td>${i.count}</td>
				<td>${job.jobName}</td>
				<td>${job.jobGroupName}</td>
				<td>${job.triggerName}</td>
				<td>${job.triggerGroupName}</td>
				<td>${job.corn}</td>
				<td>${job.jobClass}</td>
				<td>${job.jobClassSimleName}</td>
				<td>
					<fmt:formatDate value="${job.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>
					<c:forEach var="para" items="${job.params}">
						${para.key } = ${para.value }<br/>
					</c:forEach>
				</td>
				<td>${job.jobStatus}</td>
				<td>
					<a href="/getJobInfo?jobName=${job.jobName }">修改</a>&nbsp;
					<a href="/removeJob?jobName=${job.jobName }">删除</a>&nbsp;
					<a href="/stopJob?jobName=${job.jobName }">停止</a>&nbsp;
					<a href="/startJob?jobName=${job.jobName }">启动</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<a href="/shutdown">全部暂停</a>&nbsp;
</body>
<h1>${requestScope.msg }</h1>
</html>




