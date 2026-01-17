<%-- 
    Document   : edit_caretaker_profile
    Created on : Jan 20, 2025
    Author     : Naqib
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Caretaker Profile</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f4f4f4;
                padding: 20px;
                text-align: center;
            }
            .container {
                max-width: 500px;
                margin: auto;
                background-color: #ffffff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
            h1 {
                color: #333;
                margin-bottom: 20px;
            }
            label {
                display: block;
                font-weight: bold;
                margin: 10px 0 5px;
                text-align: left;
            }
            input[type="text"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
            }
            input[type="submit"], .back-button {
                width: 100%;
                padding: 12px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
            }
            input[type="submit"] {
                background-color: #007bff;
                color: white;
            }
            input[type="submit"]:hover {
                background-color: #0056b3;
            }
            .back-button {
                background-color: #6c757d;
                color: white;
                margin-top: 10px;
                display: inline-block;
                text-align: center;
                text-decoration: none;
            }
            .back-button:hover {
                background-color: #5a6268;
            }
            input[disabled] {
                background-color: #e9ecef;
                cursor: not-allowed;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Edit Caretaker Profile</h1>
            <form action="../caretakerUpdateProfileServlet" method="post">
                <input type="hidden" name="caretakerID" value="${param.caretakerID}">
                
                <label for="caretakerName">Name:</label>
                <input type="text" id="caretakerName" name="caretakerName" value="${caretakerName}" required>

                <label for="caretakerPhone">Phone:</label>
                <input type="text" id="caretakerPhone" name="caretakerPhone" value="${caretakerPhone}" required>

                <label for="caretakerICNumber">IC Number:</label>
                <input type="text" id="caretakerICNumber" name="caretakerICNumber" value="${caretakerICNumber}" disabled>

                <input type="submit" value="Save Changes">
            </form>
            <a href="../caretakerProfileServlet" class="back-button">Back</a>
        </div>
    </body>
</html>
