        <%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="header.jsp"%>
<%


    String studentID = (String) session.getAttribute("validStudentID");
    String password = (String)session.getAttribute("validPassword");
    String firstName = (String)session.getAttribute("validFirstName");
    String lastName = (String)session.getAttribute("validLastName");
    String emailAddress = (String)session.getAttribute("validEmailAddress");
    String programCode = (String)session.getAttribute("validProgramCode");
    String programDescription = (String)session.getAttribute("validProgramDescription");
    String year = (String) session.getAttribute("validYear");



    if (session.getAttribute("student") != null) {
        response.sendRedirect("./dashboard.jsp");
    }

    if (studentID == null) {
        studentID = "";
    }

    if (password == null) {
        password = "";
    }

    if (firstName == null) {
        firstName = "";
    }

    if (lastName == null) {
        lastName = "";
    }

    if (emailAddress == null) {
        emailAddress = "";
    }

    if (programCode == null) {
        programCode = "";
    }

    if (programDescription == null) {
        programDescription = "";
    }

    if (year == null) {
        year = "";
    }

%>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="node_modules/@fortawesome/fontawesome-free/css/all.css">
    <link rel="stylesheet" href="content/style.css">
</head>


<body>
<div class="container text-white">


    <form class="m-5" name="Register" method="post" action="./Register">

        <h1>Register</h1>
        <% if (session.getAttribute("errors") != null) {%>
        <div class="alert alert-danger">
            <%=session.getAttribute("errors")%>
        </div>
        <%}
        session.removeAttribute("errors");
        %>

        <div class="mb-3">
            <label for="userid" class="form-label">User ID</label>
            <input  type="text" class="form-control" id="userid" name="userid" value="<%= studentID %>">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" value="<%= password %>">
        </div>
        <div class="mb-3">
            <label for="firstname" class="form-label">First Name</label>
            <input type="text" class="form-control" id="firstname" name="firstname" value="<%= firstName %>">
        </div>
        <div class="mb-3">
            <label for="lastname" class="form-label">Last Name</label>
            <input type="text" class="form-control" id="lastname" name="lastname" value="<%= lastName %>">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="<%= emailAddress %>">
        </div>
        <div class="mb-3">
            <label for="programcode" class="form-label">Program Code</label>
            <input type="text" class="form-control" id="programcode" name="programcode" value="<%= programCode %>">
        </div>
        <div class="mb-3">
            <label for="programdescription" class="form-label">Program Description</label>
            <input type="text" class="form-control" id="programdescription" name="programdescription" value="<%= programDescription %>">
        </div>
        <div class="mb-3">
            <label for="year" class="form-label">Year</label>
            <input type="text" class="form-control" id="year" name="year" value="<%= year %>">
        </div>
        <button type="submit" class="btn btn-primary mb-5">Submit</button>
    </form>

</div>


<script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
</body>


<%@include file="footer.jsp"%>
</html>
