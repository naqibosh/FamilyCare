/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.Customer;
import userDAO.CustomerDAO;
import utils.SessionUtils;

/**
 *
 * @author hazik
 */
public class CustomerManageServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login.html?error=invalidSession";
    private static final String CUSTOMER_LIST_PAGE = "/staff/manage-customer.jsp";
    private static final String CUSTOMER_DETAILS_PAGE = "/staff/customer-details.jsp";

    // You might want a logging framework instead of printStackTrace  
    private static final Logger logger = Logger.getLogger(CustomerManageServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Integer staffId = SessionUtils.getUserIdFromSession(request);
        if (staffId == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        String action = request.getParameter("action");
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            switch (action) {
                case "viewCustList":
                    handleViewCustomerList(request, response, customerDAO);
                    break;
                case "viewCustInfo":
                    handleViewCustomerInfo(request, response, customerDAO);
                    break;
                case "updateCust":
                    handleUpdateCustomer(request, response, customerDAO);
                    break;
                case "disableCust":
                    handleDisableCustomer(request, response, customerDAO);
                    break;
                case "enableCust":
                    handleEnableCustomer(request, response, customerDAO);
                    break;
                case "editCust":
                    handleEditCustomer(request, response, customerDAO);
                    break;
                default:
                    sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleViewCustomerList(HttpServletRequest request, HttpServletResponse response, CustomerDAO customerDAO)
            throws ServletException, IOException {
        List<Customer> customerList = customerDAO.getAllCustomer();
        request.setAttribute("customerList", customerList);
        request.getRequestDispatcher(CUSTOMER_LIST_PAGE).forward(request, response);
    }

    private void handleViewCustomerInfo(HttpServletRequest request, HttpServletResponse response, CustomerDAO customerDAO)
            throws ServletException, IOException, SQLException {
        try {
            int custId = parseCustId(request);
            Customer customer = customerDAO.getCustInfo(custId);
            if (customer == null) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Customer with ID " + custId + " not found.");
                return;
            }
            request.setAttribute("customer", customer);
            request.getRequestDispatcher(CUSTOMER_DETAILS_PAGE).forward(request, response);
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID.");
        }
    }

    private void handleUpdateCustomer(HttpServletRequest request, HttpServletResponse response, CustomerDAO customerDAO)
            throws ServletException, IOException {
        try {
            int custId = parseCustId(request);
            Customer customer = createCustomerFromRequest(request, custId);

            if (customerDAO.updateCust(customer)) {
                response.sendRedirect("customer_manageProcess.jsp?action=custList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to update customer with ID " + custId);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleDisableCustomer(HttpServletRequest request, HttpServletResponse response, CustomerDAO customerDAO)
            throws ServletException, IOException {
        try {
            int custId = parseCustId(request);

            if (customerDAO.disableCust(custId)) {
                response.sendRedirect("customer_manageProcess.jsp?action=custList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to disable customer with ID " + custId);
            }
        } catch (NumberFormatException ex) {

            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID.");
        }

    }

    private void handleEnableCustomer(HttpServletRequest request, HttpServletResponse response, CustomerDAO customerDAO)
            throws ServletException, IOException {
        try {
            int custId = parseCustId(request);

            if (customerDAO.enableCust(custId)) {
                response.sendRedirect("customer_manageProcess.jsp?action=custList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to enable customer with ID " + custId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID.");
        }

    }

    private void handleEditCustomer(HttpServletRequest request, HttpServletResponse response, CustomerDAO customerDAO)
            throws ServletException, IOException {
        try {
            int custId = parseCustId(request);
            int statusId = Integer.parseInt(request.getParameter("statusId"));

            Timestamp banDate = null;
            if (statusId != 1) { // If status is not "Good"  
                banDate = new Timestamp(System.currentTimeMillis()); // Current timestamp  
            }

            // Call the editCust method with the appropriate parameters  
            if (customerDAO.editCust(custId, banDate, statusId)) {
                response.sendRedirect("customer_manageProcess.jsp?action=custList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to update customer status for ID " + custId);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private int parseCustId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("custId"));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid customer ID format", e);
            throw e; // Re-throw to handle elsewhere  
        }
    }

    private Customer createCustomerFromRequest(HttpServletRequest request, int custId) {
        Customer customer = new Customer();
        customer.setCustId(custId);
        customer.setCustUsername(request.getParameter("custUsername"));
        customer.setCustPass(request.getParameter("custPassword"));
        customer.setCustFName(request.getParameter("custFName"));
        customer.setCustLName(request.getParameter("custLName"));
        customer.setCustPhone(request.getParameter("custPhone"));
        customer.setCustEmail(request.getParameter("custEmail"));
        customer.setCustIC(request.getParameter("custIC"));
        return customer;
    }

    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);
    }

    private void handleException(HttpServletResponse response, Exception ex) throws IOException {
        logger.log(Level.SEVERE, "Exception occurred", ex);
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
        return "Customer management servlet handling CRUD operations based on action.";
    }
}
