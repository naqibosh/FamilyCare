<%-- 
    Document   : login
    Created on : Jan 18, 2025, 3:20:22 PM
    Author     : Naqib
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Customer Login</title>
        <style>
            /* Basic styling */
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
            }

            /* Login form container */
            .login-container {
                background-color: #ffffff;
                padding: 2rem;
                width: 350px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                text-align: center;
            }

            .login-container h2 {
                margin-bottom: 1rem;
                color: #333;
                font-size: 1.5rem;
            }

            .login-container p {
                font-size: 0.9rem;
                color: #666;
                margin-bottom: 1.5rem;
            }

            .login-container input[type="text"],
            .login-container input[type="password"] {
                width: 100%;
                padding: 0.75rem;
                margin: 0.5rem 0;
                border-radius: 5px;
                border: 1px solid #ddd;
                font-size: 1rem;
            }

            .login-container input[type="text"]:focus,
            .login-container input[type="password"]:focus {
                border-color: #3498db;
                outline: none;
            }

            /* Login and Register buttons */
            .button-container {
                display: flex;
                gap: 1rem;
                margin-top: 1rem;
            }

            .login-button, .register-button {
                flex: 1;
                padding: 0.75rem;
                border: none;
                border-radius: 5px;
                font-size: 1rem;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .login-button {
                background-color: #3498db;
                color: white;
            }

            .login-button:hover {
                background-color: #2980b9;
            }

            .register-button {
                background-color: #95a5a6;
                color: white;
            }

            .register-button:hover {
                background-color: #7f8c8d;
            }

            /* Link back to dashboard */
            .login-container .back-link {
                display: block;
                margin-top: 1.5rem;
                font-size: 0.9rem;
                color: #3498db;
                text-decoration: none;
            }

            .login-container .back-link:hover {
                text-decoration: underline;
            }

            /* Icon or avatar */
            .avatar {
                width: 80px;
                height: 80px;
                margin-bottom: 1rem;
                border-radius: 50%;
                background-color: #3498db;
                display: flex;
                justify-content: center;
                align-items: center;
                color: white;
                font-size: 2rem;
                font-weight: bold;
            }
        </style>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>

        <!-- Login Form Container -->
        <div class="login-container">
            <!-- Avatar or Icon -->
            <div class="avatar">C</div>
            <h2>Customer Login</h2>
            <p>Please enter your login details below.</p>
            <form action="../LoginServlet" method="post">
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <div class="button-container">
                    <button type="submit" class="login-button">Login</button>
                    <a href="register.jsp" class="register-button">Register</a>
                </div>
            </form>
            <a href="../index.html" class="back-link">Back to Dashboard</a>
        </div>

        <!-- SweetAlert Script -->
        <script>
            // Retrieve query parameters
            const urlParams = new URLSearchParams(window.location.search);
            const successMessage = urlParams.get('success');
            const errorMessage = urlParams.get('error');

            // Show success message if it exists
            if (successMessage) {
                Swal.fire({
                    icon: 'success',
                    title: 'Success',
                    text: successMessage,
                    confirmButtonText: 'OK'
                });
            }

            // Show error message if it exists
            if (errorMessage) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: errorMessage,
                    confirmButtonText: 'OK'
                });
            }
        </script>
    </body>
</html>
