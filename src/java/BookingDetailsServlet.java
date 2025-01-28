package BookingServlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer customerId = Integer.parseInt(request.getParameter("cust_id"));
        String type = request.getParameter("type");
        int caretakerId = Integer.parseInt(request.getParameter("caretaker"));
        String bookingTimeStr = request.getParameter("time");
        int duration = Integer.parseInt(request.getParameter("duration"));

        // Convert the booking time from datetime-local format into Date object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date bookingDate = null;
        try {
            bookingDate = sdf.parse(bookingTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Invalid booking time format.");
            return;
        }

        // Convert to Oracle TIMESTAMP format (e.g., 29-JAN-25 01.15.00.000000000 PM)
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a");
        String formattedBookingTime = dbDateFormat.format(bookingDate);

        // Convert duration to INTERVAL format (e.g., +00 02:00:00.000000)
        String formattedDuration = String.format("+00 %02d:00:00.000000", duration);

        // Get hourly rate based on caretaker type (baby or elder)
        double hourlyRate = 0;
        String rateQuery = "";
        if ("Babycaretaker".equals(type)) {
            rateQuery = "SELECT b.BABYSITTER_HOURLY_RATE FROM babysitter b WHERE b.CARETAKER_ID = ?";
        } else if ("Eldercaretaker".equals(type)) {
            rateQuery = "SELECT e.ELDERCARE_HOURLY_RATE FROM eldercaretaker e WHERE e.CARETAKER_ID = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement rateStmt = conn.prepareStatement(rateQuery)) {

            rateStmt.setInt(1, caretakerId);
            ResultSet rs = rateStmt.executeQuery();
            if (rs.next()) {
                hourlyRate = rs.getDouble(1);
            }

            // Calculate booking price
            double bookingPrice = hourlyRate * duration;

            // Insert booking into the booking table
            String insertBookingQuery = "INSERT INTO booking (BOOKING_TYPE, BOOKING_TIME, BOOKING_DURATION, BOOKING_PRICE, CARETAKER_ID, CUST_ID, STAFF_ID) "
                    + "VALUES (?, TO_TIMESTAMP(?, 'DD-MON-YY HH.MI.SS.FF AM'), ?, ?, ?, ?, NULL)";

            try (PreparedStatement stmt = conn.prepareStatement(insertBookingQuery)) {
                stmt.setString(1, type);
                stmt.setString(2, formattedBookingTime);
                stmt.setString(3, formattedDuration);
                stmt.setDouble(4, bookingPrice);
                stmt.setInt(5, caretakerId);
                stmt.setInt(6, customerId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Send success alert with redirection
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Booking successfully saved!');");
                    out.println("window.location.href = 'customer/booking.jsp';");
                    out.println("</script>");
                } else {
                    response.getWriter().println("Error in booking.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error saving booking data.");
        }
    }
}
