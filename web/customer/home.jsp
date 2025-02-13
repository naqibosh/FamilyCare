<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Care Giver - Customer Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <style>
            /* General Styles */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }

            body {
                background-color: #fff;
                color: #000;
                overflow-x: hidden;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            header {
                background-color: #0a1f44;
                padding: 1rem 2rem;
                width: 100%;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 10px rgba(255, 255, 255, 0.1);
            }

            .logo-container {
                display: flex;
                align-items: center;
                text-decoration: none; /* Remove default link styling */
            }

            .logo-container img {
                width: 70px;
                height: 70px;
                margin-right: 10px;
            }

            .logo-container .logo {
                font-size: 1.8rem;
                font-weight: bold;
                color: #ffffff;
            }

            header nav {
                display: flex;
                gap: 1rem;
            }

            header nav a {
                text-decoration: none;
                color: #FFFFFF;
                font-size: 1.1rem;
                padding: 0.5rem 1rem;
                border-radius: 5px;
                transition: background 0.3s ease-in-out;
            }

            header nav a:hover {
                background-color: #1F4068;
            }
            /* Icon Styles */
            

            main {
                padding: 2rem;
                margin-top: 2rem;
                text-align: center;
                max-width: 900px;
            }

            main h1 {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }

            .service-section {
                width: 100%;
                margin: 2rem 0;
            }

            .service-card {
                background: #1b376b;
                padding: 2rem;
                margin: 1rem 0;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(255, 255, 255, 0.1);
                text-align: left;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .service-card:hover {
                transform: scale(1.05);
                box-shadow: 0 8px 20px rgba(255, 255, 255, 0.2);
            }

            .service-card h3 {
                font-size: 1.5rem;
                color: #ffffff;
                margin-bottom: 10px;
            }

            .service-card ul {
                margin-top: 10px;
                padding-left: 20px;
                color: #e0e0e0;
            }

            .booking-btn {
                margin-top: 20px;
                padding: 0.75rem 1.5rem;
                background-color: #007bff;
                border: none;
                border-radius: 5px;
                color: white;
                font-size: 1rem;
                cursor: pointer;
                transition: 0.3s;
            }

            .booking-btn:hover {
                background-color: #0056b3;
            }

            footer {
                background-color: #112a5a;
                color: #fff;
                text-align: center;
                padding: 1rem;
                margin-top: 2rem;
                width: 100%;
            }
        </style>
    </head>
    <body>

        <header>
            <a href="index.jsp" class="logo-container">
                <img src="../image/item/carelogo.png" alt="Care Giver Logo">
                <div class="logo">Care Giver</div>
            </a>
            <nav>
                <a href="home.jsp">Home</a>
                <a href="bookingDetails.jsp">My Booking</a>
                <a href="profile.jsp">View Profile</a>
                <a href="http://localhost:8081/Family_Care/index.html">Logout</a>
            </nav>
        </header>

        <main>
            <h1>Welcome Back, <%= session.getAttribute("customerName") != null ? session.getAttribute("customerName") : "Guest"%></h1>
            <p>We are here to assist you with premium caregiving services tailored to your needs.</p>
            <button class="booking-btn" onclick="window.location.href = 'booking.jsp'">Start Booking</button>

            <section class="service-section">
                <div class="service-card">
                    <h3>Baby Home Care Package</h3>
                    <ul>
                        <li>Eating assistance</li>
                        <li>Diapering</li>
                        <li>Sleeping supervision</li>
                        <li>Bonding activities</li>
                    </ul>
                </div>

                <div class="service-card">
                    <h3>Elder Home Care Package</h3>
                    <ul>
                        <li>Dispensing Medications</li>
                        <li>Feeding/Prepare Meal</li>
                        <li>Light Physiotherapy</li>
                        <li>Companionship</li>
                        <li>Outdoor Exercise/Activity</li>
                        <li>Mind Wellness Activities</li>
                    </ul>
                </div>
            </section>
        </main>

        <footer>
            <p>&copy; 2024 Care Giver. All rights reserved.</p>
        </footer>

    </body>
</html>
