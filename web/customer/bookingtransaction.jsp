<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Retrieve booking details from request attributes
    String bookingType = (String) request.getAttribute("bookingType");
    String bookingStartDate = (String) request.getAttribute("bookingStartDate");
    String bookingStartTime = (String) request.getAttribute("bookingStartTime");
    String bookingEndTime = (String) request.getAttribute("bookingEndTime");
    String bookingFee = (String) request.getAttribute("bookingFee");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Booking Confirmation</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                padding: 50px;
            }

            .confirmation-container {
                max-width: 500px;
                margin: auto;
                padding: 20px;
                background: #f4f4f4;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            h2 {
                color: #333;
            }

            p {
                font-size: 18px;
                color: #555;
            }

            .payment-container {
                margin-top: 20px;
                text-align: left;
            }

            select {
                width: 100%;
                padding: 10px;
                margin-top: 10px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .qr-code {
                display: none; /* Initially hidden */
                margin-top: 15px;
                text-align: center;
            }

            .qr-code img {
                width: 200px; /* Adjust size as needed */
                height: auto;
            }

            .confirm-btn {
                width: 100%;
                padding: 10px;
                font-size: 18px;
                color: white;
                background-color: #28a745;
                border: none;
                border-radius: 5px;
                margin-top: 20px;
                cursor: pointer;
            }

            .confirm-btn:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <script>
            window.onload = function () {
                alert("Booking Successfully");
            };
        </script>

        <div class="confirmation-container">
            <h2>Confirm Your Booking</h2><br>
            <p><strong>Booking Type :</strong> <%= bookingType%></p>
            <p><strong>Booking Start Date :</strong> <%= bookingStartDate%></p>
            <p><strong>Booking Start Time :</strong> <%= bookingStartTime%></p>
            <p><strong>Booking End Time :</strong> <%= bookingEndTime%></p>
            <p><strong>Booking Fee :</strong> <%= bookingFee%></p><br>

            <!-- Payment Method Selection -->
            <div class="payment-container">
                <p><strong>Choose Payment Method:</strong></p>
                <select name="paymentMethod" id="paymentMethod" onchange="showQRCode()">
                    <option value="">-- Select Payment Method --</option>
                    <option value="QR Code">QR Code</option>

                </select>
            </div>

            <!-- QR Code Image (Hidden by default) -->
            <div class="qr-code" id="qrCode">
                <p><strong>Scan this QR Code to Pay:</strong></p>
                <img src="image/item/duitnow.png" alt="DuitNow QR Code">

                <!-- File Upload Section for Payment Proof -->
                <div class="file-upload">
                    <label for="paymentProof"><strong>Upload Payment Proof (Image Only):</strong></label>
                    <input type="file" id="paymentProof" name="paymentProof" accept="image/png, image/jpeg, image/jpg" required>
                    <p id="fileName"></p>
                </div>
            </div>

            <!-- Confirm Booking Button -->
            <button class="confirm-btn" onclick="confirmBooking()">Confirm Booking</button>
        </div>

        <script>
            // Show selected file name
            document.getElementById("paymentProof").addEventListener("change", function () {
                var fileInput = this;
                var fileNameDisplay = document.getElementById("fileName");

                if (fileInput.files.length > 0) {
                    fileNameDisplay.textContent = "Selected File: " + fileInput.files[0].name;
                } else {
                    fileNameDisplay.textContent = "";
                }
            });

            function showQRCode() {
                var paymentMethod = document.getElementById("paymentMethod").value;
                var qrCodeDiv = document.getElementById("qrCode");

                if (paymentMethod === "QR Code") {
                    qrCodeDiv.style.display = "block";
                } else {
                    qrCodeDiv.style.display = "none";
                }
            }

            function confirmBooking() {
                var paymentMethod = document.getElementById("paymentMethod").value;
                var bookingFee = "<%= bookingFee%>"; // Retrieve booking fee from JSP
                var fileInput = document.getElementById("paymentProof");

                if (paymentMethod === "") {
                    alert("Please select a payment method before confirming your booking.");
                    return;
                }

                // Check if payment proof is uploaded (only required for QR Code payment)
                if (paymentMethod === "QR Code" && fileInput.files.length === 0) {
                    alert("Please upload your payment proof before confirming your booking.");
                    return;
                }

                // Capture current timestamp
                var currentTimestamp = new Date().toISOString(); // YYYY-MM-DDTHH:MM:SSZ format

                // Create form and send data to PaymentServlet
                var form = document.createElement("form");
                form.method = "POST";
                form.action = "PaymentServlet";
                form.enctype = "multipart/form-data"; // Allow file upload

                var paymentMethodInput = document.createElement("input");
                paymentMethodInput.type = "hidden";
                paymentMethodInput.name = "paymentMethod";
                paymentMethodInput.value = paymentMethod;
                form.appendChild(paymentMethodInput);

                var paymentDateInput = document.createElement("input");
                paymentDateInput.type = "hidden";
                paymentDateInput.name = "paymentDate";
                paymentDateInput.value = currentTimestamp;
                form.appendChild(paymentDateInput);

                var paymentAmountInput = document.createElement("input");
                paymentAmountInput.type = "hidden";
                paymentAmountInput.name = "paymentAmount";
                paymentAmountInput.value = bookingFee.replace("RM ", ""); // Remove "RM" and send only the numeric value
                form.appendChild(paymentAmountInput);

                // Append file input
                if (fileInput.files.length > 0) {
                    var fileClone = fileInput.cloneNode(true);
                    form.appendChild(fileClone);
                }

                document.body.appendChild(form);
                form.submit();
            }
        </script>

    </body>
</html>
