/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hazik
 */
public class CaretakerDAO {

    private static final String INSERT_CARETAKER_SQL = "#";
    private static final String DISABLE_CARETAKER_SQL = "UPDATE caretaker SET is_active = 'N' WHERE caretaker_id = ?";
    private static final String ENABLE_CARETAKER_SQL = "UPDATE caretaker SET is_active = 'Y' WHERE caretaker_id = ?";
    private static final String UPDATE_CARETAKER_SQL = "#";
    private static final String SELECT_ALL_CARETAKER = "SELECT c.caretaker_id, c.caretaker_name, c.caretaker_phone, c.availability_status, c.caretaker_ic_number, c.staff_id, c.status_id, c.ban_date, c.is_active, s.reason AS status FROM caretaker c JOIN Status s ON c.status_id = s.status_id";
    private static final String GET_CARETAKER_INFO = "#";
    private static final String EDIT_CARETAKER_SQL = "UPDATE caretaker SET staff_id = ? ,ban_date = ?, status_id = ? WHERE caretaker_id = ?";

    public Connection getConnection() throws SQLException {
        final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
        final String DB_USER = "CareGiver";
        final String DB_PASSWORD = "system";

        Connection connection = null;
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException ex) {
            // Log error if the driver is not found
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, "Oracle JDBC Driver not found", ex);
            throw new SQLException("Unable to load the Oracle JDBC Driver", ex);
        } catch (SQLException ex) {
            // Log error for database connection issues
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
            throw ex; // Re-throw exception for caller to handle
        }
        return connection;
    }

    // Method to insert a new caretaker  
    public void insertCaretaker(Caretaker caretaker) {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_CARETAKER_SQL)) {

            ps.setString(1, caretaker.getName());
            ps.setString(2, caretaker.getPhone());
            ps.setString(3, caretaker.getAvailabilityStatus());
            ps.setString(4, caretaker.getIC());
            ps.setInt(5, caretaker.getStaffId());
            ps.setInt(6, caretaker.getStatusId());
            ps.setString(7, caretaker.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to disable a caretaker  
    public boolean disableCaretaker(int caretakerId) {
        boolean isDisable = false;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(DISABLE_CARETAKER_SQL)) {

            ps.setInt(1, caretakerId);
            int rowsUpdated = ps.executeUpdate();
            isDisable = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDisable;
    }

    // Method to enable a caretaker  
    public boolean enableCaretaker(int caretakerId) {
        boolean isEnabled = false;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(ENABLE_CARETAKER_SQL)) {

            ps.setInt(1, caretakerId);
            int rowsUpdated = ps.executeUpdate();
            isEnabled = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEnabled;
    }

    // Method to update a caretaker's details  
    public boolean updateCaretaker(Caretaker caretaker) {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_CARETAKER_SQL)) {

            ps.setString(1, caretaker.getName());
            ps.setString(2, caretaker.getPhone());
            ps.setString(3, caretaker.getAvailabilityStatus());
            ps.setString(4, caretaker.getIC());
            ps.setInt(5, caretaker.getStaffId());
            ps.setInt(6, caretaker.getStatusId());
            ps.setInt(7, caretaker.getCaretakerId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Failed to update customer", e);
            return false;
        }
    }

    // Method to retrieve all caretakers  
    public List<Caretaker> selectAllCaretakers() {
        List<Caretaker> caretakers = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_CARETAKER);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Caretaker caretaker = new Caretaker();
                caretaker.setCaretakerId(rs.getInt("caretaker_id"));
                caretaker.setName(rs.getString("caretaker_name"));
                caretaker.setPhone(rs.getString("caretaker_phone"));
                caretaker.setAvailabilityStatus(rs.getString("availability_status"));
                caretaker.setIC(rs.getString("caretaker_ic_number"));
                caretaker.setStaffId(rs.getInt("staff_id"));
                caretaker.setStatusId(rs.getInt("status_id"));
                caretaker.setBanDate(rs.getString("ban_date"));
                caretaker.setStatus(rs.getString("status"));
                caretaker.setIs_active(rs.getString("is_active"));

                caretakers.add(caretaker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caretakers;
    }

    // Method to get specific caretaker information  
    public Caretaker getCaretakerInfo(int caretakerId) {
        Caretaker caretaker = null;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_CARETAKER_INFO)) {

            ps.setInt(1, caretakerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                caretaker = new Caretaker();
                caretaker.setCaretakerId(rs.getInt("caretaker_id"));
                caretaker.setName(rs.getString("caretaker_name"));
                caretaker.setPhone(rs.getString("caretaker_phone"));
                caretaker.setAvailabilityStatus(rs.getString("availability_status"));
                caretaker.setIC(rs.getString("caretaker_ic_number"));
                caretaker.setStaffId(rs.getInt("staff_id"));
                caretaker.setStatusId(rs.getInt("status_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caretaker;
    }

    // Method to update specific caretaker details  
    public Boolean editCaretaker(int staffId, int caretakerId, Timestamp banDate, int statusId) {
        boolean isUpdated = false;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(EDIT_CARETAKER_SQL)) {

            ps.setInt(1, staffId);
            ps.setTimestamp(2, banDate);
            ps.setInt(3, statusId);
            ps.setInt(4, caretakerId);

            int rowsUpdated = ps.executeUpdate();
            isUpdated = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

}
