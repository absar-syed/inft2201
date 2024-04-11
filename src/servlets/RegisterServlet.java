package servlets;

import exceptions.*;
import syeda.DatabaseConnect;
import syeda.Student;
import syeda.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import static jdk.nashorn.internal.runtime.JSType.isNumber;
import static syeda.User.MAXIMUM_PASSWORD_LENGTH;
import static syeda.User.MINIMUM_PASSWORD_LENGTH;



public class RegisterServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        String logFile = "./logger.log";
        File f = new File(logFile);
        PrintStream printStream = new PrintStream(new BufferedOutputStream(Files.newOutputStream(f.toPath())), true);
        System.setErr(printStream);
        System.setOut(printStream);
        System.out.println("Log started: " + new Date());

        try
        {
            Connection c = DatabaseConnect.initialize();
            Student.initialize(c);

        }
        catch (Exception e)
        {

            System.out.println(e);
            String line1="<h2>A network error has occurred!</h2>";
            String line2="<p>Please notify your system " +
                    "administrator and check log. "+e.getMessage()+"</p>";
            formatErrorPage(line1, line2,response);

        }

        HttpSession session = request.getSession(true); //retrieve the session (or start)

        boolean anyErrors = false;
        Student aStudent;
        Long inputtedID = null;
        String inputtedPassword = null;
        String inputtedFirstName = null;
        String inputtedLastName = null;
        String inputtedEmailAddress = null;
        String inputtedProgramCode = null;
        String inputtedProgramDescription = null;
        int inputtedYear = 0;
        String errorBuilder = "";


        //ID VALIDATION
        String id = request.getParameter("userid");

        if (id.isEmpty())
        {
            anyErrors = true;
            session.setAttribute("errors", errorBuilder = errorBuilder.concat("<p>User ID cannot be blank!</p>"));

        }
        else
        {
            try
            {
                inputtedID = Long.parseLong(id.trim());

                try
                {
                    Student.retrieve(inputtedID);
                    anyErrors = true;
                    session.setAttribute("errors", errorBuilder = errorBuilder.concat("<p>User ID already exists!</p>"));

                }
                catch (NotFoundException | SQLException e)
                {
                    if (User.verifyID(inputtedID))
                    {
                        session.setAttribute("validStudentID", Long.toString(inputtedID));
                    }
                    else
                    {
                        anyErrors = true;
                        session.setAttribute("errors", errorBuilder = errorBuilder.concat("<p>User ID must be 9 digits!</p>"));

                    }
                }
            }
            catch (NumberFormatException e)
            {
                anyErrors = true;
                session.setAttribute("errors", errorBuilder = errorBuilder.concat("<p>User ID must be numeric!</p>"));

            }


        }

        //PASSWORD VALIDATION

        inputtedPassword = request.getParameter("password").trim();

        if (inputtedPassword.isEmpty())
        {
            anyErrors = true;
            session.setAttribute("errors", errorBuilder = errorBuilder.concat("<p>Password cannot be empty!</p>"));
        }
        else
        {
            try
            {


                if (inputtedPassword.length() > MAXIMUM_PASSWORD_LENGTH) {


                    throw new InvalidPasswordException("<p>Password is too long. Maximum of 64 characters!</p>");

                }

                if ( inputtedPassword.length() < MINIMUM_PASSWORD_LENGTH ) {


                    throw new InvalidPasswordException("<p>Password is too short. Minimum of 8 characters!</p>");

                }

                session.setAttribute("validPassword", inputtedPassword);

            }
            catch (InvalidPasswordException e)
            {
                anyErrors = true;
                session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

            }
        }



        //FIRST NAME VALIDATION
        try
        {
            inputtedFirstName = request.getParameter("firstname").trim();

            if (isNumber(inputtedFirstName)) {

                anyErrors = true;
                throw new InvalidNameException("<p>First name cannot be a number!</p>");

            }
            if (Objects.equals(inputtedFirstName, "")) {

                anyErrors = true;
                throw new InvalidNameException("<p>First name must not be empty!</p>");

            }

            session.setAttribute("validFirstName", inputtedFirstName);

        }
        catch (InvalidNameException e)
        {

            session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

        }

        //LAST NAME VALIDATION
        try
        {
            inputtedLastName = request.getParameter("lastname").trim();

            if (isNumber(inputtedLastName)) {

                anyErrors = true;
                throw new InvalidNameException("<p>Last name cannot be a number!</p>");

            }
            if (Objects.equals(inputtedLastName, "")) {

                anyErrors = true;
                throw new InvalidNameException("<p>Last name must not be empty!</p>");

            }

            session.setAttribute("validLastName", inputtedLastName);

        }
        catch (InvalidNameException e)
        {

            session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

        }

        //EMAIL ADDRESS VALIDATION
        try
        {
            inputtedEmailAddress = request.getParameter("email").trim();

            if (inputtedEmailAddress.isEmpty())
            {
                anyErrors = true;
                throw new InvalidUserDataException("<p>Email cannot be empty!</p>");
            }

            InternetAddress emailValidation = new InternetAddress(inputtedEmailAddress);
            emailValidation.validate();

            session.setAttribute("validEmailAddress", inputtedEmailAddress);
        }
        catch (AddressException | InvalidUserDataException e)
        {
            anyErrors = true;
            session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

        }

        //PROGRAM CODE VALIDATION
        try
        {
            inputtedProgramCode = request.getParameter("programcode").trim();

            if (inputtedProgramCode.isEmpty())
            {
                anyErrors = true;
                throw new InvalidUserDataException("<p>Program Code cannot be empty!</p>");
            }

            if (!isNumber(inputtedProgramCode))
            {
                inputtedProgramCode = inputtedProgramCode.toUpperCase();

                if (inputtedProgramCode.length() == 4)
                {
                    session.setAttribute("validProgramCode", inputtedProgramCode);
                }
                else
                {
                    anyErrors = true;
                    throw new InvalidUserDataException("<p>Program Code must be 4 characters long!</p>");
                }

            }
            else
            {
                anyErrors = true;
                throw new InvalidUserDataException("<p>Program Code must alphabetic!</p>");
            }

            session.setAttribute("validProgramCode", inputtedProgramCode);
        }
        catch (InvalidUserDataException e)
        {
            session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

        }


        //PROGRAM DESCRIPTION
        try
        {
            inputtedProgramDescription = request.getParameter("programdescription").trim();

            if (inputtedProgramDescription.isEmpty())
            {
                anyErrors = true;
                throw new InvalidUserDataException("<p>Program Description cannot be empty!</p>");
            }

            session.setAttribute("validProgramDescription", inputtedProgramDescription);

        }
        catch (InvalidUserDataException e)
        {
            session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

        }


        //YEAR VALIDATION

        String year = request.getParameter("year");

        if (year.isEmpty())
        {
            anyErrors = true;
            session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat("<p>Year cannot be empty!</p>"));
        }
        else
        {
            try
            {
                inputtedYear = Integer.parseInt(request.getParameter("year").trim());

                if (inputtedYear > 3 || inputtedYear < 1)
                {
                    throw new InvalidUserDataException("<p>Year can only be 1, 2 or 3</p>");
                }

                session.setAttribute("validYear", inputtedYear);
            }
            catch (NumberFormatException e)
            {
                anyErrors = true;
                session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat("<p>Year must be numeric!</p>"));
            }
            catch ( InvalidUserDataException e)
            {
                anyErrors = true;
                session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));
            }
        }


        //if no errors enter into database
        if (!anyErrors)
        {

            Date lastAccess = new Date();
            Date enrolDate = new Date();
            boolean enabled = true;
            char type = 's';

            try {
                aStudent = new Student(inputtedID, inputtedPassword, inputtedFirstName, inputtedLastName, inputtedEmailAddress,
                                       lastAccess, enrolDate, enabled, type, inputtedProgramCode, inputtedProgramDescription,
                                       inputtedYear);
            } catch (InvalidUserDataException e) {
                throw new RuntimeException(e);
            }

            try {
                aStudent.create();
            } catch (DuplicateException | ParseException | SQLException | InvalidUserDataException e) {
                throw new RuntimeException(e);
            }
            session.setAttribute("student", aStudent);
            response.sendRedirect("./dashboard.jsp");
        }
        else
        {
            response.sendRedirect("./register.jsp");
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }


    public void formatErrorPage( String first, String second,
                                 HttpServletResponse response) throws IOException
    {
        PrintWriter output = response.getWriter();
        response.setContentType( "text/html" );
        output.println(first);
        output.println(second);
        output.close();
    }
}


