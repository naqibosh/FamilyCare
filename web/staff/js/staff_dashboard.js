/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Prevent user to access staff management
document.addEventListener('DOMContentLoaded', function () {
    // Get URL parameters  
    const urlParams = new URLSearchParams(window.location.search);
    const success = urlParams.get('success');
    const errorCode = urlParams.get('errcode');

    // Check for error code (Action Denied)  
    if (success === 'false' && errorCode === '4') {  
        Swal.fire({  
            title: 'Action Denied!',  
            text: 'You do not have permission to view the staff list.',  
            icon: 'warning',  
            confirmButtonText: 'OK'  
        }).then(() => {  
            clearUrlParams();  
        });  
    } else if (success === 'false' && errorCode === '5') {  
        Swal.fire({  
            title: 'Action Denied!',  
            text: 'You do not have permission to register staff.',  
            icon: 'warning',  
            confirmButtonText: 'OK'  
        }).then(() => {  
            clearUrlParams();  
        });  
    }  


    function updateTime() {
        const timeElement = document.getElementById('current-time');
        const now = new Date();
        const formattedTime = now.toLocaleTimeString(); // Displays time in HH:MM:SS format
        timeElement.textContent = formattedTime;
    }
// Update time every second
    setInterval(updateTime, 1000);
// Initialize time immediately on page load
    updateTime();

    function clearUrlParams() {
        const url = new URL(window.location.href);
        url.searchParams.delete('success'); // Remove success parameter  
        url.searchParams.delete('errcode'); // Remove error code parameter  
        window.history.replaceState({}, document.title, url); // Update browser history without reloading  
    }

});
