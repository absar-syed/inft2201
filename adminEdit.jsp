<%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<%@ page import="static syeda.User.DF" %>
<%@ page import="syeda.User" %>
<%
    User aUser = (User) session.getAttribute("editUser");

    String FirstName = aUser.getFirstName();
    String LastName = aUser.getLastName();
    long ID = aUser.getId();
    String Email = aUser.getEmailAddress();
    String Enrol = DF.format(aUser.getEnrolDate()) ;
    String LastAccess = DF.format(aUser.getLastAccess()) ;
    String Password = aUser.getPassword();

%>

<html>
<head>
    <title>Edit User Information</title>
    <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="node_modules/@fortawesome/fontawesome-free/css/all.css">
    <link rel="stylesheet" href="content/style.css">
</head>


<body>



    <div class="card m-5 bg-dark text-white">

        <h5 class="card-header">ADMIN EDIT</h5>

        <div class="card-body">

            <form name="Update" method="get" action="./Update">

                <% if (session.getAttribute("errors") != null) {%>
                    <div class="alert alert-danger">
                        <%=session.getAttribute("errors")%>
                    </div>
                <%} session.removeAttribute("errors"); %>

                <div class="dark input-group mb-3">
                    <label for="firstname" class="input-group-text">First Name</label>
                    <input id="firstname" name="firstname" type="text" class="form-control"   value="<%=FirstName%>">
                </div>
                <div class="dark input-group mb-3">
                    <label for="lastname" class="input-group-text">Last Name</label>
                    <input id="lastname" name="lastname" type="text" class="form-control" value="<%=LastName%>">
                </div>
                <div class="dark input-group mb-3">
                    <label for="password" class="input-group-text">Password</label>
                    <input id="password" name="password" type="password" class="form-control"  value="<%=Password%>">
                </div>
                <div class="input-group mb-3">
                    <label for="email" class="input-group-text">Email</label>
                    <input id="email" name="email" type="text" class="form-control"   value="<%=Email%>">
                </div>
                <a class="small no-decor">*ID Number, enrol date and last access cannot be updated*</a>
        </div>
<%--        <button  type="submit" class="btn btn-primary m-5" >Update</button>--%>
    </div>


    <div class="m-5">

        <div class="row">
            <div class="col-1">
                    <button  type="submit" class="btn btn-primary " >Update</button>
            </div>
            <div class="col-1">
                <form  name="Reset" method="get" action="./ResetUpdate">
                    <button  type="submit" class="btn btn-secondary " >Reset</button>
                </form>
            </div>
            <div class="col-1">
                    <a class="btn btn-warning" href="./dashboard.jsp">Cancel</a>
            </div>
        </div>

    </div>


    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>

</body>


<%@include file="footer.jsp"%>
</html>
