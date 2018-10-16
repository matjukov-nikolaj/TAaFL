<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Table</title>
</head>
<body>
<form method="get">
    <input type="submit" value="Go back">
</form>
<form id="form" method="post">
    <input type="hidden" name="millyTable">
    <%
        Integer Q = (Integer) request.getAttribute("Q");
        Integer x = (Integer) request.getAttribute("x");
        out.print("<table>");
        for (int i = 0; i <= x; ++i) {
            out.print("<tr>");
            for (int j = 0; j <= Q; ++j) {
                if (i == 0 && j == 0) {
                    out.print("<td><p> </p></td>");
                    continue;
                }
                if (i == 0) {
                    out.print("<td><p>Q" + (j - 1) + "</p></td>");
                } else if (j == 0) {
                    out.print("<td><p>x" + (i - 1) + "</p></td>");
                } else {
                    String name = "Q" + (j - 1) + "x" + (i - 1);
                    out.print("<td><input type=text name=\"" + name + "\" + " + "style=\"width: 40px;\"></td>");
                }
            }
            out.print("</tr>");
        }
        out.print("</table>");
    %>
    <p></p>
    <input type="submit" value="Submit">
</form>
</body>
</html>
