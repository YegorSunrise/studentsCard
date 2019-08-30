<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Receipt</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>


<form class="add-page" id="newStudent" action="/add" method="post">
    <div class="card border-primary mb-3" style="max-width: 35rem;">
        <div class="card-header">New student card</div>
        <div class="form-group">
            <label for="fullName">Company name:</label>
            <input class="form-control" id="fullName" name="fullName" placeholder="... ... ..."/>
            <p></p>
            <label for="birthday">date:</label>
            <input class="form-control" id="birthday" name="birthday" placeholder="dd-MM-yyyy"/>
            <p></p>
            <label for="group">date:</label>
            <input class="form-control" id="group" name="group" placeholder="№ group"/>
            <p></p>
            <label for="course">date:</label>
            <input class="form-control" id="course" name="course" placeholder="№ course"/>
            <p></p>
            <label for="subj">subjects:</label>
            <c:forEach items="${subjects}" var="subject">
            <input type="checkbox" name="subjects" value="${subject}" id="subj">${subject.title}
            </c:forEach>
        </div>
    </div>
    <input type="submit" value="Add Student">
</form>

</body>
</html>
