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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hazik
 */
public class StaffDAO {

    private static final String INSERT_STAFF_SQL = "INSERT INTO staff (staff_name, staff_email, staff_password, staff_phone_number, staff_role) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_STAFF = "SELECT s.staff_id, s.staff_name, s.staff_email, s.staff_phone_number, s.staff_role, s.supervisor_id, sup.staff_name AS supervisor_name FROM staff s LEFT JOIN staff sup ON s.supervisor_id = sup.staff_id ORDER BY s.staff_id";
    private static final String DELETE_STAFF_SQL = "DELETE FROM staff WHERE staff_id = ?";
    private static final String UPDATE_STAFF_SQL = "UPDATE staff SET staff_name = ?, staff_email = ?, staff_password = ?, staff_phone_number = ?, staff_role = ? WHERE staff_id = ?";
    private static final String CHECK_EMAIL_SQL = "SELECT * FROM staff WHERE staff_email = ?";
    private static final String CHECK_ROLE_SQL = "SELECT staff_role FROM staff WHERE staff_id = ?";

    public StaffDAO() {}

    private Connection getConnection() throws SQLException {
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

    public void deleteStaff(int id) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STAFF_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff staff) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STAFF_SQL)) {
            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getEmail());
            preparedStatement.setString(3, staff.getPassword());
            preparedStatement.setString(4, staff.getPhoneNumber());
            preparedStatement.setString(5, staff.getRole());
            preparedStatement.setInt(6, staff.getId());
            preparedStatement.setInt(7, staff.getSupervisorId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

}
