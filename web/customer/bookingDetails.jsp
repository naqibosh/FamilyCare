<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Details</title>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 50%;
            margin: 5% auto;
            padding: 2rem;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #2575fc;
        }

        .booking-info {
            margin-top: 2rem;
        }

        .booking-info p {
            font-size: 1.2rem;
            margin: 0.8rem 0;
        }

        .booking-info .label {
            font-weight: bold;
            color: #333;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Your Booking Details</h1>

        <div class="booking-info">
            <p><span class="label">Booking Type:</span> </p>
            <p><span class="label">Booking Start Time:</span> </p>
            <p><span class="label">Booking End Time:</span> 
                <c:out value="" />
            </p>
            <p><span class="label">Booking Price:</span> RM </p>
        </div>
    </div>

</body>
</html>
