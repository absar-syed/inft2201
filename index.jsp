<%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="./includes/header.jsp"%>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="node_modules/@fortawesome/fontawesome-free/css/all.css">
    <link rel="stylesheet" href="content/style.css">
</head>


<body>
    <div class="container m-5 text-white">
        <h1>WELCOME</h1>
        <p>Welcome to the User's home page click the button below to login!</p>
        <a class="no-decor" href="jsp/login.jsp">Login!</a>
    </div>


    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
</body>


<%@include file="./includes/footer.jsp"%>
</html>
