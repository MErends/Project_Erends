<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Its the lobby yo!</title>
</head>
<body>
<h1>Welcome ${sessionScope.name}!</h1>
<a href="findOpponent">Click here to find an opponent</a><br>
<a href="logout">Click here to log out</a>
</body>
</html>