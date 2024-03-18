<%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<%  String errorMessage = (String)session.getAttribute("errors");
//    Student aStudent = (Student)session.getAttribute("student");

%>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="node_modules/@fortawesome/fontawesome-free/css/all.css">
    <link rel="stylesheet" href="content/style.css">
</head>


<body>
<div class="container text-white">


    <form class="m-5" name="Login" method="post" action="./Login">

        <h1>LOGIN</h1>
        <% if(errorMessage != null) {%>
        <div class="alert alert-danger">
            <%= errorMessage %>
        </div>
        <%}%>

        <div >
            <label for="userid" class="form-label">User ID</label>
            <input  type="text" class="form-control" id="userid" name="userid">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

</div>


<script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
</body>


<%@include file="footer.jsp"%>
</html>
