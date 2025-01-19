<%-- 
    Document   : register
    Created on : Jan 18, 2025, 3:17:01 PM
    Author     : Naqib
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Customer Registration</title>
        <style>
            /* Basic page styling */
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
            }

            /* Registration form container */
            .register-container {
                background-color: #ffffff;
                padding: 2rem;
                width: 400px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                text-align: center;
            }

            .register-container h2 {
                margin-bottom: 1rem;
                color: #333;
                font-size: 1.5rem;
            }

            /* Input fields styling */
            .register-container input[type="text"],
            .register-container input[type="email"],
            .register-container input[type="password"],
            .register-container input[type="tel"] {
                width: 100%;
                padding: 0.75rem;
                margin: 0.5rem 0;
                border-radius: 5px;
                border: 1px solid #ddd;
                font-size: 1rem;
            }

            .register-container input[type="text"]:focus,
            .register-container input[type="email"]:focus,
            .register-container input[type="password"]:focus,
            .register-container input[type="tel"]:focus {
                border-color: #3498db;
                outline: none;
            }

            /* Submit button styling */
            .register-container button {
                width: 100%;
                padding: 0.75rem;
                margin-top: 1rem;
                border: none;
                border-radius: 5px;
                background-color: #3498db;
                color: white;
                font-size: 1rem;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .register-container button:hover {
                background-color: #2980b9;
            }

            /* Link to return to login */
            .register-container .back-link {
                display: block;
                margin-top: 1.5rem;
                font-size: 0.9rem;
                color: #3498db;
                text-decoration: none;
            }

            .register-container .back-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>

        <div class="register-container">
            <h2>Customer Registration</h2>
            <form action="../RegisterServlet" method="post">
                <input type="text" name="username" placeholder="Username" required>
                <input type="text" name="first_name" placeholder="First Name" required>
                <input type="text" name="last_name" placeholder="Last Name" required>
                <input type="tel" name="phone" placeholder="Phone Number" required pattern="[0-9]{10,15}" title="Enter a valid phone number">
                <input type="email" name="email" placeholder="Email" required>
                <input type="text" name="ic_number" placeholder="IC Number" required>
                <input type="password" name="password" placeholder="Password" required>
                <input type="password" name="confirm_password" placeholder="Confirm Password" required>
                <button type="submit">Register</button>
            </form>
            <a href="login.html" class="back-link">Back to Login</a>
        </div>

    </body>
</html>

