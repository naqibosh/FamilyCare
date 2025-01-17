<%-- 
    Document   : staff_registerProcess
    Created on : Jan 9, 2025, 5:30:13 PM
    Author     : hazik
--%>

<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

                if (name == null || email == null || password == null || phoneNumber == null || role == null) {
                    // Redirect to register.html with error code
                    response.sendRedirect("register.html?error=1"); 
                } else {
                    try {
                    // Create a Staff object
                    Staff staff = new Staff();
                    staff.setName(name);
                    staff.setEmail(email);
                    staff.setPassword(password); // Hash password before storing
                    staff.setPhoneNumber(phoneNumber);
                    staff.setRole(role); 

                    // Set attributes in the request 
                    request.setAttribute("staff", staff); 

                    // Forward the request to the StaffRegisterServlet
                    RequestDispatcher dispatcher = request.getRequestDispatcher("../StaffRegisterServlet");
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