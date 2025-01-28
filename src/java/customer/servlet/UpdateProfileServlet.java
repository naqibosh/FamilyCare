package customer.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconn.DatabaseConnection;

public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve session and check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect("login.jsp?error=Please log in to update your profile.");
            return;
        }

        int customerId = (Integer) session.getAttribute("customerId");

        // Retrieve form parameters
        String fullName = request.getParameter("fullName").trim();
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();

        // Split full name into first and last name
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Update the customer's profile in the database
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE CUSTOMER SET CUST_FIRST_NAME = ?, CUST_LAST_NAME = ?, CUST_USERNAME = ?, CUST_EMAIL = ?, CUST_PHONE_NUMBER = ? WHERE CUST_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, username);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setInt(6, customerId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Display success message and redirect to profile page
                response.setContentType("text/html");
                response.getWriter().write("<script>");
                response.getWriter().write("alert('Changes Made Was Successfully!');");
                response.getWriter().write("window.location.href = 'customer/profile.jsp';");
                response.getWriter().write("</script>");
            } else {
                // Redirect to edit profile page with error message
                response.sendRedirect("customer/edit_profile.jsp?error=Unable to update profile. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=An error occurred while updating your profile.");
        } finally {
            // Ensure resources are closed properly
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}