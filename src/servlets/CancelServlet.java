package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class CancelServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        request.removeAttribute("errors");
        request.removeAttribute("success");
        response.sendRedirect("./dashboard.jsp"); // redirect to dashboard.jsp
    }
}


