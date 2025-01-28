package staff.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.Staff;
import user.StaffDAO;
import utils.SessionUtils;

/**
 *
 * @author hazik
 */
public class StaffManageServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login.html?error=invalidSession";
    private static final String ERROR_PAGE = "/error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Integer staffId = SessionUtils.getUserIdFromSession(request);
        if (staffId == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        String action = request.getParameter("action");
        StaffDAO staffDAO = new StaffDAO();

        try {
            switch (action) {
                case "viewStaffList":
                    handleViewStaffList(request, response, staffDAO);
                    break;
                case "viewStaffInfo":
                    handleViewStaffInfo(request, response, staffDAO);
                    break;
                case "updateStaff":
                    handleUpdateStaff(request, response, staffDAO);
                    break;
                case "deleteStaff":
                    handleDeleteStaff(request, response, staffDAO);
                    break;
                case "editStaff":
                    handleEditStaff(request, response, staffDAO);
                    break;
                case "disableStaff":
                    handleDisableStaff(request, response, staffDAO);
                    break;
                case "enableStaff":
                    handleEnableStaff(request, response, staffDAO);
                    break;
                default:
                    sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleViewStaffList(HttpServletRequest request, HttpServletResponse response, StaffDAO staffDAO)
            throws ServletException, IOException, SQLException {
        Integer staffId = SessionUtils.getUserIdFromSession(request);
        String userRole = staffDAO.getUserRole(staffId);

        // Check if user role is neither Administrator nor Manager  
        if (!"Administrator".equals(userRole) && !"Manager".equals(userRole)) {
            response.sendRedirect("staff_dashboard.jsp?success=false&errcode=4");
            return;
        }

        // Proceed to get staff list if authorized  
        List<Staff> staffList = staffDAO.getAllStaff();
        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("/staff/manage-staff.jsp").forward(request, response);
    }

    private void handleViewStaffInfo(HttpServletRequest request, HttpServletResponse response, StaffDAO staffDAO)
            throws ServletException, IOException, SQLException {
        try {
            int staffId = parseStaffId(request);
            Staff staff = staffDAO.getStaffInfo(staffId);
            if (staff == null) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Staff with ID " + staffId + " not found.");
                return;
            }
            request.setAttribute("staff", staff);
            request.getRequestDispatcher("/staff/staff-details.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid staff ID.");
        }
    }

    private void handleUpdateStaff(HttpServletRequest request, HttpServletResponse response, StaffDAO staffDAO)
            throws ServletException, IOException {
        try {
            int staffId = parseStaffId(request);
            Staff staff = createStaffFromRequest(request, staffId);
            if (staffDAO.updateStaff(staff)) {
                response.sendRedirect("/staff/staff_dashboard.jsp");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to update staff with ID " + staffId);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleEditStaff(HttpServletRequest request, HttpServletResponse response, StaffDAO staffDAO)
            throws ServletException, IOException {
        try {
            int staffId = parseStaffId(request);
            String staffRole = request.getParameter("staffRole");
            int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));

            if (staffRole == null || staffRole.isEmpty()) {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Role is missing.");
                return;
            }

            if (staffDAO.isSupervisorExists(supervisorId)) {
                if (staffDAO.editStaff(staffId, staffRole, supervisorId)) {
                    response.sendRedirect("staff_manageProcess.jsp?action=staffList&success=true");
                } else {
                    response.sendRedirect("staff_manageProcess.jsp?action=staffList&success=false&errcode=1");
//                    sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to update staff.");  
                }
            } else {
                response.sendRedirect("staff_manageProcess.jsp?action=staffList&success=false&errcode=2");
//                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Supervisor not found.");  
            }
        } catch (NumberFormatException ex) {
            response.sendRedirect("staff_manageProcess.jsp?action=staffList&success=false&errcode=3");
//            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid staff ID or supervisor ID.");  
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleDeleteStaff(HttpServletRequest request, HttpServletResponse response, StaffDAO staffDAO)
            throws ServletException, IOException {
        try {
            int staffId = parseStaffId(request);
            if (staffDAO.deleteStaff(staffId)) {
                response.sendRedirect("staff_manageProcess.jsp?action=staffList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to delete staff with ID " + staffId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid staff ID.");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleDisableStaff(HttpServletRequest request, HttpServletResponse response, StaffDAO staffDAO)
            throws ServletException, IOException {
        try {
            int staffId = parseStaffId(request);
            if (staffDAO.disableStaff(staffId)) {
                response.sendRedirect("staff_manageProcess.jsp?action=staffList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to disable staff with ID " + staffId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid staff ID.");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleEnableStaff(HttpServletRequest request, HttpServletResponse response, StaffDAO staffDAO)
            throws ServletException, IOException {
        try {
            int staffId = parseStaffId(request);
            if (staffDAO.enableStaff(staffId)) {
                response.sendRedirect("staff_manageProcess.jsp?action=staffList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to enable staff with ID " + staffId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid staff ID.");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private int parseStaffId(HttpServletRequest request) throws NumberFormatException {
        return Integer.parseInt(request.getParameter("staffId"));
    }

    private Staff createStaffFromRequest(HttpServletRequest request, int staffId) {
        Staff staff = new Staff();
        staff.setId(staffId);
        staff.setName(request.getParameter("name"));
        staff.setEmail(request.getParameter("email"));
        staff.setPhoneNumber(request.getParameter("phoneNumber"));
        staff.setRole(request.getParameter("role"));
        return staff;
    }

    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);
    }

    private void handleException(HttpServletResponse response, Exception ex) throws IOException {
        ex.printStackTrace();  // Adjust this for actual logging  
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
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
        return "Staff management servlet handling CRUD operations based on action.";
    }
}
