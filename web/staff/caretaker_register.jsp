<%-- 
    Document   : caretaker_register
    Created on : Jan 31, 2025, 11:55:50 PM
    Author     : hazik
--%>

<%@ page import="javax.naming.*, javax.sql.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*, java.util.*" %>
<%@ page import="utils.SessionUtils" %>

<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Family Care</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet">

    </head>
    <%
        Integer staffId = SessionUtils.getUserIdFromSession(request);
        if (staffId == null) {
            response.sendRedirect("login.html?error=invalidSession");
        return;
    }
    %>

    <body class="bg-gradient-primary">
        <div class="container mt-5">
            <div class="card shadow-lg p-4">
                <h2 class="text-center mb-4">Caretaker Registration</h2>
                <form action="../CaretakerManageServlet?action=insertCaretaker" method="post" enctype="multipart/form-data" autocomplete="off">
                    <input type="hidden" name="staff_id" value="<%= staffId %>">
                    <div class="mb-3">
                        <label for="caretaker_name" class="form-label">Caretaker Name</label>
                        <input type="text" class="form-control" id="caretaker_name" name="caretaker_name" required>
                    </div>
                    <div class="mb-3">
                        <label for="caretaker_phone" class="form-label">Caretaker Phone (+60-XXXXXXXXX)</label>
                        <input type="text" class="form-control" id="caretaker_phone" name="caretaker_phone" pattern="\+60-\d{8,11}" title="Format: +60-XXXXXXXXX" required>
                    </div>
                    <div class="mb-3">
                        <label for="caretaker_ic_number" class="form-label">IC Number (XXXXXX-XX-XXXX)</label>
                        <input type="text" class="form-control" id="caretaker_ic_number" name="caretaker_ic_number" pattern="\d{6}-\d{2}-\d{4}" title="Format: XXXXXX-XX-XXXX" required>
                    </div>
                    <div class="mb-3">
                        <label for="caretaker_type" class="form-label">Caretaker Type</label>
                        <select class="form-control" id="caretaker_type" name="caretaker_type" required onchange="toggleCaretakerFields()">
                            <option value="">Select Type</option>
                            <option value="babysitter">Babysitter</option>
                            <option value="eldercaretaker">Elder Caretaker</option>
                        </select>
                    </div>
                    <div id="babysitter_fields" style="display: none;">
                        <div class="mb-3">
                            <label for="babysitter_experience_years" class="form-label">Experience (Years)</label>
                            <input type="number" class="form-control" id="babysitter_experience_years" name="babysitter_experience_years">
                        </div>
                        <div class="mb-3">
                            <label for="babysitter_hourly_rate" class="form-label">Hourly Rate</label>
                            <input type="number" step="0.01" class="form-control" id="babysitter_hourly_rate" name="babysitter_hourly_rate">
                        </div>
                    </div>
                    <div id="eldercaretaker_fields" style="display: none;">
                        <div class="mb-3">
                            <label for="eldercare_experience_years" class="form-label">Experience (Years)</label>
                            <input type="number" class="form-control" id="eldercare_experience_years" name="eldercare_experience_years">
                        </div>
                        <div class="mb-3">
                            <label for="eldercare_certification" class="form-label">Certification (JPG, PNG, PDF max 2MB)</label>
                            <input type="file" class="form-control" id="eldercare_certification" name="eldercare_certification" accept=".pdf,.jpg,.png" onchange="validateFileSize(this)">
                        </div>
                        <div class="mb-3">
                            <label for="eldercare_hourly_rate" class="form-label">Hourly Rate</label>
                            <input type="number" step="0.01" class="form-control" id="eldercare_hourly_rate" name="eldercare_hourly_rate">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="profile_description" class="form-label">Profile Description</label>
                        <textarea class="form-control" id="profile_description" name="profile_description" rows="3"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="caretaker_password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="caretaker_password" name="caretaker_password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}" title="Must be at least 8 characters, including upper/lowercase, number, and a special symbol" required>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-success">Register</button>
                    </div>
                </form>
            </div>
        </div>
        <script>
            function toggleCaretakerFields() {
                var type = document.getElementById("caretaker_type").value;
                document.getElementById("babysitter_fields").style.display = (type === "babysitter") ? "block" : "none";
                document.getElementById("eldercaretaker_fields").style.display = (type === "eldercaretaker") ? "block" : "none";
            }
            function validateFileSize(input) {
                if (input.files[0].size > 2097152) { // 2MB limit
                    alert("File size must be less than 2MB.");
                    input.value = "";
                }
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

