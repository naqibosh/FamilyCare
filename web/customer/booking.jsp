<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if the session exists and retrieve customerId
    Integer customerId = (Integer) session.getAttribute("customerId");

    if (customerId == null) {
        // Redirect to the login page if not logged in
        response.sendRedirect("login.jsp?error=Please log in to access the booking page.");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Book a Caretaker</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: 'Poppins', sans-serif;
        line-height: 1.6;
        background-color: #FFFFFF;
        color: white;
    }

    .sidebar {
        position: fixed;
        top: 0;
        left: 0;
        width: 200px;
        height: 100%;
        background: #1F4068;
        color: #fff;
        padding: 1rem;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
        box-shadow: 2px 0 10px rgba(0, 0, 0, 0.2);
    }

    .sidebar img {
        width: 150px;
        height: 150px;
        object-fit: contain;
    }

    .sidebar h2 {
        text-align: center;
        font-size: 1.2rem;
        margin-top: 1rem;
    }

    .sidebar a {
        color: #fff;
        text-decoration: none;
        font-size: 1rem;
        display: flex;
        align-items: center;
        gap: 0.5rem;
        padding: 0.5rem 1rem;
        border-radius: 5px;
        transition: background 0.3s;
    }

    .sidebar a:hover {
        background: #6a11cb;
    }

            .content-wrapper {
                margin-left: 220px;
                padding: 1rem;
            }

            .hero {
        background: #1F4068;
        color: #fff;
        text-align: center;
        padding: 2rem 1rem;
    }

    .hero h1, .hero p {
        color: white;
    }

    .hero button {
        background: linear-gradient(to right, #3498db, #6a11cb);
        color: white;
        border: none;
        padding: 12px 24px;
        font-size: 1rem;
        border-radius: 25px;
        cursor: pointer;
        transition: 0.3s;
        margin-top: 1rem;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }

    .hero button:hover {
        background: linear-gradient(to right, #6a11cb, #3498db);
        transform: scale(1.05);
    }
            .packages {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 2rem;
                padding: 2rem;
                background-color: #1F4068;
            }

            .package {
                background: #162447;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                padding: 2rem;
                width: 300px;
                text-align: center;
                color: white;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .package ul {
                list-style: none; /* Removes bullet points */
                padding: 0;
                margin: 0;
            }

            .package ul li {
                margin-bottom: 10px; /* Adjust space between items */
                line-height: 1.8; /* Increases spacing between lines */
            }

            .package:hover {
                transform: scale(1.05);
                box-shadow: 0 6px 20px rgba(255, 255, 255, 0.3);
            }



            .booking-container {
                background-color: #1F4068;
                padding: 2rem;
                margin: 2rem auto;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                max-width: 600px;
                text-align: center;
                color: white;
            }

            .booking-container input, .booking-container select {
                width: 100%;
                padding: 0.75rem;
                margin: 0.5rem 0;
                border-radius: 5px;
                border: 1px solid #ddd;
                font-size: 1rem;
                background: #FFFFFF;
                color: black;
            }

            .booking-container button {
                width: 100%;
                padding: 0.75rem;
                margin-top: 1rem;
                border: none;
                border-radius: 5px;
                background-color: #3498db;
                color: white;
                font-size: 1rem;
                cursor: pointer;
                transition: 0.3s;
            }

            .booking-container button:hover {
                background-color: #2980b9;
            }

        </style>
        <script>
            function fetchCaretakers() {
                var type = document.getElementById("type").value; // Get the selected caretaker type (baby or elder)
                var caretakerDropdown = document.getElementById("caretaker"); // Get the caretaker dropdown element
                caretakerDropdown.innerHTML = "<option value='' selected>Select a Caretaker</option>"; // Reset the dropdown

                // If no type is selected, return immediately
                if (!type) {
                    caretakerDropdown.disabled = true;
                    return;
                }

                caretakerDropdown.disabled = true; // Disable dropdown while fetching

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        // On successful response, populate the dropdown with caretaker options
                        caretakerDropdown.innerHTML = "<option value='' selected>Select a Caretaker</option>" + this.responseText;
                        caretakerDropdown.disabled = false; // Enable dropdown after populating
                    }
                };
                // Send a GET request to the CaretakerServlet with the selected type (baby or elder)
                xhttp.open("GET", "../CaretakerServlet?type=" + type, true);
                xhttp.send();
            }
        </script>

    </head>
    <body>
        <!-- Sidebar Navbar -->
    <div class="sidebar">
        <img src="../image/item/carelogo.png" alt="Care Giver Logo">
        <h2>Menu</h2>
        <a href="home.jsp"><span>🏠</span> Home</a>
        <a href="#packages"><span>📦</span> Packages</a>
        <a href="#booking"><span>📅</span> Booking</a>
        <a href="bookingDetails.jsp"><span>📖</span> My Booking</a>
    </div>

        <!-- Content Wrapper -->
        <div class="content-wrapper">
            <!-- Hero Section -->
            <div class="hero" id="home">
                <h1>Find the Perfect Caretaker</h1><br>
                <p>Choose the best caretaker for your needs. Scroll down to explore packages and start booking!</p>
                <button onclick="document.getElementById('booking').scrollIntoView({behavior: 'smooth'})">
                    Let's Start Booking
                </button>
            </div>

            <!-- Packages Section -->
            <section class="packages" id="packages">
                <div class="package">
                    <h2>Baby Home Care Package</h2><br>
                    <ul>
                        <li>Eating assistance</li>
                        <li>Diapering</li>
                        <li>Sleeping supervision</li>
                        <li>Bonding activities</li>
                    </ul>
<!--                    <p class="price">RM 10 per hour</p>-->
                </div>
                <div class="package">
                    <h2>Elder Home Care Package</h2><br>
                    <ul>
                        <li>Dispensing Medications</li>
                        <li>Feeding/Prepare Meal</li>
                        <li>Light Physiotherapy</li>
                        <li>Companionship</li>
                        <li>Outdoor Exercise/Activity</li>
                        <li>Mind Wellness Activities</li>
                    </ul>
<!--                    <p class="price">RM 12 per hour</p>-->
                </div>
            </section>

            <!-- Booking Section -->
            <div class="booking-container" id="booking">  
                <h2>Book a Caretaker</h2> <br> 
                <form action="../BookingServlet" method="post">  
                    <input type="hidden" name="cust_id" value="<%= customerId%>"/> <!-- Hidden field for customerId -->  

                    <label for="type">Booking Type</label>  
                    <select id="type" name="type" required onchange="fetchCaretakers()">  
                        <option value="" disabled selected>Select Caretaker Type</option>  
                        <option value="Babycaretaker">Baby Home Care Package</option>  
                        <option value="Eldercaretaker">Elder Home Care Package</option>  
                    </select>  

                    <label for="caretaker">Caretaker Name</label>  
                    <select id="caretaker" name="caretaker" required>  
                        <option value="" selected>Select a Caretaker</option>  
                    </select>  

                    <label for="time">Booking Time</label>  
                    <!-- Combine Date and Time Input -->  
                    <input type="datetime-local" id="time" name="time" required   
                           pattern="\d{4}-\d{2}-\d{2}T\d{2}:\d{2}"   
                           title="Format: YYYY-MM-DDTHH:MM">  
                    <!-- Example format: 2025-01-17T05:23 -->  

                    <label for="duration">Duration (hours)</label>  
                    <input type="number" id="duration" name="duration" min="1" placeholder="Enter duration" required><br>  

                    <button type="submit">Book Now</button>  
                </form>  
            </div> 
    </body>
