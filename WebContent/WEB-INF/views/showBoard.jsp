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
<style>
    input[type=radio] {
	 border: 0px;
   	 width: 100%;
   	 height: 2em;
	}
</style>
</head>

<body>
Next turn:
	<img src="images/${sessionScope.board.getTurn()}.png"/>
<form:form>
${tableString}
<c:choose>
	<c:when test="${CPU}">
	<input type="submit" id="submit" value="Make Move" name="makeMove">
	</c:when>
	<c:otherwise>
	<input type="submit" id="submit" value="Make Move" name="makeMove" disabled="disabled">
	</c:otherwise>
</c:choose>
<c:if test="${skippable}">
	<input type="submit" value="Skip Turn" name="skipTurn" />
</c:if>
</form:form>
</body>
</html>