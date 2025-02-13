<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Success</title>
    <style>
        body {
        font-family: Arial, sans-serif;
        text-align: center;
        background-color: #f4f4f4;
        padding: 50px;
    }

    .success-container {
        max-width: 500px;
        margin: auto;
        padding: 30px;
        background: white;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        animation: fadeIn 1s ease-in-out;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: scale(0.9); }
        to { opacity: 1; transform: scale(1); }
    }

    .success-logo {
        width: 80px;
        height: 80px;
        margin: 0 auto 20px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;
        animation: popIn 0.8s ease-in-out;
    }

    .success-logo img {
        width: 60px; /* Fixed size */
        height: 60px;
        object-fit: contain; /* Ensures image fits without cropping */
        opacity: 0;
        transform: scale(0.5);
        animation: drawCheck 0.5s ease-in-out 0.5s forwards;
    }

    @keyframes popIn {
        from { transform: scale(0); opacity: 0; }
        to { transform: scale(1); opacity: 1; } /* Fixed syntax error */
    }

    @keyframes drawCheck {
        from { opacity: 0; transform: scale(0.5); }
        to { opacity: 1; transform: scale(1); }
    }

    h1 {
        color: #28a745;
        font-size: 24px;
        margin-bottom: 20px;
        animation: bounce 1s ease-in-out infinite alternate;
    }

    @keyframes bounce {
        from { transform: translateY(0); }
        to { transform: translateY(-5px); }
    }

    .home-btn {
        display: inline-block;
        padding: 10px 20px;
        font-size: 18px;
        color: white;
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        text-decoration: none;
        margin-top: 20px;
        transition: background 0.3s;
    }

    .home-btn:hover {
        background-color: #0056b3;
    }
    </style>
</head>
<body>
    <div class="success-container">
        <!-- Booking success logo -->
        <div class="success-logo">
            <img src="../image/item/checkmark.png" alt="Check Mark">
        </div>
        
        <h1> Booking Confirmed! </h1>
        <p>Thank you for booking with us. Your reservation has been successfully processed.</p>
        <a href="home.jsp" class="home-btn">Go to Home</a>
    </div>
</body>
</html>
