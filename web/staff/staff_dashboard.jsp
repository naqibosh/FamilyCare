<%-- 
    Document   : staff_dashboard
    Created on : Jan 9, 2025, 3:13:29 PM
    Author     : hazik
--%>

<%@ page import="javax.naming.*, javax.sql.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*, java.util.*" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Staff Dashboard</title>
        <!-- Bootstrap CSS -->
        <link href="../css/bootstrap.min.css"" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background-color: #f4f6f9;
            }
            .sidebar {
                background-color: #2575fc;
                color: white;
            }
            .sidebar a {
                color: white;
            }
        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <div class="sidebar p-3 position-fixed h-100 d-flex flex-column" style="width: 220px;">
            <h2>Staff Panel</h2>
            <a href="#">🏠 Dashboard</a>
            <a href="#users">👥 Manage Users</a>
            <a href="#caretakers">👩‍⚕️ Manage Caretakers</a>
            <a href="#bookings">📅 View Bookings</a>
            <a href="#settings">⚙️ Settings</a>
            <a href="staff_logout.jsp" class="mt-auto">🚪 Logout</a>
        </div>

        <!-- Content -->
        <div class="content ml-5">
            <h1 id="dashboard" class="my-4">Welcome to Staff Dashboard</h1>

            <!-- Fetching Data from the Database -->
            <%

                // Database connection parameters
                String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
                String DB_USER = "CareGiver";
                String DB_PASSWORD = "CareGiver";

                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    // Establish the connection using DriverManager
                    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    stmt = conn.createStatement();

                    // Total Users
                    rs = stmt.executeQuery("SELECT COUNT(*) FROM customer");
                    int totalUsers = 0;
                    if (rs.next()) {
                        totalUsers = rs.getInt(1);
                    }

                    // Total Caretakers
                    rs = stmt.executeQuery("SELECT COUNT(*) FROM caretaker");
                    int totalCaretakers = 0;
                    if (rs.next()) {
                        totalCaretakers = rs.getInt(1);
                    }

                    // New Bookings
                    rs = stmt.executeQuery("SELECT COUNT(*) FROM booking WHERE booking_time = CURRENT_DATE");
                    int newBookings = 0;
                    if (rs.next()) {
                        newBookings = rs.getInt(1);
                    }
            %>

            <!-- Dashboard Cards -->
            <div class="container mt-4">
                <div class="row">
                    <div class="col-md-3 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Total Users</h5>
                                <p class="card-text"><%= totalUsers%> registered users</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Total Caretakers</h5>
                                <p class="card-text"><%= totalCaretakers%> active caretakers</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Recent Bookings</h5>
                                <p class="card-text"><%= newBookings%> new bookings today</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">System Overview</h5>
                                <p class="card-text">The system is running smoothly. No issues reported.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
                // Close resources
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close ResultSet, Statement, and Connection
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        %>

        <!-- Bootstrap JS -->
        <script src="../css/bootstrap.bundle.min.js"></script>
    </body>
</html>
