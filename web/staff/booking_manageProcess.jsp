<%-- 
    Document   : booking_manageProcess
    Created on : Jan 28, 2025, 6:47:34 AM
    Author     : hazik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String action = request.getParameter("action");
    String targetServlet = "../BookingManageServlet";

    try {
        if ("bookingList".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=bookingList");
            dispatcher.forward(request, response);
        } else if ("viewBookingInfo".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewBookingInfo");
            dispatcher.forward(request, response);
        } else if ("updateBooking".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=updateBooking");
            dispatcher.forward(request, response);
        } else if ("deleteBooking".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=deleteBooking");
            dispatcher.forward(request, response);
        } else if ("insertBooking".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=insertBooking");
            dispatcher.forward(request, response);
        } else if ("editBooking".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=editBooking");
            dispatcher.forward(request, response);
        }else {
            out.println("Invalid action specified.");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log the error
        out.println("An error occurred while processing your request: " + e.getMessage());
    }
%>
