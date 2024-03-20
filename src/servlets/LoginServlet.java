package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;

import exceptions.NotFoundException;
import syeda.DatabaseConnect;
import syeda.Student;
import static syeda.Student.authenticate;


public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
                       throws IOException
    {

        String logFile = "./logger.log";
	    File f = new File(logFile);
	    PrintStream printStream = new PrintStream(new BufferedOutputStream(Files.newOutputStream(f.toPath())), true);
	    System.setErr(printStream);
	    System.setOut(printStream);
	    System.out.println("Log started: " + new java.util.Date());

        try
        {
            // connect to database
            Connection c = DatabaseConnect.initialize();
            Student.initialize(c);
            HttpSession session = request.getSession(true);

            try
            {

                //initialize variables to store user inputs
                long UserID = Long.parseLong(request.getParameter( "userid" ));
                String Password = request.getParameter("password");


                //retrieve user creds from db and create a student object or throw NotFoundException
                Student aStudent = authenticate(UserID, Password);

                //update last access in the db
                aStudent.setLastAccess(new Date());
                aStudent.update();

                //set the student object to the session and any errors
                session.setAttribute("student", aStudent);
                session.setAttribute("errors", "");

                // redirect the user to dashboard
                response.sendRedirect("./dashboard.jsp");

            }catch(NotFoundException | NumberFormatException e)
            {
//                //sending errors to the page thru the session
                StringBuffer errorBuffer = new StringBuffer();
                errorBuffer.append("Your sign in information is not valid. ");
                errorBuffer.append("Please try again.");
//                String error = "Login information was invalid. Try again.";

                session.setAttribute("errors", errorBuffer.toString());
                response.sendRedirect("./login.jsp");
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