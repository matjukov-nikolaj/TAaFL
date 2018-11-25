<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: MarioSlim
  Date: 25.11.2018
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="tableStyles.jsp" %>
</head>
<body>
<form method="get">
    <input type="submit" value="Go back">
</form>
<input type="hidden" name="valuesTable">
<%
    Map<List<Integer>, List<List<List<Integer>>>> result
    = (Map<List<Integer>, List<List<List<Integer>>>>) request.getAttribute("result");
    List<List<Integer>> finalStates = (List<List<Integer>>) request.getAttribute("finalStates");
    out.print("<p> List of new final states.</p>");
    out.print("<table>");
    for (List<Integer> state: finalStates) {
        out.print("<tr>");
        String stateStr = state.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        out.print("<td><p>" + stateStr + "</p></td>");
        out.print("</tr>");
    }
    out.print("</table>");

    out.print("<p> Deterministic table.</p>");

    Set<List<Integer>> keySet = result.keySet();
    out.print("<table>");
    for (List<Integer> key: keySet) {
        List<List<Integer>> row = result.get(key).get(0);
        out.print("<tr>");
        String keyStr = key.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        out.print("<td><p>" + keyStr + "</p></td>");
        for (List<Integer> cell: row)  {
            String cellStr = cell.stream().map(Object::toString)
                    .collect(Collectors.joining(", "));
            out.print("<td><p>" + cellStr + "</p></td>");
        }
        out.print("</tr>");
    }
    out.print("</table>");
%>
<img src="http://localhost:8311/out.png">
</body>
</html>
