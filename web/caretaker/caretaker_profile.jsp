<%-- 
    Document   : caretaker_profile
    Created on : Jan 20, 2025, 2:33:35 AM
    Author     : Naqib
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Caretaker Profile</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Arial', sans-serif;
                background-color: #f5f7fa;
                color: #333;
                padding: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
            }

            .container {
                width: 100%;
                max-width: 800px;
                background: white;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                overflow: hidden;
            }

            .header {
                background-color: #4682b4;
                color: white;
                padding: 20px;
                text-align: center;
                font-size: 24px;
                font-weight: bold;
            }

            .profile-table {
                width: 100%;
                border-collapse: collapse;
            }

            .profile-table th, .profile-table td {
                padding: 15px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            .profile-table th {
                background-color: #f0f8ff;
                text-transform: uppercase;
                font-size: 14px;
                color: #555;
            }

            .profile-table td {
                font-size: 16px;
                background-color: #fff;
            }

            .buttons {
                display: flex;
                justify-content: center;
                gap: 15px;
                padding: 20px;
                background-color: #f9f9f9;
            }

            .buttons button {
                padding: 12px 20px;
                font-size: 16px;
                font-weight: bold;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                transition: transform 0.2s ease, background-color 0.3s;
            }

            .buttons .back-btn {
                background-color: #4682b4;
                color: white;
            }

            .buttons .edit-btn {
                background-color: #32cd32;
                color: white;
            }

            .buttons button:hover {
                transform: translateY(-2px);
                opacity: 0.95;
            }

            .buttons button:active {
                transform: translateY(0);
            }

            @media (max-width: 768px) {
                .profile-table th, .profile-table td {
                    font-size: 14px;
                }

                .buttons {
                    flex-direction: column;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">Caretaker Profile</div>

            <table class="profile-table">
                <tr>
                    <th>ID</th>
                    <td>${caretakerID}</td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td>${caretakerName}</td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>${caretakerPhone}</td>
                </tr>
                <tr>
                    <th>IC Number</th>
                    <td>${caretakerICNumber}</td>
                </tr>
            </table>

            <div class="buttons">
                <button class="back-btn" onclick="location.href = 'caretaker/caretaker_homepage.jsp'">Back</button>
                <button class="edit-btn" onclick="location.href = 'caretaker/edit_caretaker_profile.jsp?caretakerID=${caretakerID}'">Edit</button>
            </div>
        </div>
    </body>
</html>
