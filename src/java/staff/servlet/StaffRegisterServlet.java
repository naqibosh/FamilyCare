package staff.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;
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

    private static final Logger logger = Logger.getLogger(StaffRegisterServlet.class.getName());

    private static final String ACCESS_DENIED = "staff_dashboard.jsp?success=false&errcode=5";
    private static final String REGISTER_PAGE = "register.html";
    private static final String SUCCESS_REDIRECT = "staff_dashboard.jsp?status=success";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            Integer userId = SessionUtils.getUserIdFromSession(request);
            if (userId == null) {
                response.sendRedirect(ACCESS_DENIED);
                return;
            }

            if (!hasPermissionToRegister(userId)) {
                response.sendRedirect(ACCESS_DENIED);
                return;
            }

            Staff staff = (Staff) request.getAttribute("staff");
            if (staff == null) {
                // Handle the case where staff is not present  
                response.sendRedirect(REGISTER_PAGE + "?error=invalid_data");
                return;
            }

            if (isEmailExists(staff.getEmail())) {
                response.sendRedirect(REGISTER_PAGE + "?error=3"); // Email already exists  
                return;
            }

            registerStaff(staff);
            response.sendRedirect(SUCCESS_REDIRECT);

        } catch (SQLException ex) {
            logger.severe("Error registering staff: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to register staff.");
        }
    }

    private boolean hasPermissionToRegister(Integer userId) throws SQLException {
        StaffDAO staffDAO = new StaffDAO();
        String userRole = staffDAO.getUserRole(userId);
        return userRole.equals("Administrator") || userRole.equals("Manager") || userRole.equals("Supervisor");
    }

    private boolean isEmailExists(String email) throws SQLException {
        StaffDAO staffDAO = new StaffDAO();
        return staffDAO.isStaffEmailExists(email);
    }

    private void registerStaff(Staff staff) throws SQLException {
        String hashedPassword = BCrypt.hashpw(staff.getPassword(), BCrypt.gensalt());
        staff.setPassword(hashedPassword);
        StaffDAO staffDAO = new StaffDAO();
        int rowsInserted = staffDAO.insertStaff(staff);
        if (rowsInserted <= 0) {
            throw new SQLException("Failed to insert staff record.");
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
