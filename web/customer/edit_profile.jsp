<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            <form action="UpdateProfileServlet" method="POST">
                <label for="name">Full Name:</label>
                <input type="text" id="name" name="name" value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : "" %>" required>

                <label for="email">Email Address:</label>
                <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required>

                <label for="phone">Phone Number:</label>
                <input type="tel" id="phone" name="phone" value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>" required>

                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="<%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %>" required>

                <button type="submit" class="save-btn">Save Changes</button>
            </form>
        </main>

        <!-- Footer -->
        <footer>
            <p>© 2024 Care Giver. All rights reserved.</p>
        </footer>

    </body>
</html>
