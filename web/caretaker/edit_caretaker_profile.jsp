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
                background-color: #f0f8ff;
                padding: 20px;
            }
            form {
                width: 50%;
                margin: auto;
                background-color: #ffffff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            input[type="text"], input[type="submit"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            input[type="submit"] {
                background-color: #4682b4;
                color: white;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background-color: #5a9bd3;
            }
        </style>
    </head>
    <body>
        <h1 style="text-align: center;">Edit Caretaker Profile</h1>
        <form action="../caretakerUpdateProfileServlet" method="post">
            <input type="hidden" name="caretakerID" value="${param.caretakerID}">
            <label for="caretakerName">Name:</label>
            <input type="text" id="caretakerName" name="caretakerName" value="${caretakerName}" required>

            <label for="caretakerPhone">Phone:</label>
            <input type="text" id="caretakerPhone" name="caretakerPhone" value="${caretakerPhone}" required>

            <label for="caretakerICNumber">IC Number:</label>
            <input type="text" id="caretakerICNumber" name="caretakerICNumber" value="${caretakerICNumber}" required>

            <input type="submit" value="Save Changes">
        </form>

    </body>
</html>
