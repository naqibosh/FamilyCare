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

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "CareGiver";
    private static final String DB_PASSWORD = "system";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> jobs = new ArrayList<>();
        String message = null;
        String error = null;

        String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "CareGiver";
        String dbPassword = "system";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT c.customer_name, c.customer_phone, b.booking_time, b.booking_duration, b.booking_price "
                    + "FROM CUSTOMER c "
                    + "JOIN BOOKING b ON c.cust_id = b.cust_id";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> job = new HashMap<>();
                    job.put("customerName", rs.getString("customer_name"));
                    job.put("customerPhone", rs.getString("customer_phone"));
                    job.put("bookingTime", rs.getString("booking_time"));
                    job.put("bookingDuration", rs.getInt("booking_duration"));
                    job.put("bookingPrice", rs.getDouble("booking_price"));
                    jobs.add(job);
                }

                if (jobs.isEmpty()) {
                    message = "No jobs assigned.";
                }

            } catch (Exception e) {
                error = "Error fetching job data: " + e.getMessage();
            }
        } catch (Exception e) {
            error = "Database connection error: " + e.getMessage();
        }

        request.setAttribute("jobs", jobs);
        request.setAttribute("message", message);
        request.setAttribute("error", error);
        request.getRequestDispatcher("/view_jobs.jsp").forward(request, response);
    }
}
