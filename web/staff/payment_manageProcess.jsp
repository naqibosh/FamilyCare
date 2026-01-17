<%-- 
    Document   : payment_manageProcess
    Created on : Jan 28, 2025, 12:44:50 PM
    Author     : hazik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String action = request.getParameter("action");
    String targetServlet = "../PaymentManageServlet";

    try {
        if ("paymentList".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=paymentList");
            dispatcher.forward(request, response);
        } else if ("viewPaymentInfo".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewPaymentInfo");
            dispatcher.forward(request, response);
        } else if ("updatePayment".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=updatePayment");
            dispatcher.forward(request, response);
        } else if ("deletePayment".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=deletePayment");
            dispatcher.forward(request, response);
        } else if ("insertPayment".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=insertPayment");
            dispatcher.forward(request, response);
        } else if ("editPayment".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=editPayment");
            dispatcher.forward(request, response);
        } else if ("previewPayment".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=previewPayment");
            dispatcher.forward(request, response);
        }else {
            out.println("Invalid action specified.");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log the error
        out.println("An error occurred while processing your request: " + e.getMessage());
    }
%>
