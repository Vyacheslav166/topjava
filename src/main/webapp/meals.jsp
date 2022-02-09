<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<c:set var="meals" scope="request" value="${listMeals}"/>
<table border="1">
    <tr>
        <th><%="Date"%>
        </th>
        <th><%="Description"%>
        </th>
        <th><%="Calories"%>
        </th>
    </tr>
    <c:forEach items="${meals}" var="m" >
        <c:set var="color" value="${m.excess ? 'red' : 'green'}"/>
        <tr>
            <td style="color: ${color};">${m.dateTime}"</td>
            <td style="color: ${color};">${m.description}</td>
            <td style="color: ${color};">${m.calories}</td>
            <td style="color: ${color};">${m.excess}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>