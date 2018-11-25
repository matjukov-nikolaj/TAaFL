<%--
  Created by IntelliJ IDEA.
  User: MarioSlim
  Date: 18.11.2018
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="form" method="post">
    <input type="hidden" name="determineTable">
    <%!
        public void printCells(Integer i, Integer j, JspWriter out) {
            try {
                if (i == 0 && j == 0) {
                    out.print("<td><p> </p></td>");
                    return;
                }
                if (i == 0) {
                    out.print("<td><p>x" + (j - 1) + "</p></td>");
                } else if (j == 0) {
                    out.print("<td><p>s" + (i - 1) + "</p></td>");
                } else {
                    String name = "x" + (j - 1) + "s" + (i - 1);
                    out.print("<td><input type=text name=\"" + name + "\" + " + "style=\"width: 40px;\"></td>");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    %>
    <%
        Integer x = (Integer) request.getAttribute("x");
        Integer k = (Integer) request.getAttribute("k");
        Integer z = (Integer) request.getAttribute("z");
        out.print("<p>Введите массив финальных состояний</p>");
        out.print("<table>");
        for (int i = 0; i < k; ++i) {
            out.print("<tr>");
            String name = "K" + i;
            out.print("<td><input type=text name=\"" + name + "\" + " + "style=\"width: 40px;\"></tr>");
            out.print("</tr>");
        }
        out.print("</table>");
        out.print("<table>");
        for (int i = 0; i <= z; ++i) {
            out.print("<tr>");
            for (int j = 0; j <= x; ++j) {
                printCells(i, j, out);
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
