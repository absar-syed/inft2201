package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;


public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {

        HttpSession session = request.getSession(true); //retrieve the session (or start)
        session.removeAttribute("student"); //remove the object stored at login
        session.invalidate(); //delete the entire session
        session.setAttribute("message","You have successfully logged out"); //give an informational message
        response.sendRedirect("./login.jsp"); // redirect to login.jsp
    }
}


