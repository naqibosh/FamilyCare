
package userDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import user.CaretakerBabysitter;
import dbconn.DatabaseConnection;

/**
 *
 * @author hazik
 */
public class CaretakerBabysitterDAO {

    private static final String INSERT_BABYSITTER_SQL = "INSERT INTO BABYSITTER (CARETAKER_ID, BABYSITTER_EXPERIENCE_YEARS, BABYSITTER_HOURLY_RATE) VALUES (?, ?, ?)";
    
    public boolean insertCaretakerBabysitter(CaretakerBabysitter CaretakerBabysitter) throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_BABYSITTER_SQL)) {

            ps.setInt(1, CaretakerBabysitter.getCaretakerId());
            ps.setInt(2, CaretakerBabysitter.getExperienceYears());
            ps.setDouble(3, CaretakerBabysitter.getHourlyRate());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
