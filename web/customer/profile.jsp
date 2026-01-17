<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="dbconn.DatabaseConnection" %>
<%
    // Check if the user is logged in
    Integer customerId = (Integer) session.getAttribute("customerId");
    if (customerId == null) {
        // Redirect to login if not logged in
        response.sendRedirect("login.jsp?error=Please log in to view your profile.");
        return;
    }

    // Initialize variables
    String firstName = "", lastName = "", username = "", email = "", phone = "";

    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT CUST_FIRST_NAME, CUST_LAST_NAME, CUST_USERNAME, CUST_EMAIL, CUST_PHONE_NUMBER FROM CUSTOMER WHERE CUST_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            firstName = rs.getString("CUST_FIRST_NAME");
            lastName = rs.getString("CUST_LAST_NAME");
            username = rs.getString("CUST_USERNAME");
            email = rs.getString("CUST_EMAIL");
            phone = rs.getString("CUST_PHONE_NUMBER");
        } else {
            response.sendRedirect("login.jsp?error=Profile not found.");
            return;
        }

        // Close resources
        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp?message=Unable to load profile.");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Care Giver - View Profile</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* General Styles */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }

            html, body {
                width: 100%;
                height: 100%;
                background-color: #FFFFFF;
                color: #0A1931;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            /* Header */
            header {
                background-color: #162447;
                width: 100%;
                padding: 1rem 2rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            }

            header .logo {
                display: flex;
                align-items: center;
                font-size: 1.8rem;
                font-weight: bold;
                color: #FFFFFF;
            }

            header .logo img {
                width: 50px;
                height: 50px;
                margin-right: 10px;
            }

            header nav {
                display: flex;
                gap: 1rem;
            }

            header nav a {
                text-decoration: none;
                color: #FFFFFF;
                font-size: 1.1rem;
                padding: 0.5rem 1rem;
                border-radius: 5px;
                transition: background 0.3s ease-in-out;
            }

            header nav a:hover {
                background-color: #1F4068;
            }

            /* Main Section */
            main {
                width: 100%;
                max-width: 800px;
                background: #162447;
                padding: 2rem;
                border-radius: 10px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
                margin-top: 2rem;
                text-align: center;
            }

            /* Profile Card */
            .profile-card {
                margin-bottom: 2rem;
                text-align: left;
            }

            .profile-card h3 {
                font-size: 1.6rem;
                margin-bottom: 1rem;
                color: #FFFFFF;
            }

            .profile-card p {
                font-size: 1.1rem;
                margin-bottom: 10px;
                color: #EAEAEA;
            }

            .edit-btn {
                padding: 0.8rem 1.5rem;
                background-color: #00A8E8;
                border: none;
                border-radius: 5px;
                color: white;
                font-size: 1.1rem;
                cursor: pointer;
                transition: 0.3s;
                margin-top: 20px;
            }

            .edit-btn:hover {
                background-color: #0077B6;
            }

            /* Footer */
            footer {
                background-color: #162447;
                color: #FFFFFF;
                text-align: center;
                padding: 1rem;
                width: 100%;
                margin-top: auto;
            }
            /* Profile Card */
            .profile-card {
                margin-bottom: 2rem;
                text-align: left;
                background-color: #1F4068; /* Slightly lighter blue */
                padding: 1.5rem;
                border-radius: 10px;
                transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
            }

            .profile-card:hover {
                transform: scale(1.03);
                box-shadow: 0 8px 20px rgba(255, 255, 255, 0.2);
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                header {
                    flex-direction: column;
                    align-items: center;
                    text-align: center;
                }

                header nav {
                    flex-wrap: wrap;
                    justify-content: center;
                }

                main {
                    padding: 1.5rem;
                }
            }

        </style>
    </head>
    <body>
        <%-- Placeholder for dynamic username --%>

        <!-- Header -->
        <header>
            <div class="logo">
                <img src="../image/item/carelogo.png" alt="Care Giver Logo" style="width: 50px; height: 50px; margin-right: 10px;">
                <span>Care Giver</span>
            </div>
            <nav>
                <a href="home.jsp">🏠 Home</a>
                <a href="bookingDetails.jsp">ℹ️ My Booking</a>
                <a href="profile.jsp">👤 View Profile</a>
                <a href="http://localhost:8081/Family_Care/index.html">🚪 Logout</a>
            </nav>
        </header>

        <h1 style="margin-top: 20px; margin-bottom: -10px; font-weight: bold;">Welcome, <%= username%></h1>

        <!-- Main Content -->
        <main> 
            <div class="profile-card">
                <h3>Customer Information</h3><br>
                <p><strong>Name:</strong> <%= firstName + " " + lastName%></p>
                <p><strong>Username:</strong> <%= username%></p>
                <p><strong>Email:</strong> <%= email%></p>
                <p><strong>Phone Number:</strong> <%= phone%></p>
                <button class="edit-btn" onclick="window.location.href = 'edit_profile.jsp'">Edit Profile</button>
            </div>
        </main>

        <!-- Footer -->
        <footer>
            <p>© 2024 Care Giver. All rights reserved.</p>
        </footer>
    </body>
</html>