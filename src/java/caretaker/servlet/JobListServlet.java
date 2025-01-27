package caretaker.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JobListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> jobs = new ArrayList<>();
        String message = null;
        String error = null;

        // Retrieve caretaker ID from session
        Integer caretakerId = (Integer) request.getSession().getAttribute("caretakerId");

        // Check if caretaker ID is null, indicating that the caretaker is not logged in
        if (caretakerId == null) {
            error = "You must be logged in as a caretaker to view the job list.";
            request.setAttribute("error", error);
            request.getRequestDispatcher("caretaker/login.jsp").forward(request, response);
            return;
        }

        // Try-with-resources for Connection and PreparedStatement
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Updated query to include payment_status by joining the PAYMENT table
            String query = "SELECT c.cust_username, c.cust_phone_number, b.booking_time, b.booking_duration, " +
                           "b.booking_price, p.payment_status " +
                           "FROM CUSTOMER c " +
                           "JOIN BOOKING b ON c.cust_id = b.cust_id " +
                           "LEFT JOIN PAYMENT p ON b.booking_id = p.booking_id " +
                           "WHERE b.caretaker_id = ?"; // Assuming the BOOKING table has a caretaker_id column

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, caretakerId);  // Set caretakerId as the filter

                try (ResultSet rs = stmt.executeQuery()) {
                    // Process the result set
                    jobs = processResultSet(rs);

                    if (jobs.isEmpty()) {
                        message = "No jobs assigned.";
                    }
                } catch (SQLException e) {
                    error = "Error fetching job data: " + e.getMessage();
                    e.printStackTrace();  // Log the stack trace for debugging
                }
            }
        } catch (SQLException e) {
            error = "Database connection error: " + e.getMessage();
            e.printStackTrace();  // Log the stack trace for debugging
        }

        // Set attributes for the JSP
        request.setAttribute("jobs", jobs);
        request.setAttribute("message", message);
        request.setAttribute("error", error);

        // Forward to the view_jobs.jsp
        request.getRequestDispatcher("caretaker/view_jobs.jsp").forward(request, response);
    }

    private List<Map<String, Object>> processResultSet(ResultSet rs) throws SQLException {
        List<Map<String, Object>> jobList = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> job = new HashMap<>();
            job.put("customerName", rs.getString("cust_username"));
            job.put("customerPhone", rs.getString("cust_phone_number"));
            job.put("bookingTime", rs.getString("booking_time"));
            job.put("bookingDuration", rs.getString("booking_duration"));
            job.put("bookingPrice", rs.getDouble("booking_price"));
            job.put("bookingStatus", rs.getString("payment_status")); // Fetch payment_status
            jobList.add(job);
        }

        return jobList;
    }
}
