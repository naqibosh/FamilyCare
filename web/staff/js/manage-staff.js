
// DataTables Initialization Script
$(document).ready(function () {
    $('#dataTable').DataTable();
});

function openEditModal(staffId, role, supervisorId) {
    // Set the values of the fields in the modal
    document.getElementById('staffId').value = staffId;
    document.getElementById('role').value = role; 
    document.getElementById('supervisor').value = supervisorId; 
}

function confirmDelete(staffId) {
    var inputText = document.getElementById("confirmText-" + staffId).value;
    if (inputText.toLowerCase() === 'confirm') {
        window.location.href = 'staff_manageProcess.jsp?action=deleteStaff&staffId=' + staffId;// Redirect to delete action
    } else {
        alert("You must type 'confirm' to delete.");
    }
}

function enableStaff(staffId) {
    window.location.href = 'staff_manageProcess.jsp?action=enableStaff&staffId=' + staffId; // Redirect to the enable action  

}
function disableStaff(staffId) {
    window.location.href = 'staff_manageProcess.jsp?action=disableStaff&staffId=' + staffId; // Redirect to the enable action  

}

// Get the URL parameters  
const urlParams = new URLSearchParams(window.location.search);
const success = urlParams.get('success');
const errorCode = urlParams.get('errcode');

// Function to clear specific URL parameters while keeping the action parameter  
function clearUrlParams() {
    const url = new URL(window.location.href);
    url.searchParams.delete('success'); // Remove success parameter  
    url.searchParams.delete('errcode'); // Remove error code parameter  
    window.history.replaceState({}, document.title, url); // Update browser history without reloading  
}

// Check if success parameter exists and is set to 'true'  
if (success === 'true') {
    window.onload = function () {
        // Set up the SweetAlert with countdown  
        let countdown = 3; // countdown timer in seconds  

        // Show the SweetAlert with countdown  
        Swal.fire({
            title: 'Success!',
            text: 'Staff updated successfully!',
            icon: 'success',
            showConfirmButton: false, // Hide the confirm button  
            timer: countdown * 1000, // Set timer for 3 seconds  
            timerProgressBar: true, // Show progress bar  
            willClose: () => {
                console.log('The alert was closed after 3 seconds');
            }
        });

        // Optional: If you want to show the countdown dynamically (on the SweetAlert popup)  
        let interval = setInterval(function () {
            if (countdown > 0) {
                Swal.getContent().innerHTML = `<p>Staff updated successfully! Closing in ${countdown} seconds...</p>`;
                countdown--;
            } else {
                clearInterval(interval);  // Clear the interval when the countdown ends  
                clearUrlParams(); // Clear the URL parameters (success and errcode) after closing the alert  
            }
        }, 1000);
    };
} else if (success === 'false') {
    let errorMessage = '';

    // Determine the error message based on the error code  
    switch (errorCode) {
        case '1':
            errorMessage = 'Failed to update staff. Please try again.';
            break;
        case '2':
            errorMessage = 'Supervisor not found. Please check the supervisor ID.';
            break;
        case '3':
            errorMessage = 'Invalid staff ID or supervisor ID. Please check your input.';
            break;
        default:
            errorMessage = 'An unknown error occurred. Please try again.';
            break;
    }

    window.onload = function () {
        // Show the SweetAlert with error message  
        Swal.fire({
            title: 'Error!',
            text: errorMessage,
            icon: 'error',
            confirmButtonText: 'OK' // Show confirm button for error  
        }).then(() => {
            clearUrlParams(); // Clear the URL parameters (success and errcode) after user closes the alert  
        });
    };
}
