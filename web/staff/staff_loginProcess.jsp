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
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                if (username != null && password != null) {
                    // Debugging
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);

                    // Forward the request to the servlet
                    RequestDispatcher dispatcher = request.getRequestDispatcher("../StaffLoginServlet");
                    dispatcher.forward(request, response);
                } else {
                    out.println("<p>Missing username or password. Please try again.</p>");
                }
            } else {
                out.println("<p>Invalid request method. Please submit the form properly.</p>");
            }
        %>
    </body>
</html>


