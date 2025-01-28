<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="dbconn.DatabaseConnection" %>
<%
    // Check if the user is logged in
    Integer customerId = (Integer) session.getAttribute("customerId");
    if (customerId == null) {
        // Redirect to login if not logged in
        response.sendRedirect("login.jsp?error=Please log in to edit your profile.");
        return;
    }

    // Initialize customer details
    String firstName = "", lastName = "", username = "", email = "", phone = "";

    try {
        // Retrieve customer data from the database
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
        <title>Edit Profile - Care Giver</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* General Styles */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }

            body {
                background: linear-gradient(to bottom right, #6a11cb, #2575fc);
                color: #f4f4f4;
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 20px;
            }

            header {
                background-color: #2c3e50;
                width: 100%;
                padding: 1rem 2rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            }

            header .logo {
                font-size: 1.5rem;
                font-weight: bold;
                color: #fff;
            }

            header nav {
                display: flex;
                gap: 1rem;
            }

            header nav a {
                text-decoration: none;
                color: #fff;
                padding: 0.5rem 1rem;
                border-radius: 5px;
                transition: background 0.3s ease-in-out;
            }

            header nav a:hover {
                background-color: #34495e;
            }

            main {
                width: 100%;
                max-width: 800px;
                background: rgba(255, 255, 255, 0.1);
                padding: 2rem;
                border-radius: 10px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
                margin-top: 2rem;
                text-align: center;
            }

            h1 {
                font-size: 2.5rem;
                margin-bottom: 1rem;
                text-shadow: 2px 2px 6px rgba(0, 0, 0, 0.5);
            }

            form {
                display: flex;
                flex-direction: column;
                gap: 1.5rem;
            }

            label {
                font-size: 1.1rem;
                color: #fff;
            }

            input {
                padding: 0.75rem;
                font-size: 1rem;
                border: 1px solid #ddd;
                border-radius: 5px;
                background-color: rgba(255, 255, 255, 0.2);
                color: #fff;
            }

            .save-btn {
                padding: 0.75rem 1.5rem;
                background-color: #00bcd4;
                border: none;
                border-radius: 5px;
                color: white;
                font-size: 1rem;
                cursor: pointer;
                transition: 0.3s;
                margin-top: 20px;
            }

            .save-btn:hover {
                background-color: #0097a7;
            }

            footer {
                background-color: #2c3e50;
                color: #fff;
                text-align: center;
                padding: 1rem;
                width: 100%;
                margin-top: 3rem;
            }
        </style>
    </head>
    <body>
        <script>
            function validateForm() {
                const fullName = document.getElementById('fullName').value.trim();
                const nameParts = fullName.split(' ');

                if (nameParts.length < 2) {
                    alert("Please enter both first and last names.");
                    return false;
                }
                return true;
            }
        </script>
        <!-- Header -->
        <header>
            <div class="logo">Care Giver</div>
            <nav>
                <a href="home.jsp">🏠 Home</a>
                <a href="about.jsp">ℹ️ About Us</a>
                <a href="profile.jsp">👤 View Profile</a>
                <a href="logout.jsp">🚪 Logout</a>
            </nav>
        </header>

        <!-- Main Content -->
        <main>
            <h1>Edit Your Profile</h1>

            <!-- Edit Profile Form -->
            <form action="../UpdateProfileServlet" method="POST" onsubmit="return validateForm()">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" value="<%= firstName + " " + lastName%>" required>

                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= username%>" required>

                <label for="email">Email Address:</label>
                <input type="email" id="email" name="email" value="<%= email%>" required>

                <label for="phone">Phone Number:</label>
                <input type="text" id="phone" name="phone" value="<%= phone%>" required>

                <button type="submit" class="save-btn">Save Changes</button>
            </form>
        </main>

        <!-- Footer -->
        <footer>
            <p>© 2024 Care Giver. All rights reserved.</p>
        </footer>

    </body>
</html>
