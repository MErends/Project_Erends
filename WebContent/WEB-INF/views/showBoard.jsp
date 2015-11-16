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
$(document).ready(function() {
	$('td.clickable').click(function() {
		var id = this.getAttribute("id");
		$('img.clickable').attr("src", "images/None.png");
		$('img[id='+ id + ']').attr("src", "images/Click.png");
		$('#submit').prop("disabled", false);
	});
});
</script>
<body>
Next turn:
	<img src="images/${sessionScope.board.getTurn()}.png"/>

${tableString}
<form:form>
<input type="hidden" name="placeID" value="">
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