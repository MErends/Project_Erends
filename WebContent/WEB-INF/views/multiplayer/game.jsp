<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="reversi.game.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<!DOCTYPE html>
<html>
<head>

<style>
td.clickable {cursor: pointer}
</style>

<meta charset="UTF-8">
<title>Current board</title>

<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script>
var playerColor = "${playerColor}";

function getTurn() {
	console.log("getting Turn");
	$.get("/Reversi/api/turn/${boardID}", function(data) {
		if (data == playerColor) {
			location.reload(true);
		}
	});
}

$(document).ready(function() {
	$('td.clickable')
		.click(function() {
			var id = $(this).attr("id");
			$('img.clickable').attr("src", "/Reversi/images/None.png");
			$('img[id='+ id + ']').attr("src", "/Reversi/images/Click.png");
			$('#placeID').attr("value", id)
			$('#submit').prop("disabled", false);
		})
		.mouseover(function() {
			var id = $(this).attr("id");
			$('img[id='+ id + ']').attr("src", "/Reversi/images/Click.png");
		})
		.mouseout(function(){
			var id = $(this).attr("id");
			var clicked = $('#placeID').attr("value");
			if (clicked != id)
				$('img[id='+ id + ']').attr("src", "/Reversi/images/None.png");
		});
	if ("${playerColor}" != "${turn}") {
	setInterval(getTurn, 2000);
	}
});
</script>

<body>
	Next turn:
	<img src="/Reversi/images/${turn}.png"/>
	${tableString}
	<form:form>
		<input type="hidden" id="placeID" name="placeID" value="${bestMove}">
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