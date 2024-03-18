<%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<%@ page import="static syeda.User.DF" %>
<%
    Student aStudent = (Student) session.getAttribute("student");

    String FullName = aStudent.getFirstName() + " " + aStudent.getLastName();
    long StudentID = aStudent.getId();
    String Email = aStudent.getEmailAddress();
    String ProgramCode = aStudent.getProgramCode();
    String ProgramDescription = aStudent.getProgramDescription();
    int Year = aStudent.getYear();
    String Enrol = DF.format(aStudent.getEnrolDate()) ;
    String LastAccess = DF.format(aStudent.getLastAccess()) ;

%>

<html>
<head>
    <title>DashBoard</title>
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="node_modules/@fortawesome/fontawesome-free/css/all.css">
    <link rel="stylesheet" href="content/style.css">
</head>


<body>


    <div class="card m-5 bg-dark text-white">

        <h5 class="card-header">STUDENT DASHBOARD</h5>

        <div class="card-body">
            <p class="card-text">Full Name: <%= FullName %></p>
            <p class="card-text">Student Number: <%= StudentID%></p>
            <p class="card-text">Email: <%= Email%></p>
            <p class="card-text">Program Code: <%= ProgramCode%></p>
            <p class="card-text">Program Description: <%= ProgramDescription%></p>
            <p class="card-text">Year: <%= Year%></p>
            <p class="card-text">Enrol Date: <%= Enrol%></p>
            <p class="card-text">Last Access: <%= LastAccess%></p>

        </div>
    </div>

    <div class="m-5">
        <form name="Logout" method="get" action="./Logout">
            <button type="submit" class="btn btn-light" >Logout</button>
        </form>
    </div>


    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>

</body>


<%@include file="footer.jsp"%>
</html>
