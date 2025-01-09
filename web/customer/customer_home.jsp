<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.html");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Care Giver - Customer Page</title>
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
            overflow-x: hidden;
            display: flex;
            flex-direction: column;
        }
        header {
            background-color: #2c3e50;
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
            padding: 2rem;
            margin-top: 2rem;
            text-align: center;
        }
        main h1 {
            font-size: 2.5rem;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 6px rgba(0, 0, 0, 0.5);
        }
        .service-section {
            margin: 2rem auto;
            max-width: 900px;
        }
        .service-card {
            background: rgba(255, 255, 255, 0.1);
            padding: 2rem;
            margin: 1rem 0;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            text-align: left;
        }
        .service-card h3 {
            font-size: 1.5rem;
            color: #fff;
            margin-bottom: 10px;
        }
        .service-card ul {
            margin-top: 10px;
            padding-left: 20px;
            color: #ddd;
        }
        .service-card ul li {
            margin-bottom: 5px;
        }
        .price {
            font-size: 1.2rem;
            color: #00bcd4;
            margin-top: 15px;
            font-weight: bold;
        }
        .contact-section {
            background: #2c3e50;
            color: #fff;
            padding: 2rem;
            margin-top: 3rem;
            border-radius: 10px;
            text-align: center;
        }
        .contact-section h3 {
            font-size: 1.8rem;
            margin-bottom: 1rem;
        }
        .contact-section p {
            margin-bottom: 1rem;
        }
        footer {
            background-color: #2c3e50;
            color: #fff;
            text-align: center;
            padding: 1rem;
            margin-top: 2rem;
        }
        .booking-btn {
            margin-top: 20px;
            padding: 0.75rem 1.5rem;
            background-color: #00bcd4;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 1rem;
            cursor: pointer;
            transition: 0.3s;
        }
        .booking-btn:hover {
            background-color: #0097a7;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header>
        <div class="logo">Care Giver</div>
        <nav>
            <a href="#">🏠 Home</a>
            <a href="about.html">ℹ️ About Us</a>
            <a href="profile.html">👤 View Profile</a>
            <a href="/Family_Care/logout">🚪 Logout</a>
        </nav>
    </header>

    <!-- Main Content -->
    <main>
        <h1>Welcome Back, <%= username %>!</h1>
        <p>We are here to assist you with premium caregiving services tailored to your needs.</p>

        <!-- Start Booking Button -->
        <button class="booking-btn" onclick="window.location.href='booking.html'">Start Booking</button>

        <!-- Services Section -->
        <section class="service-section">
            <div class="service-card">
                <h3>Baby Home Care Package</h3>
                <ul>
                    <li>Eating assistance</li>
                    <li>Diapering</li>
                    <li>Sleeping supervision</li>
                    <li>Bonding activities</li>
                </ul>
                <p class="price">RM 10 per hour</p>
            </div>

            <div class="service-card">
                <h3>Elder Home Care Package</h3>
                <ul>
                    <li>Dispensing Medications</li>
                    <li>Feeding/Prepare Meal</li>
                    <li>Light Physiotherapy</li>
                    <li>Companionship</li>
                    <li>Outdoor Exercise/Activity</li>
                    <li>Mind Wellness Activities</li>
                </ul>
                <p class="price">RM 12 per hour</p>
            </div>
        </section>

        <!-- Contact Section -->
        <section class="contact-section">
            <h3>Contact Us</h3>
            <p>If you have any questions, feel free to contact our support team.</p>
            <p>Email: support@caregiver.com | Phone: +60 123 456 789</p>
        </section>
    </main>

    <!-- Footer -->
    <footer>
        <p>© 2024 Care Giver. All rights reserved.</p>
    </footer>
</body>
</html>
