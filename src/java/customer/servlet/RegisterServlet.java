/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String icNumber = request.getParameter("ic_number");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        System.out.println("Received registration details:");
        System.out.println("Username: " + username);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("IC Number: " + icNumber);

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.jsp?error=Passwords do not match!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connection established successfully.");
            String sql = "INSERT INTO CUSTOMER (CUST_USERNAME, CUST_FIRST_NAME, CUST_LAST_NAME, CUST_PHONE_NUMBER, CUST_EMAIL, CUST_IDENTIFICATION_NUMBER, CUST_PASSWORD, STATUS_ID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, icNumber);
            pstmt.setString(7, password);

            int rows = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rows);
            if (rows > 0) {
                response.sendRedirect("customer/login.jsp?success=Registration successful!");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=" + e.getMessage());
        }
    }

}
