<%-- 
    Document   : staff_loginProcess
    Created on : Jan 8, 2025, 5:37:29 PM
    Author     : hazik
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff Login Processing</title>
    </head>
    <body>
        <%
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (email == null || password == null) {
                    // Redirect to login.html with error code
                    response.sendRedirect("login.html?error=1");
                } else {
                    try {
                        // Forward the request to the servlet
                        RequestDispatcher dispatcher = request.getRequestDispatcher("../StaffLoginServlet");
                        dispatcher.forward(request, response);
                    } catch (Exception e) {
                        // Log the error (optional, for debugging purposes)
                        e.printStackTrace();
                
                        // Redirect to an error page
                        response.sendRedirect("404.html");
                    }
                }
            } 
        %>
    </body>
</html>


