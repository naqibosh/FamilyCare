<%-- 
    Document   : Staff_manageProcess
    Created on : Jan 17, 2025, 12:31:07 AM
    Author     : hazik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            try{
                RequestDispatcher dispatcher = request.getRequestDispatcher("../StaffManageServlet");
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
