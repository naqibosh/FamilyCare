/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.Payment;
import userDAO.PaymentDAO;
import utils.SessionUtils;

/**
 *
 * @author hazik
 */
public class PaymentManageServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login.html?error=invalidSession";
    private static final String PAYMENT_LIST_PAGE = "/staff/manage-payment.jsp";
    private static final String PAYMENT_DETAILS_PAGE = "/staff/payment-details.jsp";

    private static final Logger logger = Logger.getLogger(PaymentManageServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Integer staffId = SessionUtils.getUserIdFromSession(request);
        if (staffId == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        String action = request.getParameter("action");
        PaymentDAO paymentDAO = new PaymentDAO();

        try {
            switch (action) {
                case "paymentList":
                    handleViewPaymentList(request, response, paymentDAO);
                    break;
                case "viewPaymentInfo":
                    handleViewPaymentInfo(request, response, paymentDAO);
                    break;
                case "updatePayment":
                    handleUpdatePayment(request, response, paymentDAO);
                    break;
                case "deletePayment":
                    handleDeletePayment(request, response, paymentDAO);
                    break;
                case "insertPayment":
                    handleInsertPayment(request, response, paymentDAO);
                    break;
                case "editPayment":
                    handleEditPayment(request, response, paymentDAO);
                    break;
                case "previewPayment":
                    handlePreviewPayment(request, response, paymentDAO);
                    break;
                default:
                    sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleViewPaymentList(HttpServletRequest request, HttpServletResponse response, PaymentDAO paymentDAO)
            throws ServletException, IOException {
        try {
            List<Payment> paymentList = paymentDAO.selectAllPayments();
            request.setAttribute("paymentList", paymentList);
            request.getRequestDispatcher(PAYMENT_LIST_PAGE).forward(request, response);
        } catch (SQLException e) {
            handleException(response, e);
        }
    }

    private void handleViewPaymentInfo(HttpServletRequest request, HttpServletResponse response, PaymentDAO paymentDAO)
            throws ServletException, IOException {
        try {
            int paymentId = parsePaymentId(request);
            Payment payment = paymentDAO.selectPaymentById(paymentId);
            if (payment == null) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Payment with ID " + paymentId + " not found.");
                return;
            }
            request.setAttribute("payment", payment);
            request.getRequestDispatcher(PAYMENT_DETAILS_PAGE).forward(request, response);
        } catch (NumberFormatException | SQLException ex) {
            handleException(response, ex);
        }
    }

    private void handleUpdatePayment(HttpServletRequest request, HttpServletResponse response, PaymentDAO paymentDAO)
            throws ServletException, IOException {
        try {
            int paymentId = parsePaymentId(request);
            Payment payment = createPaymentFromRequest(request, paymentId);

            paymentDAO.updatePayment(payment);
            response.sendRedirect("payment_manageProcess.jsp?action=paymentList&success=true");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleDeletePayment(HttpServletRequest request, HttpServletResponse response, PaymentDAO paymentDAO)
            throws ServletException, IOException {
        try {
            int paymentId = parsePaymentId(request);

            paymentDAO.deletePayment(paymentId);
            response.sendRedirect("payment_manageProcess.jsp?action=paymentList&success=true");
        } catch (SQLException | NumberFormatException ex) {
            handleException(response, ex);
        }
    }

    private void handleInsertPayment(HttpServletRequest request, HttpServletResponse response, PaymentDAO paymentDAO)
            throws ServletException, IOException {
        try {
            Payment payment = createPaymentFromRequest(request, 0);

            paymentDAO.insertPayment(payment);
            response.sendRedirect("payment_manageProcess.jsp?action=paymentList&success=true");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleEditPayment(HttpServletRequest request, HttpServletResponse response, PaymentDAO paymentDAO)
            throws ServletException, IOException {
        try {
            // Parse the paymentId and new paymentStatus from the request
            String paymentId = request.getParameter("paymentId");
            String paymentStatus = request.getParameter("status");

            // Validate input
            if (paymentId == null || paymentStatus == null || paymentId.trim().isEmpty() || paymentStatus.trim().isEmpty()) {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Payment ID and status must not be empty.");
                return;
            }

            // Call the DAO method to update the payment status
            boolean isUpdated = paymentDAO.editPayment(paymentId, paymentStatus);

            if (isUpdated) {
                // Redirect to the payment list with a success message
                response.sendRedirect("payment_manageProcess.jsp?action=paymentList&success=true");
            } else {
                // Handle case where update failed (e.g., payment ID not found)
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Payment with ID " + paymentId + " not found.");
            }
        } catch (SQLException ex) {
            handleException(response, ex);
        }
    }
    
        protected void handlePreviewPayment(HttpServletRequest request, HttpServletResponse response, PaymentDAO paymentDAO)
            throws ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));

        InputStream inputStream = paymentDAO.getReceiptById(paymentId);
        if (inputStream != null) {
            // Detect file type (assuming it's stored with correct format)
            String contentType = "application/octet-stream";
            String fileName = "receipt_" + paymentId;

            byte[] fileHeader = new byte[4];
            inputStream.read(fileHeader, 0, 4);

            String headerHex = bytesToHex(fileHeader);

            if (headerHex.startsWith("25504446")) {
                contentType = "application/pdf";
                fileName += ".pdf";
            } else if (headerHex.startsWith("89504E47")) {
                contentType = "image/png";
                fileName += ".png";
            } else if (headerHex.startsWith("FFD8FF")) {
                contentType = "image/jpeg";
                fileName += ".jpg";
            }

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);

            // Reset input stream to ensure all content is read
            inputStream = PaymentDAO.getReceiptById(paymentId);

            try (OutputStream output = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } finally {
                inputStream.close(); // Close the stream after writing to response
            }
        } else {
            response.setContentType("text/html");
            response.getWriter().write("<h3>No certification found for this caretaker.</h3>");
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    private int parsePaymentId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("paymentId"));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid payment ID format", e);
            throw e;
        }
    }

    private Payment createPaymentFromRequest(HttpServletRequest request, int paymentId) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentId);
        payment.setBookingId(Integer.parseInt(request.getParameter("bookingId")));
        payment.setAmount(Double.parseDouble(request.getParameter("paymentAmount")));
        payment.setDate(request.getParameter("paymentDate"));
        payment.setMethod(request.getParameter("paymentMethod"));
        payment.setStaffId(Integer.parseInt(request.getParameter("staffId")));
        return payment;
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
        return "Payment management servlet handling CRUD operations based on action.";
    }

}
