<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Job List</title>
</head>
<body>

<h2>Assigned Jobs</h2>

<% 
    List<Map<String, Object>> jobs = (List<Map<String, Object>>) request.getAttribute("jobs");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");

    if (message != null) {
        out.println("<p>" + message + "</p>");
    }

    if (error != null) {
        out.println("<p>Error: " + error + "</p>");
    }
%>

<% if (jobs != null && !jobs.isEmpty()) { %>
    <table border="1">
        <thead>
            <tr>
                <th>Customer Name</th>
                <th>Customer Phone</th>
                <th>Booking Time</th>
                <th>Booking Duration</th>
                <th>Booking Price</th>
            </tr>
        </thead>
        <tbody>
            <% 
                for (Map<String, Object> job : jobs) {
                    String customerName = (String) job.get("customerName");
                    String customerPhone = (String) job.get("customerPhone");
                    String bookingTime = (String) job.get("bookingTime");
                    int bookingDuration = (int) job.get("bookingDuration");
                    double bookingPrice = (double) job.get("bookingPrice");
            %>
            <tr>
                <td><%= customerName %></td>
                <td><%= customerPhone %></td>
                <td><%= bookingTime %></td>
                <td><%= bookingDuration %> hours</td>
                <td>$<%= bookingPrice %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
<% } else { %>
    <p>No jobs assigned.</p>
<% } %>

</body>
</html>
