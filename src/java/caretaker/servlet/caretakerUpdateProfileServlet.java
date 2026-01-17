package caretaker.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class caretakerUpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("caretakerName") == null) {
            response.sendRedirect("login.jsp?errorMessage=Please log in first.");
            return;
        }

        String caretakerID = request.getParameter("caretakerID");
        String caretakerName = request.getParameter("caretakerName");
        String caretakerPhone = request.getParameter("caretakerPhone");
        String caretakerICNumber = request.getParameter("caretakerICNumber");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new DatabaseConnection().getConnection();
            String updateQuery = "UPDATE CARETAKER SET CARETAKER_NAME = ?, CARETAKER_PHONE = ?, CARETAKER_IC_NUMBER = ? WHERE CARETAKER_ID = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, caretakerName);
            pstmt.setString(2, caretakerPhone);
            pstmt.setString(3, caretakerICNumber);
            pstmt.setString(4, caretakerID);

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows > 0) {
                // Update session attributes if name was changed
                session.setAttribute("caretakerName", caretakerName);
                response.sendRedirect("caretakerProfileServlet");
            } else {
                response.sendRedirect("caretaker/edit_caretaker_profile.jsp?caretakerID=" + caretakerID + "&errorMessage=Failed to update profile.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/caretakerProfileServlet?caretakerID=" + caretakerID + "&errorMessage=Error occurred while updating profile.");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
