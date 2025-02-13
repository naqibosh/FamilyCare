package userDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import user.Booking;
import dbconn.DatabaseConnection;

/**
 *
 * @author hazik
 */
public class BookingDAO {

    private static final String INSERT_BOOKING_SQL = "#";

    private static final String DELETE_BOOKING_SQL
            = "DELETE FROM booking "
            + "WHERE booking_id = ?";

    private static final String DELETE_PAYMENT_SQL
            = "DELETE FROM payment "
            + "WHERE booking_id = ?";

    private static final String DELETE_REVIEW_SQL
            = "DELETE FROM review "
            + "WHERE booking_id = ?";

    private static final String UPDATE_BOOKING_SQL = "#";

    private static final String SELECT_ALL_BOOKING
            = "SELECT b.booking_id, "
            + "       b.booking_type, "
            + "       TO_CHAR(b.booking_time, 'YYYY-MM-DD HH24:MI:SS') AS booking_time, "
            + "       TRIM(BOTH ' ' FROM "
            + "           (CASE WHEN EXTRACT(DAY FROM b.booking_duration) > 0 THEN "
            + "               TO_CHAR(EXTRACT(DAY FROM b.booking_duration), 'FM9999') || ' days ' "
            + "            ELSE '' END || "
            + "            CASE WHEN EXTRACT(HOUR FROM b.booking_duration) > 0 THEN "
            + "               TO_CHAR(EXTRACT(HOUR FROM b.booking_duration), 'FM00') || ' hours ' "
            + "            ELSE '' END || "
            + "            CASE WHEN EXTRACT(MINUTE FROM b.booking_duration) > 0 THEN "
            + "               TO_CHAR(EXTRACT(MINUTE FROM b.booking_duration), 'FM00') || ' minutes ' "
            + "            ELSE '' END || "
            + "            CASE WHEN EXTRACT(SECOND FROM b.booking_duration) > 0 THEN "
            + "               TO_CHAR(EXTRACT(SECOND FROM b.booking_duration), 'FM00') || ' seconds' "
            + "            ELSE '' END "
            + "           )"
            + "       ) AS booking_duration, "
            + "       b.booking_price, "
            + "       b.cust_id, "
            + "       b.staff_id, "
            + "       b.caretaker_id, "
            + "       c.cust_username, "
            + "       s.staff_name, "
            + "       k.caretaker_name, "
            + "       p.payment_status AS booking_status "
            + "FROM booking b "
            + "LEFT JOIN customer c ON b.cust_id = c.cust_id "
            + "LEFT JOIN staff s ON b.staff_id = s.staff_id "
            + "LEFT JOIN caretaker k ON b.caretaker_id = k.caretaker_id "
            + "LEFT JOIN payment p ON b.booking_id = p.booking_id"; 

    private static final String GET_BOOKING_INFO = "#";

    private static final String EDIT_BOOKING_SQL
            = "UPDATE booking "
            + "SET staff_id = ? "
            + "WHERE booking_id = ?";

    public void deleteBooking(int bookingId) throws SQLException {
        Connection connection = null;
        PreparedStatement deletePaymentStmt = null;
        PreparedStatement deleteReviewStmt = null;
        PreparedStatement deleteBookingStmt = null;

        try {
            connection = DatabaseConnection.getConnection();
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
        try (Connection connection = DatabaseConnection.getConnection();
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
        try (Connection connection = DatabaseConnection.getConnection();
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
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BOOKING);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking book = new Booking();
                book.setBookingId(rs.getInt("booking_id"));
                book.setBookingType(rs.getString("booking_type"));
                book.setBookingStatus(rs.getString("booking_status"));
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
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(EDIT_BOOKING_SQL)) {

            ps.setInt(1, staffId); // Set the new staff ID
            ps.setInt(2, bookingId); // Specify which booking to update
            

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful
        }
    }

}
