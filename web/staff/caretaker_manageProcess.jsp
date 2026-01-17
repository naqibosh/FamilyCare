<%-- 
    Document   : caretaker_manageProcess
    Created on : Jan 28, 2025, 3:01:39 AM
    Author     : hazik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String action = request.getParameter("action");
    String targetServlet = "../CaretakerManageServlet";

    try {
        if ("caretakerList".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewCaretakerList");
            dispatcher.forward(request, response);
        } else if ("viewCaretaker".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewCaretakerInfo");
            dispatcher.forward(request, response);
        } else if ("updateCaretaker".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=updateCaretaker");
            dispatcher.forward(request, response);
        } else if ("enableCaretaker".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=enableCaretaker");
            dispatcher.forward(request, response);
        } else if ("disableCaretaker".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=disableCaretaker");
            dispatcher.forward(request, response);
        } else if ("editCaretaker".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=editCaretaker");
            dispatcher.forward(request, response); 
        } else if ("previewCaretaker".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=previewCaretaker");
            dispatcher.forward(request, response); 
        } else {
            out.println("Invalid action specified.");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log the error
        out.println("An error occurred while processing your request: " + e.getMessage());
    }
%>
