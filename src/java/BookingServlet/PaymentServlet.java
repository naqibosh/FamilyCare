package BookingServlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Get parameters from request
            HttpSession session = request.getSession();
            String paymentMethod = request.getParameter("paymentMethod");
            String paymentDateStr = request.getParameter("paymentDate"); // From input type="datetime-local"
            String paymentAmountStr = request.getParameter("paymentAmount");

            // Retrieve BOOKING_ID and CUST_ID from session
            Integer bookingId = (Integer) session.getAttribute("bookingId");
            Integer custId = (Integer) session.getAttribute("custId");

            if (bookingId == null || custId == null) {
                response.getWriter().println("Error: Missing booking or customer details. Please ensure your session is active.");
                return;
            }

            // Convert payment date from datetime-local format to Oracle TIMESTAMP format
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); // Matches datetime-local input
            SimpleDateFormat dbFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a"); // Matches BOOKING_TIME format
            Date paymentDate = inputFormat.parse(paymentDateStr);
            String formattedPaymentDate = dbFormat.format(paymentDate);

            // Convert payment amount to double
            double paymentAmount = Double.parseDouble(paymentAmountStr);

            // Establish database connection (use getConnection instead of initializeDatabase)
            conn = DatabaseConnection.getConnection();

            // Insert payment record into the database
            String sql = "INSERT INTO payment (PAYMENT_DATE, PAYMENT_STATUS, PAYMENT_METHOD, PAYMENT_AMOUNT, PAYMENT_RECEIPT, BOOKING_ID, CUST_ID, STAFF_ID) "
                    + "VALUES (TO_TIMESTAMP(?, 'DD-MON-YY HH.MI.SS.FF AM'), ?, ?, ?, NULL, ?, ?, NULL)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, formattedPaymentDate);
            pstmt.setString(2, "Pending"); // Default status
            pstmt.setString(3, paymentMethod);
            pstmt.setDouble(4, paymentAmount);
            pstmt.setInt(5, bookingId);
            pstmt.setInt(6, custId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("customer/bookingsuccess.jsp"); // Redirect after successful payment entry
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
