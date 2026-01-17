package userDAO;

import userDAO.StaffDAO;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import user.Payment;
import dbconn.DatabaseConnection;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author hazik
 */
public class PaymentDAO {

    private static final String INSERT_PAYMENT_SQL = "INSERT INTO payment (payment_date, payment_status, payment_amount, payment_method, payment_receipt, booking_id, cust_id, staff_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PAYMENT_SQL = "UPDATE payment SET payment_date = ?, payment_status = ?, payment_amount = ?, payment_method = ?, payment_receipt = ?, booking_id = ?, cust_id = ?, staff_id = ? WHERE payment_id = ?";
    private static final String DELETE_PAYMENT_SQL = "DELETE FROM payment WHERE payment_id = ?";
    private static final String SELECT_ALL_PAYMENTS_SQL = "SELECT p.*, c.cust_username, b.booking_type, s.staff_name FROM payment p LEFT JOIN customer c ON p.cust_id = c.cust_id LEFT JOIN booking b ON p.booking_id = b.booking_id LEFT JOIN staff s ON p.staff_id = s.staff_id";
    private static final String SELECT_PAYMENT_BY_ID_SQL = "SELECT p.*, c.cust_username, b.booking_type, s.staff_name FROM payment p LEFT JOIN customer c ON p.cust_id = c.cust_id LEFT JOIN booking b ON p.booking_id = b.booking_id LEFT JOIN staff s ON p.staff_id = s.staff_id WHERE p.payment_id = ?";
    private static final String EDIT_PAYMENT_SQL = "UPDATE payment SET payment_status = ? WHERE payment_id = ?";
    private static final String GET_PAYMENT_RECEIPT = "SELECT payment_receipt FROM payment WHERE payment_id = ?";


    public void insertPayment(Payment payment) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_PAYMENT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, payment.getDate());
            ps.setString(2, payment.getStatus());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getMethod());

            // Set the BLOB data
            if (payment.getReceipt() != null) {
                ps.setBytes(5, payment.getReceipt());
            } else {
                ps.setNull(5, java.sql.Types.BLOB);
            }

            ps.setInt(6, payment.getBookingId());
            ps.setInt(7, payment.getCustId());
            ps.setInt(8, payment.getStaffId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setPaymentId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public void updatePayment(Payment payment) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_PAYMENT_SQL)) {

            ps.setString(1, payment.getDate());
            ps.setString(2, payment.getStatus());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getMethod());

            // Update the BLOB data
            if (payment.getReceipt() != null) {
                ps.setBytes(5, payment.getReceipt());
            } else {
                ps.setNull(5, java.sql.Types.BLOB);
            }

            ps.setInt(6, payment.getBookingId());
            ps.setInt(7, payment.getCustId());
            ps.setInt(8, payment.getStaffId());
            ps.setInt(9, payment.getPaymentId());

            ps.executeUpdate();
        }
    }

    public void deletePayment(int paymentId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_PAYMENT_SQL)) {

            ps.setInt(1, paymentId);
            ps.executeUpdate();
        }
    }

    public List<Payment> selectAllPayments() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PAYMENTS_SQL);
                ResultSet rs = ps.executeQuery()) {

            List<Payment> payments = new ArrayList<>();
            while (rs.next()) {
                Payment payment = new Payment();

                payment.setPaymentId(rs.getInt("PAYMENT_ID"));
                payment.setDate(rs.getString("PAYMENT_DATE")); 
                payment.setStatus(rs.getString("PAYMENT_STATUS"));
                payment.setAmount(rs.getDouble("PAYMENT_AMOUNT"));
                payment.setMethod(rs.getString("PAYMENT_METHOD"));
                payment.setBookingId(rs.getInt("BOOKING_ID"));
                payment.setCustId(rs.getInt("CUST_ID"));
                payment.setStaffId(rs.getInt("STAFF_ID"));

                // Handle the BLOB (payment_receipt)
                Blob blob = rs.getBlob("PAYMENT_RECEIPT");
                if (blob != null) {
                    payment.setReceipt(blob.getBytes(1, (int) blob.length()));
                    blob.free(); // Free blob memory
                }

                // Additional fields from joins
                payment.setCustName(rs.getString("CUST_USERNAME"));
                payment.setBookingType(rs.getString("BOOKING_TYPE"));
                payment.setStaffName(rs.getString("STAFF_NAME"));

                payments.add(payment);
            }
            return payments;
        }
    }

    public Payment selectPaymentById(int paymentId) throws SQLException {
        Payment payment = null;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_PAYMENT_BY_ID_SQL)) {

            ps.setInt(1, paymentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Retrieving the BLOB data as byte[]
                    Blob blob = rs.getBlob("payment_receipt");
                    byte[] receiptBytes = null;
                    if (blob != null) {
                        receiptBytes = blob.getBytes(1, (int) blob.length());
                        blob.free(); // Free BLOB resources after use
                    }

                    // Create the Payment object
                    payment = new Payment(
                            rs.getInt("payment_id"),
                            rs.getString("payment_date"),
                            rs.getString("payment_status"),
                            rs.getDouble("payment_amount"),
                            rs.getString("payment_method"),
                            receiptBytes, // Set the BLOB data as byte[]
                            rs.getString("booking_type"),
                            rs.getString("cust_username"),
                            rs.getString("staff_name"),
                            rs.getInt("booking_id"),
                            rs.getInt("cust_id"),
                            rs.getInt("staff_id")
                    );
                }
            }
        }
        return payment;
    }

    public boolean editPayment(String paymentId, String paymentStatus) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(EDIT_PAYMENT_SQL)) {
            // Set parameters for the prepared statement
            ps.setString(1, paymentStatus);
            ps.setString(2, paymentId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful
        }
    }
    
        public static InputStream getReceiptById(int paymentId) {
        String sql = GET_PAYMENT_RECEIPT;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("PAYMENT_RECEIPT");
                if (blob != null) {
                    byte[] bytes = blob.getBytes(1, (int) blob.length()); 
                    return new ByteArrayInputStream(bytes); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
