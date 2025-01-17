package staff.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.Staff;
import user.StaffDAO;

/**
 *
 * @author hazik
 */
public class StaffManageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            StaffDAO staffDAO = new StaffDAO();
            List<Staff> staffList = staffDAO.getAllStaff();
            System.out.println("StaffManageServlet triggered.");
            // Print staffList to debug the fetched data
//            if (staffList != null && !staffList.isEmpty()) {
//                System.out.println("Fetched staff list:");
//                for (Staff staff : staffList) {
//                    System.out.println(staff);
//                }
//            } else {
//                System.out.println("No staff data found or staffList is null.");
//            }

            request.setAttribute("staffList", staffList);
//            System.out.println("Forwarding to manage-staff.jsp");
            request.getRequestDispatcher("/staff/manage-staff.jsp").forward(request, response);
        } catch (Exception ex) {
            // Handle exceptions (e.g., log the error, display an error message)
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving staff data.");
            request.getRequestDispatcher("/staff/404.html").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
