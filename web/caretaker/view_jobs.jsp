<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.NumberFormat"%>

<html>
    <head>
        <title>Assigned Jobs</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f7fa;
            }
            .navbar {
                background-color: #007bff;
                color: white;
                padding: 10px 0;
                text-align: center;
            }
            .navbar h1 {
                margin: 0;
                font-size: 24px;
            }
            .container {
                width: 80%;
                margin: 50px auto;
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            h2 {
                text-align: center;
                color: #333;
            }
            .message, .error {
                padding: 10px;
                text-align: center;
                margin-bottom: 20px;
            }
            .message {
                background-color: #d4edda;
                color: #155724;
            }
            .error {
                background-color: #f8d7da;
                color: #721c24;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 15px;
                text-align: left;
                border: 1px solid #ddd;
                font-size: 14px;
            }
            th {
                background-color: #f8f9fa;
                color: #495057;
            }
            .back-btn, .update-btn {
                display: inline-block;
                padding: 12px 20px;
                background-color: #5bc0de;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                font-weight: bold;
                margin-top: 10px;
                text-align: center;
            }
            .back-btn:hover, .update-btn:hover {
                background-color: #31b0d5;
            }
            .status-select {
                padding: 6px 12px;
                font-size: 14px;
                border-radius: 4px;
                border: 1px solid #ddd;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <h1>Caretaker Dashboard</h1>
        </div>
        <div class="container">
            <h2>Assigned Jobs</h2>
            <%
                List<Map<String, Object>> jobs = (List<Map<String, Object>>) request.getAttribute("jobs");
                String message = (String) request.getAttribute("message");
                String error = (String) request.getAttribute("error");

                if (message != null) {
                    out.println("<div class='message'>" + message + "</div>");
                }
                if (error != null) {
                    out.println("<div class='error'>" + error + "</div>");
                }
            %>
            <a href="caretaker/caretaker_homepage.jsp" class="back-btn">Back</a>
            <% if (jobs != null && !jobs.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>Customer Name</th>
                        <th>Customer Phone</th>
                        <th>Booking Time</th>
                        <th>Booking Duration</th>
                        <th>Booking Price</th>
                        <th>Booking Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                        for (Map<String, Object> job : jobs) {
                            String customerName = (String) job.get("customerName");
                            String customerPhone = (String) job.get("customerPhone");
                            String bookingTime = (String) job.get("bookingTime");
                            String bookingDuration = (String) job.get("bookingDuration");
                            Double bookingPrice = (Double) job.get("bookingPrice");
                            String bookingStatus = (String) job.get("bookingStatus");
                            Integer bookingId = (Integer) job.get("bookingId");
                    %>
                    <tr>
                        <td><%= customerName %></td>
                        <td><%= customerPhone %></td>
                        <td><%= bookingTime %></td>
                        <td><%= bookingDuration %></td>
                        <td><%= currencyFormat.format(bookingPrice) %></td>
                        <td>
                            <form action="UpdateBookingStatusServlet" method="post">
                                <input type="hidden" name="bookingId" value="<%= bookingId %>" />
                                <select name="status" class="status-select">
                                    <option value="Pending" <%= "Pending".equals(bookingStatus) ? "selected" : "" %>>Pending</option>
                                    <option value="Completed" <%= "Completed".equals(bookingStatus) ? "selected" : "" %>>Completed</option>
                                    <option value="Canceled" <%= "Canceled".equals(bookingStatus) ? "selected" : "" %>>Canceled</option>
                                </select>
                        </td>
                        <td>
                                <button type="submit" class="update-btn">Update</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p style="text-align: center;">No jobs assigned.</p>
            <% } %>
        </div>
    </body>
</html>
