package caretaker.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateBookingStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        String bookingIdParam = request.getParameter("bookingId");
        String message = null;
        String error = null;

        // Debugging: Print the received parameters
        System.out.println("Received bookingId: " + bookingIdParam);
        System.out.println("Received status: " + status);

        try {
            if (bookingIdParam == null || bookingIdParam.isEmpty() || "null".equals(bookingIdParam)) {
                throw new IllegalArgumentException("Booking ID is missing or invalid.");
            }

            int bookingId = Integer.parseInt(bookingIdParam);

            if (status == null || status.isEmpty()) {
                throw new IllegalArgumentException("Status is missing or invalid.");
            }

            // Validate the status is one of the allowed values
            if (!"Pending".equals(status) && !"Completed".equals(status) && !"Canceled".equals(status)) {
                throw new IllegalArgumentException("Invalid status value.");
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                // Update the status in the database
                String query = "UPDATE PAYMENT SET PAYMENT_STATUS = ? WHERE BOOKING_ID = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, status);
                    stmt.setInt(2, bookingId);

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        message = "Booking status updated successfully.";
                    } else {
                        error = "Failed to update booking status. No matching booking ID.";
                    }

                    // Debugging: Check how many rows were updated
                    System.out.println("Rows updated: " + rowsUpdated);
                }
            }
        } catch (NumberFormatException e) {
            error = "Invalid booking ID format: " + bookingIdParam;
            e.printStackTrace(); // Log the error for debugging
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        } catch (SQLException e) {
            error = "Database error: " + e.getMessage();
            e.printStackTrace(); // Log the error for debugging
        } catch (Exception e) {
            error = "Unexpected error: " + e.getMessage();
            e.printStackTrace(); // Log the error for debugging
        }

        // Set message or error attributes
        request.setAttribute("message", message);
        request.setAttribute("error", error);

        // Get the current URL to redirect back to the same page
        String currentPage = request.getHeader("Referer");

        // Redirect to the current page (view_jobs.jsp)
        response.sendRedirect(currentPage);
    }
}
