<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="reversi.game.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Current board</title>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script>
$(document).ready(function(){
    $('input:radio[name="placeID"]').click(function() {
        $("#submit").removeAttr("disabled");
    });
});
</script>
</head>
<body>
Next turn:
	<img src="images/${sessionScope.turn}.png"/>
<form:form>
${tableString}
<input type="submit" disabled="disabled" id="submit" />
</form:form>
</body>
</html>