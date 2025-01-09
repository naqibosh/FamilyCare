package com.familycare;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Mock database: Hardcoded username-password pairs
    private static final Map<String, String> mockUsers = new HashMap<>();

    static {
        mockUsers.put("user1", "password1");
        mockUsers.put("user2", "password2");
        mockUsers.put("admin", "admin123");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve username and password from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate credentials
        if (mockUsers.containsKey(username) && mockUsers.get(username).equals(password)) {
            // Create a session for the user
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Redirect to customer home page
            response.sendRedirect("customer/customer_home.jsp");
        } else {
            // Redirect back to login page with an error message
            response.sendRedirect("login.html?error=Invalid username or password");
        }
    }
}
