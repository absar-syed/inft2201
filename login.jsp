<%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="./includes/header.jsp"%>
<%@ page import = "syeda.*" %>
<%   String errorMessage = (String)session.getAttribute("errors");
    String login = (String)session.getAttribute("login");
    if(errorMessage == null)
        errorMessage="";
    if(login == null)
        login = "";
%>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="node_modules/@fortawesome/fontawesome-free/css/all.css">
    <link rel="stylesheet" href="content/style.css">
</head>


<body>
<div class="container m-5 text-white">
    <h1>LOGIN</h1>
    <div class="alert alert-danger" role="alert">
        <%= errorMessage %>
    </div>
    <form>
        <div class="mb-3">
            <label for="userid" class="form-label">User ID</label>
            <input type="email" class="form-control" id="userid">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

</div>


<script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
</body>


<%@include file="./includes/footer.jsp"%>
</html>
