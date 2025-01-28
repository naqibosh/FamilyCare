<%
    HttpSession sessions = request.getSession(false);
    String caretakerName = (sessions != null) ? (String) sessions.getAttribute("caretakerName") : null;
    String caretakerStatus = (sessions != null) ? (String) sessions.getAttribute("caretakerStatus") : null;
    System.out.println(caretakerStatus);

    if (caretakerName == null) {
        response.sendRedirect("login.jsp?errorMessage=Session expired or invalid. Please log in again.");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Review Status</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f4f8;
                color: #333;
            }
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
            .content {
                padding: 50px;
                background-color: #ffffff;
                margin: 20px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .content h2 {
                margin-bottom: 20px;
            }
            .button {
                background-color: #4682b4;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                font-weight: 500;
                cursor: pointer;
            }
            .button:hover {
                background-color: #5a9bd3;
            }
            .back-button {
                background-color: #f0f4f8;
                color: #4682b4;
                padding: 10px 20px;
                border: 1px solid #4682b4;
                border-radius: 5px;
                font-weight: 500;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
            }
            .back-button:hover {
                background-color: #e0e8f1;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <span>FamilyCare Dashboard</span>
        </div>

        <div class="content">
            <h2>Update Your Availability Status</h2>

            <!-- Success/Error Messages -->
            <c:if test="${param.errorMessage != null}">
                <p style="color: red;">${param.errorMessage}</p>
            </c:if>
            <c:if test="${param.successMessage != null}">
                <p style="color: green;">${param.successMessage}</p>
            </c:if>

            <!-- Form to Update Status -->
            <form action="../UpdateStatusServlet" method="POST">
                <label for="availabilityStatus">Current Status: </label>
                <select name="availabilityStatus" id="availabilityStatus">
                    <option value="Available" <c:if test="${sessionScope.caretakerStatus == 'Available'}">selected</c:if>>Available</option>
                    <option value="Not Available" <c:if test="${sessionScope.caretakerStatus == 'Not Available'}">selected</c:if>>Not Available</option>
                </select>

                <br><br>
                <button type="submit" class="button">Update Status</button>
            </form>

            <!-- Back Button -->
            <br><br>
            <a href="caretaker_homepage.jsp" class="back-button">Back to Homepage</a>
        </div>
    </body>
</html>
