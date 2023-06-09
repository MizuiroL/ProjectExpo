<%@page import="model.Exhibit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Today's exhibits</title>
</head>
<body>
	<h3>Product List</h3>
	<table border="1">
	<caption>List of currently booked events</caption>
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
	</table>
</body>
</html>