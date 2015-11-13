<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="reversi.game.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
	<img src="images/${sessionScope.board.getTurn()}.png"/>
<form:form>
${tableString}
<%-- <input type="submit" disabled="disabled" id="submit" value="Make Move" /> --%>
<input type="submit" id="submit" value="Make Move" name="makeMove"/>
<c:if test="${skippable}">
	<input type="submit" value="Skip Turn" name="skipTurn" />
</c:if>
</form:form>
</body>
</html>