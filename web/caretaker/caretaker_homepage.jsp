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
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #eef2f7;
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
                transition: color 0.3s ease-in-out;
            }
            .navbar a:hover {
                color: #ffdd57;
            }

            /* Welcome Section */
            .welcome-header {
                text-align: center;
                padding: 50px 20px;
                background: linear-gradient(135deg, #4682b4, #5a9bd3);
                color: white;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
                animation: fadeIn 1s ease-in-out;
            }
            .welcome-header h1 {
                font-size: 36px;
                margin-bottom: 10px;
            }
            .welcome-header p {
                font-size: 18px;
            }

            /* Dashboard */
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
                padding: 25px;
                width: 320px;
                text-align: center;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                animation: fadeInUp 0.8s ease-in-out;
            }
            .card:hover {
                transform: translateY(-8px);
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            }
            .card h3 {
                font-size: 22px;
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
                background: #4682b4;
                padding: 12px 24px;
                border-radius: 6px;
                font-weight: 500;
                transition: background 0.3s ease-in-out;
            }
            .card a:hover {
                background: #ffdd57;
                color: #333;
            }

            /* Footer */
            .footer {
                text-align: center;
                padding: 15px;
                background: #f8f9fa;
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

            /* Animations */
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(-10px); }
                to { opacity: 1; transform: translateY(0); }
            }
            @keyframes fadeInUp {
                from { opacity: 0; transform: translateY(20px); }
                to { opacity: 1; transform: translateY(0); }
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <span>FamilyCare Dashboard</span>
            <div>
                <a href="../caretakerProfileServlet">Profile</a>
                <a href="../caretakerLogoutServlet">Logout</a>
            </div>
        </div>

        <div class="welcome-header">
            <h1>Welcome, <%= caretakerName%>!</h1>
            <p>Your dashboard to manage your profile and bookings.</p>
        </div>

        <div class="dashboard">
            <div class="card">
                <h3>Incoming Job</h3>
                <p>View your upcoming job assignments.</p>
                <a href="../JobListServlet">View Jobs</a>
            </div>
            <div class="card">
                <h3>Your Status</h3>
                <p>Change your current availability status.</p>
                <a href="reviewStatus.jsp">Review Status</a>
            </div>
        </div>

        <div class="footer">
            <p>&copy; 2025 FamilyCare. All Rights Reserved.</a></p>
        </div>
    </body>
</html>
