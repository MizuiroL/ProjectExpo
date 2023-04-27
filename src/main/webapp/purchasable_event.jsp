<%@page import="model.Event"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Event</title>
</head>
<body>
	<%
	Event event = (Event) session.getAttribute("event");
	%>
	<%@ include file="event.jsp"%>

	<br>
	<br>
	<!-- TODO check if user hasn't booked this event yet
	Or if the event is no longer purchasable -->
	<form action="BookEventServlet" method="POST">
		<button class="visitor_login_input" type="submit">Book event</button>
	</form>
</body>
</html>