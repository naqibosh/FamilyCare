<%-- 
    Document   : caretaker_homepage
    Created on : Jan 20, 2025, 12:43:43 AM
    Author     : Naqib
--%>
<%
    HttpSession sessions = request.getSession(false);
    String caretakerName = (sessions != null) ? (String) sessions.getAttribute("caretakerName") : null;

    if (caretakerName == null) {
        response.sendRedirect("login.jsp?errorMessage=Session expired or invalid. Please log in again.");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Caretaker Dashboard</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
            /* Global Styles */
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f4f8;
                color: #333;
            }

            /* Navigation Bar */
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

            /* Welcome Header */
            .welcome-header {
                text-align: center;
                padding: 50px 20px;
                background-color: #ffffff;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }
            .welcome-header h1 {
                font-size: 32px;
                color: #444;
                margin-bottom: 10px;
            }
            .welcome-header p {
                font-size: 16px;
                color: #666;
            }

            /* Dashboard Cards */
            .dashboard {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                justify-content: center;
                padding: 20px;
            }
            .card {
                background: white;
                border-radius: 12px;
                padding: 20px;
                width: 300px;
                text-align: center;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: transform 0.2s, box-shadow 0.2s;
            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
            }
            .card h3 {
                margin: 15px 0;
                font-size: 20px;
                color: #4682b4;
            }
            .card p {
                font-size: 14px;
                color: #666;
                margin: 10px 0 20px;
            }
            .card a {
                text-decoration: none;
                color: white;
                background-color: #4682b4;
                padding: 10px 20px;
                border-radius: 5px;
                font-weight: 500;
                transition: background-color 0.2s;
            }
            .card a:hover {
                background-color: #5a9bd3;
            }

            /* Footer */
            .footer {
                text-align: center;
                padding: 15px 20px;
                background-color: #f8f9fa;
                border-top: 1px solid #ddd;
                font-size: 14px;
                color: #777;
            }
            .footer a {
                color: #4682b4;
                text-decoration: none;
                font-weight: 500;
            }
            .footer a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <!-- Navigation Bar -->
        <div class="navbar">
            <span>FamilyCare Dashboard</span>
            <div>
                <a href="../caretakerProfileServlet">Profile</a>
                <a href="../caretakerLogoutServlet">Logout</a>
            </div>
        </div>

        <!-- Welcome Header -->
        <div class="welcome-header">
            <h1>Welcome, <%= caretakerName%>!</h1>
            <p>Your dashboard to manage your profile, schedule, and bookings.</p>
        </div>

        <!-- Dashboard Cards -->
        <div class="dashboard">
            <div class="card">
                <h3>Incoming Job</h3>
                <p>View your upcoming job .</p>
                <a href="../JobListServlet" class="btn btn-primary">View Jobs</a>
            </div>
            <div class="card">
                <h3>Your Status</h3>
                <p>Change your current status here.</p>
                <a href="reviewStatus.jsp">Review Status</a>
            </div>
            <div class="card">
                <h3>Schedule</h3>
                <p>View and manage your upcoming schedule.</p>
                <a href="schedule.jsp">Go to Schedule</a>
            </div>

        </div>

        <!-- Footer -->
        <div class="footer">
            <p>&copy; 2025 FamilyCare. All Rights Reserved. | <a href="contact.jsp">Contact Us</a></p>
        </div>
    </body>
</html>
