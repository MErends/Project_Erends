<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="reversi.game.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
Color turn = (Color) session.getAttribute("ColorHasTurn");
int[][] potentialScore = (int[][]) session.getAttribute("potential");
for(int x = 0; x != 8; ++x) {
	%><tr><%
	for(int y = 0; y != 8; ++y) {
		boolean potential = 0 == gameBoard.potentialScoreFor(x, y, turn);
		int field = 10 * x + y;
		%>
		<c:choose>
			<c:when test="${potential}">
				<td align="center" style="background-image:url(<%= request.getContextPath() %>/images/None.png);background-repeat:no-repeat;">
					<input type="radio" name="<%= field %>" />
				</td>
			</c:when>
			<c:otherwise>
				<td><img src="<%= request.getContextPath() %>/images/<%= gameBoard.getColorAt(x, y) %>.png" /></td>
			</c:otherwise>
		</c:choose><%
	}
	%></tr><%
}%>
</table>
</body>
</html>