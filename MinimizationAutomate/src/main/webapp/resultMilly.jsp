<%@ page import="java.util.ArrayList" %>
<%@ page import="com.taafl.model.Cell" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        BODY {
            background: white; /* Цвет фона веб-страницы */
        }
        TABLE {
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
            border: 2px solid white; /* Прячем рамку вокруг таблицы */
        }
        TD, TH {
            width: 40px;
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
            text-align: center; /* Выравнивание по левому краю */
        }
    </style>
</head>
<body>
<form method="get">
    <input type="submit" value="Go back">
</form>
<form id="form" method="post">
    <input type="hidden" name="valuesTable">
        <%
        Integer Q = (Integer) request.getAttribute("Q");
        Integer x = (Integer) request.getAttribute("x");
        ArrayList<ArrayList<Cell>> result = (ArrayList<ArrayList<Cell>>)request.getAttribute("result");
        out.print("<table>");
        for (int i = 0; i <= x; ++i) {
            out.print("<tr>");
            for (int j = 0; j <= result.size(); ++j) {
                if (i == 0 && j == 0) {
                    out.print("<td><p> </p></td>");
                    continue;
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
            }
            out.print("</tr>");
        }
        out.print("</table>");
    %>
</form>

</body>
</html>
