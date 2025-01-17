<%-- 
    Document   : staff_logoutProcess
    Created on : Jan 12, 2025, 2:10:33 AM
    Author     : hazik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff Logout Processing</title>
    </head>
    <body>
        <%
            try{
                RequestDispatcher dispatcher = request.getRequestDispatcher("../StaffLogoutServlet");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                    // Log the error (optional, for debugging purposes)
                    e.printStackTrace();
                    // Redirect to an error page
                    response.sendRedirect("404.html");
                }
        %>
    </body>
</html>
