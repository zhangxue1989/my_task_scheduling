<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>遍历集合 </h1>
<table>
	<c:forEach var="person" items="${restList }">
		<tr>
			<td>${person.id}</td>
			<td>${person.name}</td>
		</tr>
	</c:forEach>
</table>

<h1>遍历map</h1>
<table>
	<c:forEach var="person" items="${restMap }">
		<tr>
			<td>key = ${person.key}</td>
			<td>${person.value.id}</td>
			<td>${person.value.name}</td>
		</tr>
	</c:forEach>
</table>

<h1>格式化时间</h1>
<fmt:formatDate value="${cur_time }" pattern="yyyy-MM-dd"/><br/>
<fmt:formatDate value="${cur_time }" pattern="yyyy-MM-dd HH:mm:dd"/><br/>
<fmt:formatDate value="${cur_time }" pattern=" HH:mm:dd"/><br/>
