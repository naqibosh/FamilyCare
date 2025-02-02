package userDAO;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import user.Caretaker;
import dbconn.DatabaseConnection;

/**
 *
 * @author hazik
 */
public class CaretakerDAO {

    private static final String INSERT_CARETAKER_SQL = "INSERT INTO CARETAKER (STAFF_ID, CARETAKER_NAME, CARETAKER_PHONE, CARETAKER_IC_NUMBER, PROFILE_DESCRIPTION, CARETAKER_PASSWORD) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_LATEST_ID_SQL = "SELECT caretaker_id FROM (SELECT caretaker_id FROM caretaker ORDER BY caretaker_id DESC ) WHERE ROWNUM = 1";
    private static final String DISABLE_CARETAKER_SQL = "UPDATE caretaker SET is_active = 'N' WHERE caretaker_id = ?";
    private static final String ENABLE_CARETAKER_SQL = "UPDATE caretaker SET is_active = 'Y' WHERE caretaker_id = ?";
    private static final String UPDATE_CARETAKER_SQL = "#";
    private static final String SELECT_ALL_CARETAKER = "SELECT c.caretaker_id, c.caretaker_name, c.caretaker_phone, c.availability_status, c.caretaker_ic_number, c.staff_id, c.status_id, c.ban_date, c.is_active, s.reason AS status FROM caretaker c JOIN Status s ON c.status_id = s.status_id";
    private static final String GET_CARETAKER_INFO = "#";
    private static final String EDIT_CARETAKER_SQL = "UPDATE caretaker SET staff_id = ? ,ban_date = ?, status_id = ? WHERE caretaker_id = ?";

    public boolean insertCaretaker(Caretaker caretaker) throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_CARETAKER_SQL)) {

            ps.setInt(1, caretaker.getStaffId());
            ps.setString(2, caretaker.getName());
            ps.setString(3, caretaker.getPhone());
            ps.setString(4, caretaker.getIC());
            ps.setString(5, caretaker.getProfileDescription());
            ps.setString(6, caretaker.getPassword());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getLatestCaretakerId() {
        int caretakerId = 0;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_LATEST_ID_SQL);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                caretakerId = rs.getInt("caretaker_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return caretakerId;
    }

    public boolean disableCaretaker(int caretakerId) {
        boolean isDisable = false;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(DISABLE_CARETAKER_SQL)) {

            ps.setInt(1, caretakerId);
            int rowsUpdated = ps.executeUpdate();
            isDisable = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDisable;
    }

    public boolean enableCaretaker(int caretakerId) {
        boolean isEnabled = false;
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(ENABLE_CARETAKER_SQL)) {

            ps.setInt(1, caretakerId);
            int rowsUpdated = ps.executeUpdate();
            isEnabled = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEnabled;
    }

    public boolean updateCaretaker(Caretaker caretaker) {
        try (Connection connection = DatabaseConnection.getConnection();
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

    public List<Caretaker> selectAllCaretakers() {
        List<Caretaker> caretakers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
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

    public Caretaker getCaretakerInfo(int caretakerId) {
        Caretaker caretaker = null;
        try (Connection connection = DatabaseConnection.getConnection();
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

    public Boolean editCaretaker(int staffId, int caretakerId, Timestamp banDate, int statusId) {
        boolean isUpdated = false;
        try (Connection connection = DatabaseConnection.getConnection();
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
