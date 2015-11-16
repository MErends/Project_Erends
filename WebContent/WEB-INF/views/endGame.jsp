<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<meta charset="UTF-8">
<title>Game Over!</title>
<script>
$(document).ready(function() {
	alert("${message}");
});
</script>
</head>
<body>
${tableString}
<img src="images/Black.png"> ${blackScore} <br>
<img src="images/White.png"> ${whiteScore} <br>
${message} <br>
<form:form action="/Reversi">
	<input type="submit" value="New Game">
</form:form>
</body>
</html>