<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${tableString}
<img src="images/Black.png"> ${blackScore} <br>
<img src="images/White.png"> ${whiteScore} <br>
${message} <br>
<form:form action="/Rerversi">
	<input type="submit" value="New Game">
</form:form>
</body>
</html>