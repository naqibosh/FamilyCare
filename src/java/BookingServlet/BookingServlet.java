package BookingServlet;

import dbconn.DatabaseConnection;
import java.io.IOException;
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
import java.util.Calendar;

public class BookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer customerId = Integer.parseInt(request.getParameter("cust_id"));
        String type = request.getParameter("type");
        int caretakerId = Integer.parseInt(request.getParameter("caretaker"));
        String bookingTimeStr = request.getParameter("time");
        int duration = Integer.parseInt(request.getParameter("duration"));

        // Convert booking time from datetime-local format into Date object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date bookingDate = null;
        try {
            bookingDate = sdf.parse(bookingTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Invalid booking time format.");
            return;
        }

        // Convert to Oracle TIMESTAMP format
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a");
        String formattedBookingTime = dbDateFormat.format(bookingDate);

        // Convert duration to INTERVAL format
        String formattedDuration = String.format("+00 %02d:00:00.000000", duration);

        // Get hourly rate from caretaker type
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

            // Calculate booking price (duration * hourly rate)
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
                    // Retrieve the generated BOOKING_ID
                    String getBookingIdQuery = "SELECT BOOKING_ID FROM (SELECT BOOKING_ID FROM booking WHERE CUST_ID = ? ORDER BY BOOKING_ID DESC) WHERE ROWNUM = 1";
                    try (PreparedStatement bookingIdStmt = conn.prepareStatement(getBookingIdQuery)) {
                        bookingIdStmt.setInt(1, customerId);
                        ResultSet bookingRs = bookingIdStmt.executeQuery();

                        if (bookingRs.next()) {
                            int bookingId = bookingRs.getInt("BOOKING_ID");

                            // Store BOOKING_ID and CUST_ID in session
                            request.getSession().setAttribute("bookingId", bookingId);
                            request.getSession().setAttribute("custId", customerId);
                        }
                    }

                    // Format dates and times
                    SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMMM yyyy");
                    String formattedDate = displayFormat.format(bookingDate);
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                    String formattedTime = timeFormat.format(bookingDate);

                    // Calculate booking end time
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(bookingDate);
                    calendar.add(Calendar.HOUR_OF_DAY, duration);
                    String formattedEndTime = timeFormat.format(calendar.getTime());

                    // Set attributes to pass booking details
                    request.setAttribute("bookingType", type);
                    request.setAttribute("bookingStartDate", formattedDate);
                    request.setAttribute("bookingStartTime", formattedTime);
                    request.setAttribute("bookingEndTime", formattedEndTime);
                    request.setAttribute("bookingFee", String.format("RM %.2f", bookingPrice));

                    // Forward request to JSP
                    request.getRequestDispatcher("customer/bookingtransaction.jsp").forward(request, response);
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
