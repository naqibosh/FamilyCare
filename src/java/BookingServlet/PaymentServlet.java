package BookingServlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // Allow up to 10MB file size
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Get parameters from request
            HttpSession session = request.getSession();
            String paymentMethod = request.getParameter("paymentMethod");
            String paymentDateStr = request.getParameter("paymentDate"); 
            String paymentAmountStr = request.getParameter("paymentAmount");

            // Retrieve booking and customer details
            Integer bookingId = (Integer) session.getAttribute("bookingId");
            Integer custId = (Integer) session.getAttribute("custId");

            if (bookingId == null || custId == null) {
                response.getWriter().println("Error: Missing booking or customer details. Please ensure your session is active.");
                return;
            }

            // Convert payment date format to Oracle TIMESTAMP
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat dbFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a");
            Date paymentDate = inputFormat.parse(paymentDateStr);
            String formattedPaymentDate = dbFormat.format(paymentDate);

            // Convert payment amount
            double paymentAmount = Double.parseDouble(paymentAmountStr);

            // Retrieve the uploaded file (if exists)
            Part filePart = request.getPart("paymentProof");
            InputStream fileContent = null;

            if (filePart != null && filePart.getSize() > 0) {
                fileContent = filePart.getInputStream(); // Read uploaded image as input stream
            }

            // Establish database connection
            conn = DatabaseConnection.getConnection();

            // SQL query to insert payment with BLOB support
            String sql = "INSERT INTO payment (PAYMENT_DATE, PAYMENT_STATUS, PAYMENT_METHOD, PAYMENT_AMOUNT, PAYMENT_RECEIPT, BOOKING_ID, CUST_ID, STAFF_ID) "
                    + "VALUES (TO_TIMESTAMP(?, 'DD-MON-YY HH.MI.SS.FF AM'), ?, ?, ?, ?, ?, ?, NULL)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, formattedPaymentDate);
            pstmt.setString(2, "Pending");
            pstmt.setString(3, paymentMethod);
            pstmt.setDouble(4, paymentAmount);

            if (fileContent != null) {
                pstmt.setBlob(5, fileContent); // Store image in BLOB column
            } else {
                pstmt.setNull(5, java.sql.Types.BLOB); // If no image, set as NULL
            }

            pstmt.setInt(6, bookingId);
            pstmt.setInt(7, custId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("customer/bookingsuccess.jsp"); // Redirect after success
            } else {
                response.getWriter().println("Error processing payment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
