<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.NumberFormat"%>

<html>
    <head>
        <title>Assigned Jobs</title>
        <style>
            /* Basic styling for the page */
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 80%;
                margin: 50px auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            h2 {
                text-align: center;
                color: #333;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
            }

            th, td {
                padding: 12px;
                text-align: left;
                border: 1px solid #ddd;
                font-size: 14px;
            }

            th {
                background-color: #f4f4f4;
                color: #555;
            }

            td {
                background-color: #fafafa;
            }

            /* Message and error styling */
            .message, .error {
                padding: 10px;
                text-align: center;
                margin-bottom: 20px;
            }

            .message {
                background-color: #dff0d8;
                color: #3c763d;
            }

            .error {
                background-color: #f2dede;
                color: #a94442;
            }

            /* Button styles */
            .back-btn {
                display: inline-block;
                padding: 10px 15px;
                background-color: #5bc0de;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
                margin: 10px 0;
                text-align: center;
            }

            .back-btn:hover {
                background-color: #31b0d5;
            }

            .status-select {
                padding: 5px;
            }

            .update-btn {
                padding: 5px 10px;
                background-color: #5cb85c;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .update-btn:hover {
                background-color: #4cae4c;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h2>Assigned Jobs</h2>

            <%
                // Retrieve job data, messages, and errors from request attributes
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

            <a href="javascript:history.back()" class="back-btn">Back</a>

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
                    <%
                        // Format for currency (Booking Price)
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

                        // Loop through each job and display details
                        for (Map<String, Object> job : jobs) {
                            String customerName = (String) job.get("customerName");
                            String customerPhone = (String) job.get("customerPhone");
                            String bookingTime = (String) job.get("bookingTime");
                            String bookingDuration = (String) job.get("bookingDuration");
                            Double bookingPrice = (Double) job.get("bookingPrice");
                            String bookingStatus = (String) job.get("bookingStatus");
                            Integer bookingId = (Integer) job.get("bookingId");

                            // Safeguard against null values
                            if (customerName == null) customerName = "N/A";
                            if (customerPhone == null) customerPhone = "N/A";
                            if (bookingTime == null) bookingTime = "N/A";
                            if (bookingDuration == null) bookingDuration = "N/A";
                            if (bookingPrice == null) bookingPrice = 0.0;
                            if (bookingStatus == null) bookingStatus = "Pending";
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
