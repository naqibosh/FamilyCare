<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Caretaker Schedule</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@3.2.0/dist/fullcalendar.min.css" />
    <style>
        /* Global Styles */
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f4f8;
            color: #333;
        }

        /* Navigation Bar */
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
        .navbar a:hover {
            text-decoration: underline;
        }

        /* Schedule Container */
        .schedule-container {
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
            border-radius: 8px;
        }

        h2 {
            font-size: 28px;
            color: #333;
            margin-bottom: 20px;
        }

        /* Footer */
        .footer {
            text-align: center;
            padding: 15px 20px;
            background-color: #f8f9fa;
            border-top: 1px solid #ddd;
            font-size: 14px;
            color: #777;
        }
        .footer a {
            color: #4682b4;
            text-decoration: none;
            font-weight: 500;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <div class="navbar">
        <span>FamilyCare Dashboard</span>
        <div>
            <a href="../caretakerProfileServlet">Profile</a>
            <a href="schedule.jsp">Schedule</a>
            <a href="bookings.jsp">Bookings</a>
            <a href="../caretakerLogoutServlet">Logout</a>
        </div>
    </div>

    <!-- Schedule Section -->
    <div class="schedule-container">
        <h2>Your Schedule</h2>

        <!-- Calendar (using FullCalendar.js) -->
        <div id="calendar"></div>

    </div>

    <!-- Footer -->
    <div class="footer">
        <p>&copy; 2025 FamilyCare. All Rights Reserved. | <a href="contact.jsp">Contact Us</a></p>
    </div>

    <!-- FullCalendar JS -->
    <script src="https://cdn.jsdelivr.net/npm/moment@2.29.1/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@3.2.0/dist/fullcalendar.min.js"></script>

    <script>
        $(document).ready(function() {
            $('#calendar').fullCalendar({
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay'
                },
                events: function(start, end, timezone, callback) {
                    // Example: Fetch events from your server (could be Ajax)
                    var events = [
                        {
                            title: 'Job #1',
                            start: '2025-01-15',
                            description: 'Job for Client A'
                        },
                        {
                            title: 'Job #2',
                            start: '2025-01-20',
                            description: 'Job for Client B'
                        },
                        {
                            title: 'Job #3',
                            start: '2025-01-25',
                            description: 'Job for Client C'
                        }
                    ];
                    callback(events);
                },
                eventClick: function(event) {
                    alert('Job Details: ' + event.title + '\n' + event.description);
                }
            });
        });
    </script>
</body>
</html>
