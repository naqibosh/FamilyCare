
package staff.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jbcrypt.BCrypt;
import user.StaffDAO;
import user.Staff;

/**
 *
 * @author hazik

public class StaffServlet extends HttpServlet {
    public static final long serialVersionUID = 1L;
    private StaffDAO staffDAO;

    public StaffServlet() {
        this.staffDAO = new StaffDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getServletPath();

        switch (action) {
            case "/new":
                showRegisterForm(request, response);
                break;

            case "/update":
                updateStaff(request, response);
                break;

            case "/delete":
                deleteStaff(request, response);
                break;

            case "/getStaffList":
                getStaffList(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "The requested action is not available.");
                break;
        }
    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.html").forward(request, response);
    }

    private void updateStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = request.getParameter("role");

        Staff updatedStaff = new Staff(id, name, email, password, phoneNumber, role);
        staffDAO.updateStaff(updatedStaff);

        response.sendRedirect("getStaffList");
    }

    private void deleteStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        staffDAO.deleteStaff(id);

        response.sendRedirect("getStaffList");
    }

    private void getStaffList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Staff> staffList = staffDAO.getAllStaff();
        request.setAttribute("staffList", staffList);

        request.getRequestDispatcher("manage-staff.jsp").forward(request, response);
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
 */