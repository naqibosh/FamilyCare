package caretaker.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class caretakerLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String caretakerIC = request.getParameter("icNumber");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Query to check credentials, STATUS_ID = 1, and IS_ACTIVE = 'y'
            String sql = "SELECT * FROM CARETAKER WHERE CARETAKER_IC_NUMBER = ? AND CARETAKER_PASSWORD = ? AND STATUS_ID = 1 AND IS_ACTIVE = 'Y'";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, caretakerIC);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Credentials are correct, STATUS_ID = 1, and IS_ACTIVE = 'y'
                HttpSession session = request.getSession();

                // Store caretakerName, caretakerId, and caretakerStatus in the session
                session.setAttribute("caretakerName", resultSet.getString("CARETAKER_NAME"));
                session.setAttribute("caretakerId", resultSet.getInt("CARETAKER_ID"));
                session.setAttribute("caretakerStatus", resultSet.getString("AVAILABILITY_STATUS"));

                // Redirect to caretaker homepage
                response.sendRedirect("caretaker/caretaker_homepage.jsp");
            } else {
                // Either credentials are incorrect, STATUS_ID is not 1, or IS_ACTIVE is not 'y'
                response.sendRedirect("caretaker/login.jsp?errorMessage=Invalid IC number, password, or account status.");
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("caretaker/login.jsp?error=Something went wrong. Please try again.");
        }
    }
}
