package customer.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate input fields
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=Please fill in all fields.");
            return;
        }

        // Attempt to connect to the database and validate credentials
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT cust_id, cust_username FROM customer WHERE cust_username = ? AND cust_password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Credentials are valid; create a session
                HttpSession session = request.getSession();
                session.setAttribute("customerId", rs.getInt("cust_id")); // Assuming 'id' column exists in the database
                session.setAttribute("customerName", rs.getString("cust_username")); // Assuming 'name' column exists

                // Redirect to home page
                response.sendRedirect("customer/home.jsp");
            } else {
                // Invalid credentials; redirect to login page with an error message
                response.sendRedirect("login.jsp?error=Invalid username or password.");
            }
        } catch (Exception e) {
            // Log the error and show a generic error message
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=An error occurred. Please try again later.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp"); // Redirect GET requests to the login page
    }
}
