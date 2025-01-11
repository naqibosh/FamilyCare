<%-- 
    Document   : staff_registerProcess
    Created on : Jan 9, 2025, 5:30:13 PM
    Author     : hazik
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff Register Processing</title>
    </head>
    <body>
        <%
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String phoneNumber = request.getParameter("phoneNumber");
                String role = request.getParameter("role");

                if (email == null || password == null || password == null || phoneNumber == null || role == null) {
                    // Redirect to login.html with error code
                    response.sendRedirect("register.html?error=2+null");
                } else {                 
                    // Forward the request to the servlet
                    RequestDispatcher dispatcher = request.getRequestDispatcher("../StaffRegisterServlet");
                    dispatcher.forward(request, response);
                    //out.println("<p>An error occur1.</p>"); //debug
                }
            } 
        %>
    </body>
</html>