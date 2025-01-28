/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caretaker.servlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class caretakerProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("caretakerName") == null) {
            response.sendRedirect("login.jsp?errorMessage=Please log in first.");
            return;
        }

        String caretakerName = (String) session.getAttribute("caretakerName");
        Connection conn = null;

        try {
            conn = new DatabaseConnection().getConnection();
            String query = "SELECT * FROM CARETAKER WHERE CARETAKER_NAME = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, caretakerName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Store data in request attributes
                request.setAttribute("caretakerID", rs.getInt("CARETAKER_ID"));
                request.setAttribute("caretakerPhone", rs.getString("CARETAKER_PHONE"));
                request.setAttribute("availabilityStatus", rs.getString("AVAILABILITY_STATUS"));
                request.setAttribute("profileDescription", rs.getString("PROFILE_DESCRIPTION"));
                request.setAttribute("caretakerICNumber", rs.getString("CARETAKER_IC_NUMBER"));
                request.setAttribute("staffID", rs.getInt("STAFF_ID"));
                request.setAttribute("statusID", rs.getInt("STATUS_ID"));

                // Forward to profile JSP
                request.getRequestDispatcher("caretaker/caretaker_profile.jsp").forward(request, response);
            } else {
                response.sendRedirect("caretaker_homepage.jsp?errorMessage=No profile found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("caretaker_homepage.jsp?errorMessage=Error fetching profile.");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
