/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.SessionUtils;
import user.StaffDAO;

public class StaffLoginServlet extends HttpServlet {

    private StaffDAO staffDAO = new StaffDAO();
    int userId = 0;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check if the email exists in the database  
        try {
            if (!staffDAO.isStaffEmailExists(email)) {
                response.sendRedirect("login.html?error=3"); // Invalid email   
                return;
            }

            // Logic to authenticate the user using email and password  
            Optional<Integer> userIdOpt = staffDAO.authenticateUser(email, password);
            if (!userIdOpt.isPresent()) {
                response.sendRedirect("login.html?error=3"); // Invalid password  
            } else {
                int userId = userIdOpt.get();
                HttpSession session = SessionUtils.createSession(request, userId);
                response.sendRedirect("staff_dashboard.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception   
            response.sendRedirect("login.html?error=2"); // Generic error for any SQL problems  
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
