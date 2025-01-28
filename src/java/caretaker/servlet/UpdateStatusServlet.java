package caretaker.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check session validity
        if (session == null || session.getAttribute("caretakerId") == null) {
            response.sendRedirect("login.jsp?errorMessage=Session expired or invalid. Please log in again.");
            return;
        }

        // Retrieve caretaker ID and status
        Integer caretakerId = (Integer) session.getAttribute("caretakerId");
        String availabilityStatus = request.getParameter("availabilityStatus");

        // Validate input
        if (caretakerId == null || availabilityStatus == null || availabilityStatus.trim().isEmpty()) {
            response.sendRedirect("caretaker/reviewStatus.jsp?errorMessage=Invalid input. Please try again.");
            return;
        }

        // Ensure valid availability status values (e.g., AVAILABLE, NOT_AVAILABLE)
        if (!availabilityStatus.equalsIgnoreCase("available") && !availabilityStatus.equalsIgnoreCase("not available")) {
            response.sendRedirect("caretaker/reviewStatus.jsp?errorMessage=Invalid status value. Please choose a valid option.");
            return;
        }

        // Convert availabilityStatus to uppercase to match the expected value in the database


        // Database connection

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Update query for the CARETAKER table
            String updateQuery = "UPDATE CARETAKER SET AVAILABILITY_STATUS = ? WHERE CARETAKER_ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                stmt.setString(1, availabilityStatus);
                stmt.setInt(2, caretakerId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    // Success: Update session and redirect
                    session.setAttribute("currentStatus", availabilityStatus);
                    response.sendRedirect("caretaker/reviewStatus.jsp?successMessage=Status updated successfully.");
                } else {
                    // Failure: Redirect with error message
                    response.sendRedirect("caretaker/reviewStatus.jsp?errorMessage=Failed to update status.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("caretaker/reviewStatus.jsp?errorMessage=Database error: " + e.getMessage());
        }
    }
}
