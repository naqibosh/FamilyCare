/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import jbcrypt.BCrypt; //import from hashing package

//@WebServlet("/StaffLoginServlet")
public class StaffLoginServlet extends HttpServlet {

    // Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String DB_USER = "CareGiver";
    private static final String DB_PASSWORD = "CareGiver";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String staff_email = request.getParameter("email");
        String staff_password = request.getParameter("password");

        try (PrintWriter out = response.getWriter()) {
            if (staff_email == null || staff_password == null || staff_email.isEmpty() || staff_password.isEmpty()) {
                out.println("<p>Username or password cannot be empty. Please try again.</p>"); //debug
                return;
            }

            boolean isValidUser = false;

            // Database validation logic
            try {
                Class.forName("oracle.jdbc.OracleDriver");

                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement statement = connection.prepareStatement(
                             "SELECT staff_password FROM staff WHERE staff_email = ?")) {

                    statement.setString(1, staff_email);

                    System.out.println("Executing query with email: " + staff_email); // Debug

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            String hashedPasswordFromDB = resultSet.getString("staff_password");

                            // Validate entered password with hashed password
                            isValidUser = BCrypt.checkpw(staff_password, hashedPasswordFromDB);
                        }
                    }
                }
            } catch (Exception e) {
                out.println("<p>Error connecting to the database: " + e.getMessage() + "</p>");
                e.printStackTrace(); // Log exception to server logs
                return;
            }

            if (isValidUser) {
                // Generate a session and encrypted session ID
                HttpSession session = request.getSession();
                session.setAttribute("email", staff_email);

                String sessionId = session.getId();
                String encryptedSessionId = Base64.getEncoder().encodeToString(sessionId.getBytes());

                // Redirect to staffhome.html with the encrypted session ID
                response.sendRedirect("staff_dashboard.jsp?sessionId=" + encryptedSessionId);
            } else {
                // Redirect back to the login page on failure
                response.sendRedirect("login.html?error=Invalid+email+or+password");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported.");
    }
}