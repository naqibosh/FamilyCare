package staff.servlet;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.*;
import jbcrypt.BCrypt;
import utils.SessionUtils;

/**
 *
 * @author hazik
 */
public class StaffRegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Check if user is already logged in
            Integer userId = SessionUtils.getUserIdFromSession(request);
            if (userId != null) {
                // Retrieve Staff object from request
                Staff staff = (Staff) request.getAttribute("staff");
                StaffDAO staffDAO = new StaffDAO();
                String userRole = staffDAO.getUserRole(userId);

                // Only allow Administrators and Managers to register new staff
                if (!userRole.equals("Administrator") && !userRole.equals("Manager")) {
                    response.sendRedirect("access-denied.html"); // Redirect to access denied page
                    return;
                } else {
                    // Check if email already exists
                    if (staffDAO.isStaffEmailExists(staff.getEmail())) {
                        response.sendRedirect("register.html?error=3"); // Email already exists
                        return;
                    }

                    // Hash the password using BCrypt
                    String hashedPassword = BCrypt.hashpw(staff.getPassword(), BCrypt.gensalt());
                    staff.setPassword(hashedPassword);

                    // Insert the staff into the database
                    int rowsInserted = staffDAO.insertStaff(staff);

                    if (rowsInserted > 0) {
                        // Registration successful
                        response.sendRedirect("staff_dashboard.jsp?status=success");
                    } else {
                        // Registration failed
                        response.sendRedirect("register.html?error=1");
                    }
                }
            }

        } catch (SQLException ex) {
            logger.severe("Error registering staff: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to register staff.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
