<%@page import="model.Exhibit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="mystyle.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Serif:wght@700&display=swap"
	rel="stylesheet">
<title>Future events</title>
</head>
<body>
	<h3>Future Events</h3>
	<!-- <table border="1">
		<tr>
			<th>eventId</th>
			<th>eventName</th>
			<th>eventStartDate</th>
			<th>eventStartTime</th>
			<th>eventEndDate</th>
			<th>eventEndTime</th>
			<th>eventAvailableSeats</th>
			<th>eventTotalSeats</th>
		</tr>
		
		<c:forEach var="event" items="${eventList}">
			<tr>
				<td>${event.exhibitId}</td>
				<td>${event.exhibitName}</td>
				<td>${event.exhibitStartDate}</td>
				<td>${event.exhibitStartTime}</td>
				<td>${event.exhibitEndDate}</td>
				<td>${event.exhibitEndTime}</td>
				<td>${event.eventAvailableSeats}</td>
				<td>${event.eventTotalSeats}</td>
			</tr>
		</c:forEach>
	</table-->
	<div class="flex_options">
	<c:forEach var="event" items="${eventList}">
		<%@ include file="event.jsp" %>
	</c:forEach>
	</div>
</body>
</html>