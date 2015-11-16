<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log In</title>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script>
$(document).ready(function(){
	$('input.name')
		.focus(function() {
			if ($(this).attr("value") == "Enter your name")
				$(this).attr("value", "");
		})
		.blur(function() {
			if ($(this).attr("value") == "")
				$(this).attr("value", "Enter your name");
		});
});
</script>
</head>
<body>
<form:form>
<input type="text" class="name" name="name" value="Enter your name">
<input type="submit" value="Log in">
</form:form>
</body>
</html>