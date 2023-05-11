<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<link rel="stylesheet" href="mystyle.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Serif:wght@700&display=swap"
	rel="stylesheet">
</head>
<body>
<h1>Welcome, visitor</h1>
	<br>
	<br>
	<br>
	<div class="flex_options">
		<div class="flex_card index_visitor view_exhibits_option">
			<h2>
				<a href="visitor/CurrentExhibitsServlet">What can I visit?</a>
			</h2>
		</div>
		<div class="flex_card index_visitor view_events_option">
			<h2>
				<a href="visitor/FutureEventsServlet">What events are available?</a>
			</h2>
		</div>
		<div class="flex_card index_visitor purchased_events_option">
			<h2>
				<a href="visitor/BookedEventsServlet">What events did I book?</a>
			</h2>
		</div>
	</div>
</body>
</html>