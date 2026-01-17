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

            RequestDispatcher dispatcher = request.getRequestDispatcher("../StaffLoginServlet");
            dispatcher.forward(request, response);
        }
        %>
    </body>
</html>


