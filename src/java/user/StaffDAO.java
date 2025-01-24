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
    private static final String SELECT_ALL_STAFF = "SELECT s.staff_id, s.staff_name, s.staff_email, s.staff_phone_number, s.staff_role, s.supervisor_id, sup.staff_name AS supervisor_name FROM staff s LEFT JOIN staff sup ON s.supervisor_id = sup.staff_id WHERE s.is_active = 'Y' ORDER BY s.staff_id";
    private static final String DELETE_STAFF_SQL = "UPDATE staff SET is_active = 'N' WHERE staff_id = ?";
    private static final String UPDATE_STAFF_SQL = "UPDATE staff SET staff_name = ?, staff_email = ?, staff_password = ?, staff_phone_number = ?, staff_role = ? WHERE staff_id = ?";
    private static final String EDIT_STAFF_SQL = "UPDATE staff SET staff_role = ?, supervisor_id = ? WHERE staff_id = ?";
    private static final String CHECK_EMAIL_SQL = "SELECT * FROM staff WHERE staff_email = ?";
    private static final String CHECK_ROLE_SQL = "SELECT staff_role FROM staff WHERE staff_id = ?";
    private static final String GET_STAFF_INFO = "SELECT s.staff_id AS staff_id, s.staff_name AS staff_name, s.staff_email AS staff_email, s.staff_phone_number AS staff_phone_number, s.staff_role AS staff_role, s.supervisor_id AS supervisor_id, sup.staff_name AS supervisor_name FROM staff s LEFT JOIN staff sup ON s.supervisor_id = sup.staff_id WHERE s.staff_id = ? ORDER BY s.staff_id";
    private static final String CHECK_SVID_SQL = "SELECT staff_id FROM staff WHERE staff_id = ?";
    private static final String AUTHENTICATE_SQL = "SELECT staff_id, staff_password FROM staff WHERE staff_email = ?";

    public StaffDAO() {
    }

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
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(INSERT_STAFF_SQL);

            statement.setString(1, staff.getName());
            statement.setString(2, staff.getEmail());
            statement.setString(3, staff.getPassword());
            statement.setString(4, staff.getPhoneNumber());
            statement.setString(5, staff.getRole());

            rowsInserted = statement.executeUpdate();

        } finally {
            // Close resources
            try {
                if (statement != null) {
                    statement.close();
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
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STAFF)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("staff_id");
                String name = rs.getString("staff_name");
                String email = rs.getString("staff_email");
                String phoneNumber = rs.getString("staff_phone_number");
                String role = rs.getString("staff_role");
                int supervisorId = rs.getInt("supervisor_id");
                String supervisorName = rs.getString("supervisor_name");
                staffList.add(new Staff(id, name, email, phoneNumber, role, supervisorId, supervisorName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public boolean deleteStaff(int id) {
        boolean isDeleted = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STAFF_SQL)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            isDeleted = rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    public boolean editStaff(int staffId, String staffRole, int supervisorId) {
        boolean isUpdated = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(EDIT_STAFF_SQL)) {

            preparedStatement.setString(1, staffRole);
            preparedStatement.setInt(2, supervisorId);
            preparedStatement.setInt(3, staffId);

            int rowsUpdated = preparedStatement.executeUpdate();
            isUpdated = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean updateStaff(Staff staff) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STAFF_SQL)) {
            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getEmail());
            preparedStatement.setString(3, staff.getPassword());
            preparedStatement.setString(4, staff.getPhoneNumber());
            preparedStatement.setString(5, staff.getRole());
            preparedStatement.setInt(6, staff.getSupervisorId());
            preparedStatement.setInt(7, staff.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isStaffEmailExists(String email) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); // Check if any result is returned
        } catch (SQLException e) {
            throw e; // Re-throw the exception for handling
        }
    }

    public Optional<Integer> authenticateUser(String email, String password) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATE_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If email exists, check the password  
            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("staff_password");
                if (BCrypt.checkpw(password, storedPasswordHash)) {
                    int userId = resultSet.getInt("staff_id"); 
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
                PreparedStatement preparedStatement = connection.prepareStatement(CHECK_SVID_SQL)) {
            preparedStatement.setInt(1, supervisorId);
            int rowsExist = preparedStatement.executeUpdate();
            isSvExist = rowsExist > 0;
        } catch (SQLException e) {
            throw e;
        }
        return isSvExist;
    }

    public String getUserRole(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String role = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(CHECK_ROLE_SQL);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                role = resultSet.getString("staff_role");
            }

        } finally {
            // Close resources
            try {
                if (statement != null) {
                    statement.close();
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
                PreparedStatement preparedStatement = connection.prepareStatement(GET_STAFF_INFO)) {

            preparedStatement.setInt(1, staffId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    staff = new Staff();
                    staff.setId(resultSet.getInt("staff_id"));
                    staff.setName(resultSet.getString("staff_name"));
                    staff.setEmail(resultSet.getString("staff_email"));
                    staff.setPhoneNumber(resultSet.getString("staff_phone_number"));
                    staff.setRole(resultSet.getString("staff_role"));
                    staff.setSupervisorId(resultSet.getInt("supervisor_id"));
                    staff.setSupervisorName(resultSet.getString("supervisor_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

}
