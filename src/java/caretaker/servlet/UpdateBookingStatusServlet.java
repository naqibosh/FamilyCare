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
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String message = null;
        String error = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE PAYMENT SET PAYMENT_STATUS = ? WHERE BOOKING_ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, status);
                stmt.setInt(2, bookingId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    message = "Booking status updated successfully.";
                } else {
                    error = "Failed to update booking status.";
                }
            }
        } catch (SQLException e) {
            error = "Database error: " + e.getMessage();
            e.printStackTrace();
        }

        request.setAttribute("message", message);
        request.setAttribute("error", error);
        response.sendRedirect("JobListServlet");
    }
}
