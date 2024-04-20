<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="syeda.DatabaseConnect" %>
<%@ page import="syeda.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="exceptions.InvalidUserDataException" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Absar Syed
  Date: 2024-03-07
  Time: 4:30 p.m.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<%

    ResultSet queryResults = null;
    final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");
    long ID = 0;
    String password = null;
    String firstName = null;
    String lastName = null;
    String email = null;
    String lastAccessAsStr = null;
    String enrolDateAsStr = null;
    boolean enabled = false;
    char type = 'a';
    Date lastAccess = null;
    Date enrolDate = null;
    User aUser = null;

    if (session.getAttribute("admin") == null)
    {
        session.setAttribute("errors", "You do not have authorization to access the admin page.");
        response.sendRedirect("./dashboard.jsp");
    }

    Connection c = DatabaseConnect.initialize();
    User.initialize(c);

    try
    {
        PreparedStatement userQuery = c.prepareStatement("Select * from users;");
        queryResults = userQuery.executeQuery();

    }
    catch (SQLException e)
    {
        session.setAttribute("errors", "There was an issue with retrieving the result set.");
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

        <h5 class="card-header">ADMIN DASHBOARD</h5>

        <% if (session.getAttribute("success") != null)  {%>
            <div class="alert alert-primary">
                <%=session.getAttribute("success")%>
            </div>
        <%} session.removeAttribute("success"); %>

        <div class="card-body">
            <table class="table table-dark table-hover">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Full Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Last Access</th>
                    <th scope="col">Enrol Date</th>
                    <th scope="col">Enabled</th>
                    <th scope="col">Type</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                    <% while (queryResults.next()) {

                        try
                        {

                            ID = queryResults.getLong("userid");
                            password = queryResults.getString("password");
                            firstName = queryResults.getString("firstname");
                            lastName = queryResults.getString("lastname");
                            email = queryResults.getString("emailaddress");
                            lastAccessAsStr = String.valueOf(SQL_DF.parse(queryResults.getString("lastaccess")));
                            enrolDateAsStr = String.valueOf(SQL_DF.parse(queryResults.getString("enroldate")));
                            enabled = queryResults.getBoolean("enabled");
                            type = queryResults.getString("type").charAt(0);

                            lastAccess = SQL_DF.parse(queryResults.getString("lastaccess"));
                            enrolDate = SQL_DF.parse(queryResults.getString("enroldate"));
                        }

                        catch (SQLException e)
                        {
                            session.setAttribute("errors", "There was an issue with the sql.");
                            response.sendRedirect("./login.jsp");
                        }
                        catch (ParseException e)
                        {
                            session.setAttribute("errors", "There was an issue with the parse.");
                            response.sendRedirect("./login.jsp");
                        }

                        try
                        {
                            aUser = new User(ID, password, firstName, lastName, email, lastAccess, enrolDate, enabled, type);
                        }
                        catch (InvalidUserDataException e)
                        {
                            session.setAttribute("errors", "Something went wrong.");
                            response.sendRedirect("./login.jsp");
                        }

                        session.setAttribute("editUser", aUser);



                    %>
                        <tr>
                            <th scope="row"><%= ID %></th>
                            <td><%= firstName + " " + lastName %></td>
                            <td><%= email %></td>
                            <td><%= lastAccessAsStr %></td>
                            <td><%= enrolDateAsStr %></td>
                            <td><%= enabled %></td>
                            <td><%= type %></td>
                            <td><a href="./adminEdit.jsp" class="btn btn-primary" >Edit</a></td>
                            <td><a href="" class="btn btn-danger" >Delete</a></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
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
