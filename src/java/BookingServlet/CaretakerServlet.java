package BookingServlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaretakerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String query = "";
        if ("baby".equals(type)) {
            query = "SELECT c.caretaker_id, c.caretaker_name "
                    + "FROM caretaker c "
                    + "JOIN babysitter b ON c.caretaker_id = b.caretaker_id";
        } else if ("elder".equals(type)) {
            query = "SELECT c.caretaker_id, c.caretaker_name "
                    + "FROM caretaker c "
                    + "JOIN eldercaretaker e ON c.caretaker_id = e.caretaker_id";
        }

        if (query.isEmpty()) {
            out.println("<option value='' disabled>No caretakers available</option>");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            // Output <option> elements
            while (rs.next()) {
                int caretakerId = rs.getInt("caretaker_id");
                String caretakerName = rs.getString("caretaker_name");
                out.println("<option value='" + caretakerId + "'>" + caretakerName + "</option>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<option value='' disabled>Error loading caretakers</option>");
        }
    }

}
