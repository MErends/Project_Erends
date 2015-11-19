<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Waiting Room</title>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script>
var myID = ${playerID};
function getWaitingID() {
	$.get("/Reversi/api/waiter/", function(data) {

		if (data != myID) {
			window.location.href = "<c:url value="/multiplayer/game"/>";
		}
		//setTimeout(getWaitingID(), 2000);
	});
}

$(document).ready(function() {
	setInterval(getWaitingID, 2000);
});

</script>
</head>
<body>
You are now in the waiting room. Please wait until an opponent appears! This page will refresh automatically. Eventually...
</body>
</html>