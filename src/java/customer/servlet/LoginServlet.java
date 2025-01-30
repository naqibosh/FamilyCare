package customer.servlet;  

import dbconn.DatabaseConnection;  
import java.io.IOException;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import javax.servlet.ServletException;  
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
            String sql = "SELECT cust_id, cust_username, cust_password FROM customer WHERE cust_username = ?";  
            PreparedStatement stmt = conn.prepareStatement(sql);  
            stmt.setString(1, username);  

            ResultSet rs = stmt.executeQuery();  

            if (rs.next()) {  
                String storedPassword = rs.getString("cust_password");  

                // Check if the entered password matches the stored password  
                if (password.equals(storedPassword)) {  
                    // Credentials are valid; create a session  
                    HttpSession session = request.getSession();  
                    session.setAttribute("customerId", rs.getInt("cust_id"));  
                    session.setAttribute("customerName", rs.getString("cust_username"));  

                    // Redirect to home page  
                    response.sendRedirect("customer/home.jsp");  
                } else {  
                    // Invalid password; redirect to login page with an error message  
                    response.sendRedirect("login.jsp?error=Invalid username or password.");  
                }  
            } else {  
                // Invalid username; redirect to login page with an error message  
                response.sendRedirect("login.jsp?error=Invalid username or password.");  
            }  
        } catch (Exception e) {  
            // Log the error and show a generic error message  
            e.printStackTrace();  
            response.sendRedirect("login.jsp?error=An error occurred. Please try again later.");  
        }  
    }  
}