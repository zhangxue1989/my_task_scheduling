<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>遍历集合 </h1>
<table border="1">
	<c:forEach var="job" items="${allJob }">
		<tr>
			<td>${job.jobName}</td>
			<td>${job.jobGroupName}</td>
			<td>${job.triggerName}</td>
			<td>${job.triggerGroupName}</td>
			<td>${job.corn}</td>
			<td>${job.jobClass}</td>
			<td>${job.jobClassSimleName}</td>
			<td>${job.nextFireTime}</td>
			<td>${job.params}</td>
			<td>${job.jobStatus}</td>
		</tr>
	</c:forEach>
</table>


