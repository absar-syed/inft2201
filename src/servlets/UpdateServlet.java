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


public class UpdateServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
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
        String inputtedLastName;
        String inputtedPassword;
        String inputtedEmailAddress;
        String inputtedProgramCode;
        String inputtedProgramDescription;
        int inputtedYear;
        String errorBuilder = "";


        aStudent = (Student) session.getAttribute("student");


        /*
        This update servlet gets this error:
        java.lang.NullPointerException
            servlets.UpdateServlet.doPost(UpdateServlet.java:81)

        for some reason the variable "first" (the one below this comment) keeps pulling null,
        breaking the entire servlet.
        */


        //FIRST NAME VALIDATION


            try
            {
                String inputtedFirstName = request.getParameter("firstname").trim();

                if (isNumber(inputtedFirstName)) {

                    anyErrors = true;
                    throw new InvalidNameException("First name cannot be a number!");

                }
                if (Objects.equals(inputtedFirstName, "")) {

                    anyErrors = true;
                    throw new InvalidNameException("First name must not be empty!");

                }

                session.setAttribute("validFirstName", inputtedFirstName);
                aStudent.setFirstName(inputtedFirstName);
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
                    throw new InvalidNameException("Last name cannot be a number!");

                }
                if (Objects.equals(inputtedLastName, "")) {

                    anyErrors = true;
                    throw new InvalidNameException("Last name must not be empty!");

                }

                session.setAttribute("validLastName", inputtedLastName);
                aStudent.setLastName(inputtedLastName);

            }
            catch (InvalidNameException e)
            {

                session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

            }





        //PASSWORD VALIDATION

            inputtedPassword = request.getParameter("password").trim();

            if (inputtedPassword.isEmpty())
            {
                anyErrors = true;
                session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat("Password cannot be empty!"));
            }
            else
            {
                try
                {


                    if (inputtedPassword.length() > MAXIMUM_PASSWORD_LENGTH) {


                        throw new InvalidPasswordException("Password is too long. Maximum of 64 characters!");

                    }

                    if ( inputtedPassword.length() < MINIMUM_PASSWORD_LENGTH ) {


                        throw new InvalidPasswordException("Password is too short. Minimum of 8 characters!");

                    }

                    session.setAttribute("validPassword", inputtedPassword);

                }
                catch (InvalidPasswordException e)
                {
                    anyErrors = true;
                    session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat(e.getMessage()));

                }
            }



        //EMAIL ADDRESS VALIDATION


            try
            {
                inputtedEmailAddress = request.getParameter("email").trim();

                if (inputtedEmailAddress.isEmpty())
                {
                    anyErrors = true;
                    throw new InvalidUserDataException("Email cannot be empty!");
                }

                InternetAddress emailValidation = new InternetAddress(inputtedEmailAddress);
                emailValidation.validate();

                session.setAttribute("validEmailAddress", inputtedEmailAddress);

                aStudent.setEmailAddress(inputtedEmailAddress);

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
                    throw new InvalidUserDataException("Program Code cannot be empty!");
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
                        throw new InvalidUserDataException("Program Code must be 4 characters long!");
                    }

                }
                else
                {
                    anyErrors = true;
                    throw new InvalidUserDataException("Program Code must alphabetic!");
                }

                session.setAttribute("validProgramCode", inputtedProgramCode);
                aStudent.setProgramCode(inputtedProgramCode);
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
                    throw new InvalidUserDataException("Program Description cannot be empty!");
                }

                session.setAttribute("validProgramDescription", inputtedProgramDescription);

                aStudent.setProgramDescription(inputtedProgramDescription);

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
                session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat("Year cannot be empty!"));
            }
            else
            {
                try
                {
                    inputtedYear = Integer.parseInt(request.getParameter("year").trim());

                    if (inputtedYear > 3 || inputtedYear < 1)
                    {
                        throw new InvalidUserDataException("Year can only be 1, 2 or 3");
                    }

                    session.setAttribute("validYear", inputtedYear);

                    aStudent.setYear(inputtedYear);
                }
                catch (NumberFormatException e)
                {
                    anyErrors = true;
                    session.setAttribute("errors", errorBuilder = errorBuilder.concat("\n").concat("Year must be numeric!"));
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
            session.setAttribute("student", aStudent);

            try
            {
                aStudent = (Student) session.getAttribute("student");
                aStudent.update();
            }
            catch (InvalidUserDataException | SQLException | NotFoundException e)
            {
                throw new RuntimeException(e);
            }

            session.setAttribute("success", "Student Information Updated");
            response.sendRedirect("./dashboard.jsp");
        }
        else
        {
            response.sendRedirect("./update.jsp");
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


