        <%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<%@ page import="static syeda.User.DF" %>
<%@ page import="syeda.Student" %>
<%@ page import="syeda.Faculty" %>
<%
    //initialize variables
    boolean studentLogin = false;
    boolean facultyLogin = false;
    String FullName = null;
    long ID = 0;
    String Email = null;
    String Enrol = null;
    String LastAccess = null;
    String ProgramCode = null;
    String ProgramDescription = null;
    int Year = 0;
    String schoolCode = null;
    String schoolDescription = null;
    String office = null;
    int extension = 0;


    if (session.getAttribute("student") != null)
    {

        Student aStudent = (Student) session.getAttribute("student");

        FullName = aStudent.getFirstName() + " " + aStudent.getLastName();
        ID = aStudent.getId();
        Email = aStudent.getEmailAddress();
        ProgramCode = aStudent.getProgramCode();
        ProgramDescription = aStudent.getProgramDescription();
        Year = aStudent.getYear();
        Enrol = DF.format(aStudent.getEnrolDate());
        LastAccess = DF.format(aStudent.getLastAccess());

        studentLogin = true;
    }
    else if (session.getAttribute("faculty") != null)
    {
        Faculty aFaculty = (Faculty) session.getAttribute("faculty");

        FullName = aFaculty.getFirstName() + " " + aFaculty.getLastName();
        ID = aFaculty.getId();
        Email = aFaculty.getEmailAddress();
        schoolCode = aFaculty.getSchoolCode();
        schoolDescription = aFaculty.getSchoolDescription();
        office = aFaculty.getOffice();
        extension = aFaculty.getExtension();
        Enrol = DF.format(aFaculty.getEnrolDate());
        LastAccess = DF.format(aFaculty.getLastAccess());

        facultyLogin = true;
    }
    else
    {
        session.setAttribute("errors", "No student or faculty account detected");
        response.sendRedirect("./login.jsp");
    }


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

        <h5 class="card-header">DASHBOARD</h5>

        <% if (session.getAttribute("success") != null) {%>
            <div class="alert alert-primary">
                <%=session.getAttribute("success")%>
            </div>
        <%}
            session.removeAttribute("success"); %>

        <div class="card-body">
            <% if (studentLogin) {%>

                <p class="card-text">Full Name: <%=FullName%></p>
                <p class="card-text">Student Number: <%=ID%></p>
                <p class="card-text">Email: <%=Email%></p>
                <p class="card-text">Program Code: <%=ProgramCode%></p>
                <p class="card-text">Program Description: <%=ProgramDescription%></p>
                <p class="card-text">Year: <%=Year%></p>
                <p class="card-text">Enrol Date: <%=Enrol%></p>
                <p class="card-text">Last Access: <%=LastAccess%></p>

            <% } else if (facultyLogin) { %>

                <p class="card-text">Full Name: <%=FullName%></p>
                <p class="card-text">Student Number: <%=ID%></p>
                <p class="card-text">Email: <%=Email%></p>
                <p class="card-text">Program Code: <%=schoolCode%></p>
                <p class="card-text">Program Description: <%=schoolDescription%></p>
                <p class="card-text">Office: <%=office%></p>
                <p class="card-text">Extension: <%=extension%></p>
                <p class="card-text">Enrol Date: <%=Enrol%></p>
                <p class="card-text">Last Access: <%=LastAccess%></p>

            <% } %>
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
