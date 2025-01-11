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
import java.sql.SQLException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import jbcrypt.BCrypt; //import from hashing package

//@WebServlet("/StaffRegisterServlet")
public class StaffRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String DB_USER = "CareGiver";
    private static final String DB_PASSWORD = "CareGiver";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = (String) request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");

        // Basic validation (optional)
        if (name == null || email == null || password == null
                || phoneNumber == null || role == null) {
            out.println("Error: Please fill out all required fields.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());  // Hash the password
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO staff (staff_name, staff_password, staff_email, staff_phone_number, staff_role) " + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, hashedPassword);
                statement.setString(3, email);
                statement.setString(4, phoneNumber);
                statement.setString(5, role);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    out.println("Registration successful!"); //debug
                    // Redirect to staff_dashboard.jsp
                    response.sendRedirect("staff_dashboard.jsp?status=success");
                } else {
                    //response.sendRedirect("register.html?error=2+servlet");
                    out.println("Error: User registration failed."); //debug
                }
            } catch (Exception e) {
                e.printStackTrace(out);
                out.println("Error: Database connection failed."); //debug
                // Redirect back to the login page on failure
                //response.sendRedirect("register.html?error=2+servlet");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported.");
    }
}
