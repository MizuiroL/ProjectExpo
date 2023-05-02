<%@page import="model.Exhibit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Today's exhibits</title>
</head>
<body>
	<h3>Exhibit List</h3>
	<table border="1">
		<tr>
			<th>exhibitId</th>
			<th>exhibitName</th>
			<th>exhibitStartDate</th>
			<th>exhibitStartTime</th>
			<th>exhibitEndDate</th>
			<th>exhibitEndTime</th>
		</tr>
		
		<c:forEach var="exhibit" items="${exhibitList}">
			<tr>
				<td>${exhibit.exhibitId}</td>
				<td>${exhibit.exhibitName}</td>
				<td>${exhibit.exhibitStartDate}</td>
				<td>${exhibit.exhibitStartTime}</td>
				<td>${exhibit.exhibitEndDate}</td>
				<td>${exhibit.exhibitEndTime}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>