package user;

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

/**
 *
 * @author hazik
 */
public class BookingDAO {

    private static final String INSERT_BOOKING_SQL = "#";
    private static final String DELETE_BOOKING_SQL = "DELETE booking WHERE booking_id = ?";
    private static final String DELETE_PAYMENT_SQL = "DELETE payment WHERE booking_id = ?";
    private static final String DELETE_REVIEW_SQL = "DELETE review WHERE booking_id = ?";
    private static final String UPDATE_BOOKING_SQL = "#";
    private static final String SELECT_ALL_BOOKING = "SELECT b.booking_id, b.booking_type, b.booking_time, b.booking_duration, b.booking_price, b.cust_id, b.staff_id, b.caretaker_id, c.cust_username, s.staff_name, k.caretaker_name FROM booking b LEFT JOIN customer c ON b.cust_id = c.cust_id LEFT JOIN staff s ON b.staff_id = s.staff_id LEFT JOIN caretaker k ON b.caretaker_id = k.caretaker_id";
    private static final String GET_BOOKING_INFO = "#";
    private static final String EDIT_BOOKING_SQL = "UPDATE booking SET staff_id = ? WHERE booking_id = ?";

    public Connection getConnection() throws SQLException {
        final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
        final String DB_USER = "CareGiver";
        final String DB_PASSWORD = "system";

        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException ex) {

            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, "Oracle JDBC Driver not found", ex);
            throw new SQLException("Unable to load the Oracle JDBC Driver", ex);
        } catch (SQLException ex) {

            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
            throw ex; // Re-throw exception for caller to handle
        }
        return connection;
    }

    public void deleteBooking(int bookingId) throws SQLException {
        Connection connection = null;
        PreparedStatement deletePaymentStmt = null;
        PreparedStatement deleteReviewStmt = null;
        PreparedStatement deleteBookingStmt = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Start transaction  

            // Delete payment related to the booking  
            deletePaymentStmt = connection.prepareStatement(DELETE_PAYMENT_SQL);
            deletePaymentStmt.setInt(1, bookingId);
            deletePaymentStmt.executeUpdate();

            // Delete review related to the booking  
            deleteReviewStmt = connection.prepareStatement(DELETE_REVIEW_SQL);
            deleteReviewStmt.setInt(1, bookingId);
            deleteReviewStmt.executeUpdate();

            // Delete the booking after payments and reviews are deleted  
            deleteBookingStmt = connection.prepareStatement(DELETE_BOOKING_SQL);
            deleteBookingStmt.setInt(1, bookingId);
            deleteBookingStmt.executeUpdate();

            connection.commit(); // Commit transaction if all deletes are successful  
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction in case of error  
                } catch (SQLException rollbackEx) {
                    Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, "Rollback failed", rollbackEx);
                }
            }
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, "Error deleting booking", e);
            throw e; // Re-throw the exception for the caller to handle  
        } finally {
            // Cleanup resources  
            try {
                if (deletePaymentStmt != null) {
                    deletePaymentStmt.close();
                }
                if (deleteReviewStmt != null) {
                    deleteReviewStmt.close();
                }
                if (deleteBookingStmt != null) {
                    deleteBookingStmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
    }

    public void insertBooking(Booking booking) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_BOOKING_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, booking.getBookingType());
            ps.setString(2, booking.getBookingTime());
            ps.setString(3, booking.getBookingDuration());
            ps.setDouble(4, booking.getBookingPrice());
            ps.setInt(5, booking.getCaretakerId());
            ps.setInt(6, booking.getCustId());
            ps.setInt(7, booking.getStaffId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setBookingId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public void updateBooking(Booking booking) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_BOOKING_SQL)) {

            ps.setString(1, booking.getBookingType());
            ps.setString(2, booking.getBookingTime());
            ps.setString(3, booking.getBookingDuration());
            ps.setDouble(4, booking.getBookingPrice());
            ps.setInt(5, booking.getCaretakerId());
            ps.setInt(6, booking.getCustId());
            ps.setInt(7, booking.getStaffId());
            ps.setInt(8, booking.getBookingId());

            ps.executeUpdate();
        }
    }

    public List<Booking> selectAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BOOKING);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking book = new Booking();
                book.setBookingId(rs.getInt("booking_id"));
                book.setBookingType(rs.getString("booking_type"));
                book.setBookingTime(rs.getString("booking_time"));
                book.setBookingDuration(rs.getString("booking_duration"));
                book.setBookingPrice(rs.getDouble("booking_price"));
                book.setCaretakerId(rs.getInt("caretaker_id"));
                book.setCustId(rs.getInt("cust_id"));
                book.setStaffId(rs.getInt("staff_id"));
                book.setCustName(rs.getString("cust_username"));
                book.setStaffName(rs.getString("staff_name"));
                book.setCaretakerName(rs.getString("caretaker_name"));

                bookings.add(book);
            }
        }
        return bookings;
    }

    public boolean editBooking(int bookingId, int staffId) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(EDIT_BOOKING_SQL)) {

            ps.setInt(1, staffId); // Set the new staff ID
            ps.setInt(2, bookingId); // Specify which booking to update

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful
        }
    }

}
