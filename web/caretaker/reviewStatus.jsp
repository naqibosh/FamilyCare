<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    HttpSession sessions = request.getSession(false);
    String caretakerName = (sessions != null) ? (String) sessions.getAttribute("caretakerName") : null;
    String caretakerStatus = (sessions != null) ? (String) sessions.getAttribute("caretakerStatus") : "Not Available";
    
    if (caretakerName == null) {
        response.sendRedirect("login.jsp?errorMessage=Session expired or invalid. Please log in again.");
        return;
    }
%>

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
            background-color: #eef2f7;
            color: #333;
        }
        .navbar {
            background-color: #005a87;
            color: white;
            padding: 15px 20px;
            text-align: center;
            font-size: 20px;
            font-weight: bold;
        }
        .container {
            width: 50%;
            margin: 40px auto;
            background: white;
            padding: 30px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            text-align: center;
        }
        .status-box {
            font-size: 20px;
            font-weight: bold;
            margin: 20px auto;
            padding: 15px;
            width: 60%;
            border-radius: 8px;
            text-transform: uppercase;
        }
        .status-available {
            background-color: #28a745;
            color: white;
        }
        .status-not-available {
            background-color: #dc3545;
            color: white;
        }
        .form-group {
            margin-top: 20px;
        }
        select {
            padding: 10px;
            font-size: 16px;
            width: 50%;
            border-radius: 5px;
        }
        .button {
            background-color: #005a87;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 20px;
        }
        .button:hover {
            background-color: #0073a9;
        }
        .back-button {
            display: inline-block;
            margin-top: 15px;
            text-decoration: none;
            color: #005a87;
            font-size: 16px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="navbar">FamilyCare Dashboard</div>
    <div class="container">
        <h2>Welcome, <%= caretakerName %></h2>
        <h3>Your Current Availability Status</h3>
        <div class="status-box <%= caretakerStatus.equals("Available") ? "status-available" : "status-not-available" %>">
            <%= caretakerStatus %>
        </div>
        
        <h3>Update Your Availability Status</h3>
        <c:if test="${param.errorMessage != null}">
            <p style="color: red;">${param.errorMessage}</p>
        </c:if>
        <c:if test="${param.successMessage != null}">
            <p style="color: green;">${param.successMessage}</p>
        </c:if>

        <form action="../UpdateStatusServlet" method="POST">
            <div class="form-group">
                <label for="availabilityStatus">Choose new status: </label>
                <select name="availabilityStatus" id="availabilityStatus">
                    <option value="Available" <c:if test="${sessionScope.caretakerStatus == 'Available'}">selected</c:if>>Available</option>
                    <option value="Not Available" <c:if test="${sessionScope.caretakerStatus == 'Not Available'}">selected</c:if>>Not Available</option>
                </select>
            </div>
            <button type="submit" class="button">Update Status</button>
        </form>
        <br>
        <a href="caretaker_homepage.jsp" class="back-button">← Back to Homepage</a>
    </div>
</body>
</html>
