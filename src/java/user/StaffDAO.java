/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jbcrypt.BCrypt;

/**
 *
 * @author hazik
 */
public class StaffDAO {

    private static final String INSERT_STAFF_SQL = "INSERT INTO staff (staff_name, staff_email, staff_password, staff_phone_number, staff_role) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_STAFF = "SELECT s.staff_id, s.staff_name, s.staff_email, s.staff_phone_number, s.staff_role, s.supervisor_id,  s.is_active, sup.staff_name AS supervisor_name FROM staff s LEFT JOIN staff sup ON s.supervisor_id = sup.staff_id ORDER BY s.staff_id";
    private static final String DELETE_STAFF_SQL = "UPDATE staff SET is_active = 'N' WHERE staff_id = ?";
    private static final String UPDATE_STAFF_SQL = "UPDATE staff SET staff_name = ?, staff_email = ?, staff_password = ?, staff_phone_number = ?, staff_role = ? WHERE staff_id = ?";
    private static final String EDIT_STAFF_SQL = "UPDATE staff SET staff_role = ?, supervisor_id = ? WHERE staff_id = ?";
    private static final String CHECK_EMAIL_SQL = "SELECT * FROM staff WHERE staff_email = ?";
    private static final String CHECK_ROLE_SQL = "SELECT staff_role FROM staff WHERE staff_id = ?";
    private static final String GET_STAFF_INFO = "SELECT s.staff_id AS staff_id, s.staff_name AS staff_name, s.staff_email AS staff_email, s.staff_phone_number AS staff_phone_number, s.staff_role AS staff_role, s.supervisor_id AS supervisor_id, sup.staff_name AS supervisor_name FROM staff s LEFT JOIN staff sup ON s.supervisor_id = sup.staff_id WHERE s.staff_id = ? ORDER BY s.staff_id";
    private static final String CHECK_SVID_SQL = "SELECT staff_id FROM staff WHERE staff_id = ?";
    private static final String AUTHENTICATE_SQL = "SELECT staff_id, staff_password FROM staff WHERE staff_email = ?";
    private static final String DISABLE_STAFF_SQL = "UPDATE staff SET is_active = 'N' WHERE staff_id = ?";
    private static final String ENABLE_STAFF_SQL = "UPDATE staff SET is_active = 'Y' WHERE staff_id = ?";

    public StaffDAO() {}

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

    public int insertStaff(Staff staff) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        int rowsInserted = 0;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(INSERT_STAFF_SQL);

            ps.setString(1, staff.getName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPassword());
            ps.setString(4, staff.getPhoneNumber());
            ps.setString(5, staff.getRole());

            rowsInserted = ps.executeUpdate();

        } finally {
            // Close resources
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing PreparedStatement: " + e.getMessage(), e);
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing Connection: " + e.getMessage(), e);
            }
        }
        return rowsInserted;
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_STAFF)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("staff_id");
                String name = rs.getString("staff_name");
                String email = rs.getString("staff_email");
                String phoneNumber = rs.getString("staff_phone_number");
                String role = rs.getString("staff_role");
                int supervisorId = rs.getInt("supervisor_id");
                String supervisorName = rs.getString("supervisor_name");
                String isActive = rs.getString("is_active");
                staffList.add(new Staff(id, name, email, phoneNumber, role, supervisorId, supervisorName, isActive));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public boolean deleteStaff(int id) {
        boolean isDeleted = false;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_STAFF_SQL)) {
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            isDeleted = rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    public boolean disableStaff(int staffId) {
        boolean isDisable = false;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(DISABLE_STAFF_SQL)) {
            ps.setInt(1, staffId);
            int rowsUpdated = ps.executeUpdate();
            isDisable = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDisable;
    }

    public boolean enableStaff(int staffId) {
        boolean isEnabled = false;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(ENABLE_STAFF_SQL)) {
            ps.setInt(1, staffId);
            int rowsUpdated = ps.executeUpdate();
            isEnabled = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEnabled;
    }

    public boolean editStaff(int staffId, String staffRole, int supervisorId) {
        boolean isUpdated = false;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(EDIT_STAFF_SQL)) {

            ps.setString(1, staffRole);
            ps.setInt(2, supervisorId);
            ps.setInt(3, staffId);

            int rowsUpdated = ps.executeUpdate();
            isUpdated = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean updateStaff(Staff staff) {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_STAFF_SQL)) {
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPassword());
            ps.setString(4, staff.getPhoneNumber());
            ps.setString(5, staff.getRole());
            ps.setInt(6, staff.getSupervisorId());
            ps.setInt(7, staff.getId());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isStaffEmailExists(String email) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(CHECK_EMAIL_SQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Check if any result is returned
        } catch (SQLException e) {
            throw e; // Re-throw the exception for handling
        }
    }

    public Optional<Integer> authenticateUser(String email, String password) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(AUTHENTICATE_SQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            // If email exists, check the password  
            if (rs.next()) {
                String storedPasswordHash = rs.getString("staff_password");
                if (BCrypt.checkpw(password, storedPasswordHash)) {
                    int userId = rs.getInt("staff_id");
                    return Optional.of(userId);
                }
            }
            // Email not found or password does not match  
            return Optional.empty();
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean isSupervisorExists(int supervisorId) throws SQLException {
        boolean isSvExist = false;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(CHECK_SVID_SQL)) {
            ps.setInt(1, supervisorId);
            int rowsExist = ps.executeUpdate();
            isSvExist = rowsExist > 0;
        } catch (SQLException e) {
            throw e;
        }
        return isSvExist;
    }

    public String getUserRole(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String role = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(CHECK_ROLE_SQL);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                role = rs.getString("staff_role");
            }

        } finally {
            // Close resources
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing PreparedStatement: " + e.getMessage(), e);
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing Connection: " + e.getMessage(), e);
            }
        }
        return role;
    }

    public Staff getStaffInfo(int staffId) throws SQLException {
        Staff staff = null;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_STAFF_INFO)) {

            ps.setInt(1, staffId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = new Staff();
                    staff.setId(rs.getInt("staff_id"));
                    staff.setName(rs.getString("staff_name"));
                    staff.setEmail(rs.getString("staff_email"));
                    staff.setPhoneNumber(rs.getString("staff_phone_number"));
                    staff.setRole(rs.getString("staff_role"));
                    staff.setSupervisorId(rs.getInt("supervisor_id"));
                    staff.setSupervisorName(rs.getString("supervisor_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

}
