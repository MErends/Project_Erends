<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="reversi.game.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Current board</title>
</head>
<body>
<table>
<%
Board gameBoard = (Board) session.getAttribute("board");
for(int x = 0; x != 8; ++x) {
	%><tr><%
	for(int y = 0; y != 8; ++y) {
		%><td>
		<img src="/images/<%=gameBoard.getColorAt(x, y) %>.png" />
		</td><%
	}
	%></tr><%
}%>
</table>
</body>
</html>