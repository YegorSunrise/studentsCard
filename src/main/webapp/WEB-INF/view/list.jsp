<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<h3>Students list</h3>

<c:forEach items="${students}" var="student">
    <ul>
        <li>#${student.id} ${student.fullName} г.р.: ${student.birthday}</li>
        курс: ${student.course} группа: ${student.group}
        <label>предметы:</label>
        <c:forEach items="${student.subjects}" var="subj">
            <label>${subj.title}</label>
        </c:forEach>
    </ul>
</c:forEach>
</body>
</html>
    
