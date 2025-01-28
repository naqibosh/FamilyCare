package caretaker.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JobServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/FamilyCare";
    private static final String DB_USER = "CareGiver";
    private static final String DB_PASSWORD = "system";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the caretakerId from the request
        String caretakerId = request.getParameter("caretakerId");
        
        // Ensure caretakerId is not null or empty
        if (caretakerId == null || caretakerId.isEmpty()) {
            response.sendRedirect("/errorPage.jsp");  // Redirect to an error page if caretakerId is invalid
            return;
        }

        // Create a list to hold job data
        List<Map<String, Object>> jobs = new ArrayList<>();

        // SQL query to fetch job data for the caretaker, joining customer and booking tables
        String query = "SELECT c.cust_first_name, c.cust_last_name, c.cust_phone_number, b.booking_time, b.booking_duration, b.booking_price " +
                       "FROM jobs j " +
                       "JOIN customer c ON j.cust_id = c.cust_id " +
                       "JOIN booking b ON j.booking_id = b.booking_id " +
                       "WHERE j.caretaker_id = ?"; // Query to fetch jobs where the specific caretaker is assigned
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Set the caretakerId in the query
            stmt.setString(1, caretakerId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Check if there are any records for this caretaker
                boolean hasJobs = false;
                
                while (rs.next()) {
                    // Map job details from the result set
                    Map<String, Object> job = new HashMap<>();
                    job.put("customerName", rs.getString("cust_first_name") + " " + rs.getString("cust_last_name"));
                    job.put("customerPhone", rs.getString("cust_phone_number"));
                    job.put("bookingTime", rs.getString("booking_time"));
                    job.put("bookingDuration", rs.getInt("booking_duration"));
                    job.put("bookingPrice", rs.getDouble("booking_price"));
                    
                    // Add the job to the list
                    jobs.add(job);
                    hasJobs = true;
                }

                // If no jobs found, you could set an attribute to notify the user in the JSP
                if (!hasJobs) {
                    request.setAttribute("message", "No jobs assigned to you.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors and redirect to error page
            request.setAttribute("error", "Database error occurred while fetching jobs.");
            request.setAttribute("errorDetails", e.getMessage()); // Optional: detailed error message
            RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Set jobs as a request attribute to be accessed in JSP
        request.setAttribute("jobs", jobs);

        // Forward the request to the JSP page to display the jobs
        RequestDispatcher dispatcher = request.getRequestDispatcher("/caretaker/view_jobs.jsp");
        dispatcher.forward(request, response);
    }
}
