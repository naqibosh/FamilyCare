<%-- 
    Document   : customer_manageProcess
    Created on : Jan 27, 2025, 5:58:02 PM
    Author     : hazik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String action = request.getParameter("action");
    String targetServlet = "../CustomerManageServlet";

    try {
        if ("custList".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewCustList");
            dispatcher.forward(request, response);
        } else if ("viewCust".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewCustInfo");
            dispatcher.forward(request, response);
        } else if ("updateCust".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=updateCust");
            dispatcher.forward(request, response);
        } else if ("disableCust".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=disableCust");
            dispatcher.forward(request, response);
        } else if ("enableCust".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=enableCust");
            dispatcher.forward(request, response);
        } else if ("editCust".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=editCust");
            dispatcher.forward(request, response); 
        } else {
            out.println("Invalid action specified.");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log the error
        out.println("An error occurred while processing your request: " + e.getMessage());
    }
%>
