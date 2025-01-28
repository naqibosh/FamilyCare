<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Caretaker Login</title>
        <style>
            /* Global styles */
            body {
                font-family: 'Roboto', Arial, sans-serif;
                background-color: #f5f5f5; /* Soft neutral color */
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                color: #333;
            }

            /* Login container */
            .login-container {
                background: #ffffff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
                width: 350px;
                text-align: center;
            }

            /* Header */
            .login-container h2 {
                margin-bottom: 20px;
                font-size: 24px;
                color: #444;
                font-weight: bold;
            }

            /* Form group */
            .form-group {
                margin-bottom: 20px;
                text-align: left;
            }

            /* Labels */
            .form-group label {
                display: block;
                font-size: 14px;
                margin-bottom: 5px;
                color: #666;
            }

            /* Input fields */
            .form-group input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
                outline: none;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            .form-group input:focus {
                border-color: #87cefa;
                box-shadow: 0 0 5px rgba(135, 206, 250, 0.5);
            }

            /* Submit button */
            .form-group button {
                width: 100%;
                padding: 12px;
                background-color: #87cefa;
                border: none;
                border-radius: 5px;
                color: #fff;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .form-group button:hover {
                background-color: #4682b4;
            }

            /* Error message */
            .error-message {
                color: #e63946;
                margin-bottom: 15px;
                font-size: 14px;
            }

            /* Footer */
            .login-container .footer {
                margin-top: 20px;
                font-size: 12px;
                color: #999;
            }

            .login-container .footer a {
                color: #87cefa;
                text-decoration: none;
            }

            .login-container .footer a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Caretaker Login</h2>
            <%-- Display error message if login fails --%>
            <c:if test="${not empty param.errorMessage}">
                <div class="error-message">${param.errorMessage}</div>
            </c:if>
            <form action="../caretakerLoginServlet" method="post">
                <div class="form-group">
                    <label for="icNumber">IC Number</label>
                    <input 
                        type="text" 
                        name="icNumber" 
                        id="icNumber"
                        placeholder="XXXXXX-XX-XXXX" 
                        pattern="\d{6}-\d{2}-\d{4}" 
                        title="IC number must follow the format XXXXXX-XX-XXXX" 
                        required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input 
                        type="password" 
                        name="password" 
                        id="password"
                        placeholder="Enter your password" 
                        required>
                </div>
                <div class="form-group">
                    <button type="submit">Login</button>
                </div>
            </form>
        </div>
    </body>
</html>
