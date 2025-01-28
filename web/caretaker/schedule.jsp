<%-- 
    Document   : caretaker_schedule
    Created on : Jan 27, 2025
    Author     : Naqib
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*, java.util.*"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Caretaker Schedule</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f4f8;
                color: #333;
            }

            .navbar {
                background-color: #4682b4;
                color: white;
                padding: 15px 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            }

            .navbar a {
                color: white;
                text-decoration: none;
                margin-left: 20px;
                font-weight: 500;
                font-size: 16px;
            }

            .navbar a:hover {
                text-decoration: underline;
            }

            .container {
                padding: 20px;
            }

            .calendar {
                display: grid;
                grid-template-columns: repeat(7, 1fr);
                gap: 10px;
                margin-top: 20px;
            }

            .day {
                background: white;
                border-radius: 8px;
                padding: 15px;
                text-align: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                position: relative;
            }

            .day .date {
                font-weight: bold;
                color: #4682b4;
                margin-bottom: 5px;
            }

            .day .note {
                background-color: #ffd700;
                color: #333;
                border-radius: 5px;
                padding: 5px;
                font-size: 12px;
                position: absolute;
                bottom: 10px;
                left: 10px;
                right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <span>FamilyCare Schedule</span>
            <div>
                <a href="caretaker_homepage.jsp">Home</a>
                <a href="../caretakerLogoutServlet">Logout</a>
            </div>
        </div>

        <div class="container">
            <h1>Your Schedule</h1>
            <p>View your upcoming jobs directly on the calendar.</p>

            <div class="calendar">
                <% 
                    // Connect to the database
                    String caretakerId = (String) sessions.getAttribute("caretakerId");
                    Connection con = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    Map<Integer, String> jobNotes = new HashMap<>();

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/familycare", "root", "password");
                        String query = "SELECT job_date, notes FROM jobs WHERE caretaker_id = ?";
                        ps = con.prepareStatement(query);
                        ps.setString(1, caretakerId);
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            Date jobDate = rs.getDate("job_date");
                            String note = rs.getString("notes");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(jobDate);
                            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                            jobNotes.put(dayOfMonth, note);
                        }
                    } catch (Exception e) {
                        out.println("<p>Error loading schedule: " + e.getMessage() + "</p>");
                    } finally {
                        if (rs != null) rs.close();
                        if (ps != null) ps.close();
                        if (con != null) con.close();
                    }

                    // Generate the calendar
                    Calendar calendar = Calendar.getInstance();
                    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    for (int day = 1; day <= daysInMonth; day++) {
                        boolean hasJob = jobNotes.containsKey(day);
                        String note = hasJob ? jobNotes.get(day) : "";
                %>

                <div class="day">
                    <div class="date"><%= day %></div>
                    <% if (hasJob) { %>
                        <div class="note"><%= note %></div>
                    <% } %>
                </div>

                <% } %>
            </div>
        </div>
    </body>
</html>
