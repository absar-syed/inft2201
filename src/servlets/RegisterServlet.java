package servlets;

import exceptions.InvalidNameException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidUserDataException;
import exceptions.NotFoundException;
import jdk.nashorn.internal.runtime.ECMAException;
import syeda.DatabaseConnect;
import syeda.PasswordHasher;
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
            HttpSession session = request.getSession(true); //retrieve the session (or start)

            boolean anyErrors = false;
            Student aStudent = null;

            Long inputtedID = null;
            String inputtedPassword = null;
            String inputtedFirstName = null;
            String inputtedLastName = null;
            String inputtedEmailAddress = null;
            String inputtedProgramCode = null;
            String inputtedProgramDescription = null;
            int inputtedYear = 0;

            //ID VALIDATION
            try
            {
                inputtedID = Long.parseLong(request.getParameter("userid").trim());

                try
                {
                    aStudent = Student.retrieve(inputtedID);
                    throw new Exception("User ID already exists. Try something else.");
                }
                catch (NotFoundException e)
                {
                    if (User.verifyID(inputtedID))
                    {
                        session.setAttribute("validStudentID", inputtedID);
                    }
                    else
                    {
                        anyErrors = true;
                        throw new NumberFormatException("User ID must be 9 digits!");
                    }
                }

            }
            catch (NumberFormatException e)
            {
                anyErrors = true;
                session.setAttribute("errors", e.getMessage());
            }

            //PASSWORD VALIDATION
            try
            {
                inputtedPassword = request.getParameter("password").trim();

                if (inputtedPassword.length() > MAXIMUM_PASSWORD_LENGTH) {

                    anyErrors = true;
                    throw new InvalidPasswordException("Password is too long. Maximum of 64 characters!");

                }

                if ( inputtedPassword.length() < MINIMUM_PASSWORD_LENGTH ) {

                    anyErrors = true;
                    throw new InvalidPasswordException("Password is too short. Minimum of 8 characters!");

                }

                session.setAttribute("validPassword", inputtedPassword);

            }
            catch (InvalidPasswordException e)
            {
                session.setAttribute("errors", e.getMessage());
            }

            //FIRST NAME VALIDATION
            try
            {
                inputtedFirstName = request.getParameter("firstname").trim();

                if (isNumber(inputtedFirstName)) {

                    anyErrors = true;
                    throw new InvalidNameException("First name cannot be a number!");

                }
                if (Objects.equals(inputtedFirstName, "")) {

                    anyErrors = true;
                    throw new InvalidNameException("First name must not be empty!");

                }

                session.setAttribute("validFirstName", inputtedFirstName);

            }
            catch (InvalidNameException e)
            {

                session.setAttribute("errors", e.getMessage());
            }

            //LAST NAME VALIDATION
            try
            {
                inputtedLastName = request.getParameter("lastname").trim();

                if (isNumber(inputtedLastName)) {

                    anyErrors = true;
                    throw new InvalidNameException("Last name cannot be a number!");

                }
                if (Objects.equals(inputtedLastName, "")) {

                    anyErrors = true;
                    throw new InvalidNameException("Last name must not be empty!");

                }

                session.setAttribute("validFirstName", inputtedLastName);

            }
            catch (InvalidNameException e)
            {

                session.setAttribute("errors", e.getMessage());
            }

            //EMAIL ADDRESS VALIDATION
            try
            {
                inputtedEmailAddress = request.getParameter("email").trim();

                if (inputtedEmailAddress.isEmpty())
                {
                    anyErrors = true;
                    throw new Exception("Email Input must not be empty!");
                }

                InternetAddress emailValidation = new InternetAddress(inputtedEmailAddress);
                emailValidation.validate();

                session.setAttribute("validEmailAddress", inputtedEmailAddress);
            }
            catch (AddressException e)
            {
                anyErrors = true;
                session.setAttribute("errors", e.getMessage());
            }

            //PROGRAM CODE VALIDATION
            try
            {
                inputtedProgramCode = request.getParameter("programcode").trim();

                if (inputtedProgramCode.isEmpty())
                {
                    anyErrors = true;
                    throw new Exception("Program Code cannot be empty!");
                }

                if (!isNumber(inputtedProgramCode))
                {
                    inputtedProgramCode.toUpperCase();

                    if (inputtedProgramCode.length() == 4)
                    {
                        session.setAttribute("validProgramCode", inputtedProgramCode);
                    }
                    else
                    {
                        anyErrors = true;
                        throw new Exception("Program Code must be 4 characters long!");
                    }

                }
                else
                {
                    anyErrors = true;
                    throw new Exception("Program Code must alphabetic!");
                }

                session.setAttribute("validProgramCode", inputtedProgramCode);
            }
            catch (Exception e)
            {
                session.setAttribute("errors", e.getMessage());
            }


            //PROGRAM DESCRIPTION
            try
            {
                inputtedProgramDescription = request.getParameter("programdescription").trim();

                if (inputtedProgramDescription.isEmpty())
                {
                    anyErrors = true;
                    throw new Exception("Program Description cannot be empty!");
                }

                session.setAttribute("validProgramDescription", inputtedProgramDescription);

            }
            catch (Exception e)
            {
                session.setAttribute("errors", e.getMessage());
            }


            //YEAR VALIDATION
            try
            {
                inputtedYear = Integer.parseInt(request.getParameter("year").trim());

                if (inputtedYear > 3 || inputtedYear < 1)
                {
                    anyErrors = true;
                    throw new Exception("For year you can only input 1, 2 or 3");
                }

                session.setAttribute("validYear", inputtedYear);
            }
            catch (NumberFormatException e)
            {
                session.setAttribute("errors", e.getMessage());
            }



            //if no errors enter into database
            if (!anyErrors)
            {

                Date lastAccess = new Date();
                Date enrolDate = new Date();
                boolean enabled = true;
                char type = 's';

                PasswordHasher Pass = new PasswordHasher(inputtedPassword);

                aStudent = new Student(inputtedID, Pass.Hash(), inputtedFirstName, inputtedLastName, inputtedEmailAddress,
                                       lastAccess, enrolDate, enabled, type, inputtedProgramCode, inputtedProgramDescription,
                                       inputtedYear);

                aStudent.create();
                session.setAttribute("student", aStudent);
                response.sendRedirect("./dashboard.jsp");
            }
            else
            {
                response.sendRedirect("./register.jsp");
            }


        }
        catch (Exception e)
        {

            System.out.println(e);
            String line1="<h2>A network error has occurred!</h2>";
            String line2="<p>Please notify your system " +
                    "administrator and check log. "+e.getMessage()+"</p>";
            formatErrorPage(line1, line2,response);

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


