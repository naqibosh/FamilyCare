/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.Booking;
import userDAO.BookingDAO;
import userDAO.CaretakerDAO;
import utils.SessionUtils;

/**
 *
 * @author hazik
 */
public class BookingManageServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login.html?error=invalidSession";
    private static final String BOOKING_LIST_PAGE = "/staff/manage-booking.jsp";
    private static final String BOOKING_DETAILS_PAGE = "/staff/booking-details.jsp";

    private static final Logger logger = Logger.getLogger(BookingManageServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Integer staffId = SessionUtils.getUserIdFromSession(request);
        if (staffId == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        String action = request.getParameter("action");
        BookingDAO bookingDAO = new BookingDAO();

        try {
            switch (action) {
                case "bookingList":
                    handleViewBookingList(request, response, bookingDAO);
                    break;
                case "viewBookingInfo":
//                    handleViewBookingInfo(request, response, bookingDAO);  
                    break;
                case "updateBooking":
                    handleUpdateBooking(request, response, bookingDAO);
                    break;
                case "deleteBooking":
                    handleDeleteBooking(request, response, bookingDAO);
                    break;
                case "insertBooking":
                    handleInsertBooking(request, response, bookingDAO);
                    break;
                case "editBooking":
                    handleEditBooking(request, response, bookingDAO);
                    break;
                default:
                    sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleViewBookingList(HttpServletRequest request, HttpServletResponse response, BookingDAO bookingDAO)
            throws ServletException, IOException {
        try {
            List<Booking> bookingList = bookingDAO.selectAllBookings();
            request.setAttribute("bookingList", bookingList);
            request.getRequestDispatcher(BOOKING_LIST_PAGE).forward(request, response);
        } catch (SQLException e) {
            handleException(response, e);
        }
    }

//    private void handleViewBookingInfo(HttpServletRequest request, HttpServletResponse response, BookingDAO bookingDAO)  
//            throws ServletException, IOException {  
//        try {  
//            int bookingId = parseBookingId(request);  
//            Booking booking = bookingDAO.selectBookingById(bookingId);  
//            if (booking == null) {  
//                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Booking with ID " + bookingId + " not found.");  
//                return;  
//            }  
//            request.setAttribute("booking", booking);  
//            request.getRequestDispatcher(BOOKING_DETAILS_PAGE).forward(request, response);  
//        } catch (NumberFormatException ex) {  
//            handleException(response, ex);  
//        }  
//    }  
    private void handleUpdateBooking(HttpServletRequest request, HttpServletResponse response, BookingDAO bookingDAO)
            throws ServletException, IOException {
        try {
            int bookingId = parseBookingId(request);
            Booking booking = createBookingFromRequest(request, bookingId);

            bookingDAO.updateBooking(booking);
            response.sendRedirect("booking_manageProcess.jsp?action=viewBookingList&success=true");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleDeleteBooking(HttpServletRequest request, HttpServletResponse response, BookingDAO bookingDAO)
            throws ServletException, IOException {
        try {
            int bookingId = parseBookingId(request);

            bookingDAO.deleteBooking(bookingId);
            response.sendRedirect("booking_manageProcess.jsp?action=bookingList&success=true");
        } catch (SQLException | NumberFormatException ex) {
            handleException(response, ex);
        }
    }

    private void handleInsertBooking(HttpServletRequest request, HttpServletResponse response, BookingDAO bookingDAO)
            throws ServletException, IOException {
        try {
            Booking booking = createBookingFromRequest(request, 0);

            bookingDAO.insertBooking(booking);
            response.sendRedirect("booking_manageProcess.jsp?action=viewBookingList&success=true");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleEditBooking(HttpServletRequest request, HttpServletResponse response, BookingDAO bookingDAO)
            throws ServletException, IOException {
        try {
            // Parse parameters from the request
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int staffId = Integer.parseInt(request.getParameter("staffId"));

            // Call DAO to edit booking
            if (bookingDAO.editBooking(bookingId, staffId)) {
                response.sendRedirect("booking_manageProcess.jsp?action=bookingList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to edit booking with ID " + bookingId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid booking ID or staff ID format.");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private int parseBookingId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("bookingId"));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid booking ID format", e);
            throw e;
        }
    }

    private Booking createBookingFromRequest(HttpServletRequest request, int bookingId) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setBookingType(request.getParameter("bookingType"));
        booking.setBookingTime(request.getParameter("bookingTime"));
        booking.setBookingDuration(request.getParameter("bookingDuration"));
        booking.setBookingPrice(Double.parseDouble(request.getParameter("bookingPrice")));
        booking.setCaretakerId(Integer.parseInt(request.getParameter("caretakerId")));
        booking.setCustId(Integer.parseInt(request.getParameter("custId")));
        booking.setStaffId(Integer.parseInt(request.getParameter("staffId")));
        return booking;
    }

    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);
    }

    private void handleException(HttpServletResponse response, Exception ex) throws IOException {
        logger.log(Level.SEVERE, "Exception occurred", ex);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Booking management servlet handling CRUD operations based on action.";
    }
}
