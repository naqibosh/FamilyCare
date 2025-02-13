<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*, java.text.SimpleDateFormat" %>
<%@ page import="dbconn.DatabaseConnection" %>
<%
    Integer customerId = (Integer) session.getAttribute("customerId");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Booking Details</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Poppins', sans-serif;
                line-height: 1.6;
                background-color: #FFFFFF;
                color: black;
            }

            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                width: 200px;
                height: 100%;
                background: #1F4068;
                color: #fff;
                padding: 1rem;
                display: flex;
                flex-direction: column;
                align-items: flex-start; /* Aligns content to the left */
                gap: 1rem;
                box-shadow: 2px 0 10px rgba(0, 0, 0, 0.2);
            }

            .sidebar img {
                width: 150px;
                height: 150px;
                object-fit: contain;
                align-self: center; /* Keeps the image centered */
            }

            .sidebar h2 {
                text-align: left; /* Aligns the menu text to the left */
                font-size: 1.2rem;
                margin-top: 1rem;
                width: 100%;
            }

            .sidebar a {
                color: #fff;
                text-decoration: none;
                font-size: 1rem;
                display: flex;
                align-items: center;
                gap: 0.5rem;
                padding: 0.5rem 1rem;
                border-radius: 5px;
                transition: background 0.3s;
                width: 100%; /* Ensures links take full width for proper alignment */
            }

            .sidebar a:hover {
                background: #6a11cb;
            }

            .content-wrapper {
                margin-left: 220px;
                padding: 1rem;
            }
             .booking-list {
        margin: 20px auto;
        width: 80%;
        max-width: 600px;
        display: flex;
        flex-direction: column;
        gap: 25px; /* Increased spacing between booking items */
    }

    .booking-item {
        font-weight: bold;
        display: flex;
        flex-direction: column;
        align-items: center;
        background: #ffffff;
        padding: 25px; /* More spacing inside */
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px; /* Extra spacing between items */
    }

    .booking-info {
        display: none;
        margin-top: 15px; /* Adds space between item title and details */
        padding: 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        background: #1F4068;
        width: 100%;
        animation: fadeIn 0.3s ease-in-out;
        color: white;
        line-height: 1.8; /* More spacing between lines */
    }

    .booking-info p {
        padding: 8px 0; /* Adds space between text blocks */
        margin-bottom: 5px;
    }

    .view-button, .hide-button {
        background-color: #007BFF;
        color: white;
        border: none;
        padding: 12px 18px; /* Increased button padding */
        cursor: pointer;
        border-radius: 5px;
        margin-top: 12px; /* More spacing between button and content */
        transition: background 0.3s ease;
    }

    .view-button:hover, .hide-button:hover {
        background-color: #0056b3;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(-10px); }
        to { opacity: 1; transform: translateY(0); }
    }
        </style>
        <script>
            function toggleDetails(id) {
                var infoDiv = document.getElementById("info-" + id);
                if (infoDiv.style.display === "none" || infoDiv.style.display === "") {
                    infoDiv.style.display = "block";
                } else {
                    infoDiv.style.display = "none";
                }
            }
        </script>
    </head>
    <body>
        <div class="sidebar">
            <img src="../image/item/carelogo.png" alt="Care Giver Logo">
            <a><h2>Menu</h2><a>
            <a href="home.jsp"><span>🏠</span> Home</a>
            <a href="booking.jsp"><span>📦</span> Packages</a>
            <a href="booking.jsp"><span>📅</span> Booking</a>
            <a href="bookingDetails.jsp"><span>📖</span> My Booking</a>
        </div>
        <div class="content-wrapper">
            <h2>Booking Details</h2>
            <div class="booking-list">
                <h3>Your Bookings</h3>
                <%
                    try {
                        conn = DatabaseConnection.getConnection();
                        String query = "SELECT BOOKING_ID, BOOKING_TIME, BOOKING_TYPE, CARETAKER_ID FROM booking WHERE CUST_ID = ? ORDER BY BOOKING_TIME DESC";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, customerId);
                        rs = pstmt.executeQuery();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a"); // Extracting only time format

                        while (rs.next()) {
                            int bookingId = rs.getInt("BOOKING_ID");
                            String bookingDate = dateFormat.format(rs.getTimestamp("BOOKING_TIME")); // Displaying only date
                            String bookingStartTime = timeFormat.format(rs.getTimestamp("BOOKING_TIME")); // Only time
                            String bookingType = rs.getString("BOOKING_TYPE");
                            int caretakerId = rs.getInt("CARETAKER_ID");

                            String caretakerName = "Not Assigned";
                            PreparedStatement caretakerStmt = conn.prepareStatement("SELECT caretaker_name FROM caretaker WHERE caretaker_id = ?");
                            caretakerStmt.setInt(1, caretakerId);
                            ResultSet caretakerRs = caretakerStmt.executeQuery();
                            if (caretakerRs.next()) {
                                caretakerName = caretakerRs.getString("caretaker_name");
                            }
                            caretakerRs.close();
                            caretakerStmt.close();
                %>
                <div class="booking-item">
                    <span><%= bookingDate%></span> <!-- Date only -->
                    <button class="view-button" onclick="toggleDetails('<%= bookingId%>')">View</button>
                    <div class="booking-info" id="info-<%= bookingId%>">
                        <p><strong>Booking Type:</strong> <%= bookingType%></p>
                        <p><strong>Caretaker Name:</strong> <%= caretakerName%></p>
                        <p><strong>Booking Start Time:</strong> <%= bookingStartTime%></p> <!-- Start Time under Caretaker Name -->
                        <button class="hide-button" onclick="toggleDetails('<%= bookingId%>')">Hide</button>
                    </div>
                </div>
                <%
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (pstmt != null) {
                            pstmt.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    }
                %>
            </div>
        </div>
    </body>
</html>
