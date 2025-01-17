/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jbcrypt.BCrypt; //import from hashing package
import utils.SessionUtils;

public class StaffLoginServlet extends HttpServlet {

    // Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String DB_USER = "CareGiver";
    private static final String DB_PASSWORD = "system";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String staff_email = request.getParameter("email");
        String staff_password = request.getParameter("password");

        try {
            if (staff_email == null || staff_password == null || staff_email.isEmpty() || staff_password.isEmpty()) {
                System.out.println("Error: Empty fields.");
                response.sendRedirect("login.html?error=1");
                return;
            }

            boolean isValidUser = false;
            int userId = 0;

            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Debug: Check if connection details are correct
            System.out.println("Connecting to database...");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                System.out.println("Database connected successfully.");

                // Prepare SQL query
                String query = "SELECT staff_id, staff_password FROM staff WHERE staff_email = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, staff_email);

                    // Debug: Show the query being executed
                    System.out.println("Executing query: " + statement);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            userId = resultSet.getInt("staff_id");
                            String hashedPasswordFromDB = resultSet.getString("staff_password").trim();

                            // Debug: Check the retrieved password hash
                            System.out.println("Retrieved hashed password: " + hashedPasswordFromDB);

                            // Validate entered password with hashed password
                            isValidUser = BCrypt.checkpw(staff_password, hashedPasswordFromDB);
                        } else {
                            System.out.println("Error: No user found with the given email.");
                        }
                    }
                }
            }

            if (isValidUser) {
                // Debug: Valid user login
                System.out.println("User authenticated successfully. User ID: " + userId);

                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);

                // Encrypt session ID using BCrypt
                String sessionId = session.getId();
                String encryptedSessionId = BCrypt.hashpw(sessionId, BCrypt.gensalt());

                // Redirect to dashboard
                response.sendRedirect("staff_dashboard.jsp?sessionId=" + encryptedSessionId);
            } else {
                // Debug: Invalid credentials
                System.out.println("Error: Invalid credentials.");
                response.sendRedirect("login.html?error=3");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Oracle JDBC Driver not found.");
            e.printStackTrace();
            response.sendRedirect("login.html?error=2");
        } catch (SQLException e) {
            System.out.println("Error: Database connection or query execution failed.");
            e.printStackTrace();
            response.sendRedirect("login.html?error=2");
        } catch (Exception e) {
            System.out.println("Error: Unexpected exception occurred.");
            e.printStackTrace();
            response.sendRedirect("login.html?error=2");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
