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
public class CustomerDAO {

    private static final String INSERT_CUST_SQL = "INSERT INTO customer (cust_username, cust_password, cust_first_name, cust_last_name, cust_phone_number, cust_email, cust_identification_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DISABLE_CUST_SQL = "UPDATE customer SET is_active = 'N' WHERE cust_id = ?";
    private static final String ENABLE_CUST_SQL = "UPDATE customer SET is_active = 'Y' WHERE cust_id = ?";
    private static final String UPDATE_CUST_SQL = "UPDATE Customer SET cust_username=?, cust_password=?, cust_first_name=?, cust_last_name=?, cust_phone_number=?, cust_email=?, cust_identification_number=? WHERE cust_id = ?";
    private static final String SELECT_ALL_CUST = "SELECT c.cust_id, c.cust_username, c.cust_first_name, c.cust_last_name, c.cust_phone_number, c.cust_email, c.cust_identification_number, c.ban_date, c.status_id, s.reason AS status, c.is_active FROM Customer c JOIN Status s ON c.status_id = s.status_id";
    private static final String GET_CUST_INFO = "SELECT cust_username, cust_password, cust_first_name, cust_last_name, cust_phone_number, cust_email, cust_identification_number FROM Customer WHERE cust_id = ?";
    private static final String EDIT_CUST_SQL = "UPDATE Customer SET ban_date = ?, status_id = ? WHERE cust_id = ?";

    public CustomerDAO() {}

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

    public int insertCustomer(Customer customer) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(INSERT_CUST_SQL);

            statement.setString(1, customer.getCustUsername());
            statement.setString(2, customer.getCustPass());
            statement.setString(3, customer.getCustFName());
            statement.setString(4, customer.getCustLName());
            statement.setString(5, customer.getCustPhone());
            statement.setString(6, customer.getCustEmail());
            statement.setString(7, customer.getCustIC());

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

    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_CUST)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custId = rs.getInt("cust_id");
                String custUsername = rs.getString("cust_username");
                String custFName = rs.getString("cust_first_name");
                String custLName = rs.getString("cust_last_name");
                String custPhone = rs.getString("cust_phone_number");
                String custEmail = rs.getString("cust_email");
                String custIC = rs.getString("cust_identification_number");
                String banDate = rs.getString("ban_date");
                String is_active = rs.getString("is_active");
                String status = rs.getString("status");
                int statusId = rs.getInt("status_id");

                customerList.add(new Customer( custId,  custUsername, custFName,  custLName,  custPhone,  custEmail,  custIC,  banDate,  statusId,  status,  is_active));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public boolean disableCust(int custId) {
        boolean isDisable = false;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(DISABLE_CUST_SQL)) {
            ps.setInt(1, custId);
            int rowsUpdated = ps.executeUpdate();
            isDisable = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDisable;
    }

    public boolean enableCust(int custId) {
        boolean isEnabled = false;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(ENABLE_CUST_SQL)) {
            ps.setInt(1, custId);
            int rowsUpdated = ps.executeUpdate();
            isEnabled = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEnabled;
    }

    public boolean updateCust(Customer customer) {
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_CUST_SQL)) {

            ps.setString(1, customer.getCustUsername());
            ps.setString(2, customer.getCustPass());
            ps.setString(3, customer.getCustFName());
            ps.setString(4, customer.getCustLName());
            ps.setString(5, customer.getCustPhone());
            ps.setString(6, customer.getCustEmail());
            ps.setString(7, customer.getCustIC());
            ps.setInt(8, customer.getStatusId());
            ps.setInt(9, customer.getCustId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to update customer", e);
            return false;
        }
    }

    public boolean editCust(int custId, Timestamp banDate, int statusId) {
        boolean isUpdated = false;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(EDIT_CUST_SQL)) {

            ps.setTimestamp(1, banDate);
            ps.setInt(2, statusId);
            ps.setInt(3, custId);

            int rowsUpdated = ps.executeUpdate();
            isUpdated = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public Customer getCustInfo(int custId) throws SQLException {
        Customer cust = null;
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_CUST_INFO)) {
            ps.setInt(1, custId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cust = new Customer();
                    cust.setCustUsername(rs.getString("cust_username"));
                    cust.setCustUsername(rs.getString("cust_password"));
                    cust.setCustFName(rs.getString("cust_first_name"));
                    cust.setCustLName(rs.getString("cust_last_name"));
                    cust.setCustPhone(rs.getString("cust_phone_number"));
                    cust.setCustEmail(rs.getString("cust_email"));
                    cust.setCustIC(rs.getString("cust_identification_number"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cust;
    }

}
