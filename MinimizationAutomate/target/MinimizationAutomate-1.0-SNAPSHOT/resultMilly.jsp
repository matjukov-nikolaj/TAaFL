<%@ page import="java.util.ArrayList" %>
<%@ page import="com.taafl.model.Cell" %>
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
<%!
    public void printCells(Integer i, Integer j, JspWriter out, ArrayList<ArrayList<Cell>> result)
    {
        try {
            if (i == 0 && j == 0) {
                out.print("<td><p> </p></td>");
                return;
            }
            if (i == 0) {
                out.print("<td><p>S" + (j - 1) + "</p></td>");
            } else if (j == 0) {
                out.print("<td><p>x" + (i - 1) + "</p></td>");
            } else {
                String q = result.get(j - 1).get(i - 1).Q.toString();
                String y = result.get(j - 1).get(i - 1).y.toString();
                out.print("<td><p>" + q + " " + y + "</p</td>");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
%>
<%
    Integer Q = (Integer) request.getAttribute("Q");
    Integer x = (Integer) request.getAttribute("x");
    ArrayList<ArrayList<Cell>> result = (ArrayList<ArrayList<Cell>>) request.getAttribute("result");
    out.print("<table>");
    for (int i = 0; i <= x; ++i) {
        out.print("<tr>");
        for (int j = 0; j <= result.size(); ++j) {
            printCells(i, j, out, result);
        }
        out.print("</tr>");
    }
    out.print("</table>");
%>
<img src="http://localhost:8311/out.png">
</body>
</html>
