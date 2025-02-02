package userDAO;

import dbconn.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import user.CaretakerElder;

/**
 *
 * @author hazik
 */
public class CaretakerElderDAO {

    private static final String INSERT_ELDER_SQL = "INSERT INTO ELDERCARETAKER (CARETAKER_ID, ELDERCARE_EXPERIENCE_YEARS, ELDERCARE_HOURLY_RATE, ELDERCARE_CERTIFICATION) VALUES (? ,?, ?, ?)";

    public boolean insertCaretakerElder(CaretakerElder elder) throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_ELDER_SQL)) {

            ps.setInt(1, elder.getCaretakerId());
            ps.setInt(2, elder.getExperienceYears());
            ps.setDouble(3, elder.getHourlyRate());
            ps.setBlob(4, elder.getCertificationStream()); 

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
