/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
document.addEventListener('DOMContentLoaded', (event) => {

    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('error');
    const success = urlParams.get('success');

    // Check if there is an error code  
    if (code === '1') {
        displayError('Please enter both email and password.');
    } else if (code === '2') {
        displayError('Database connection failed. Please try again later.');
    } else if (code === '3') {
        displayError('Invalid email or password. Please try again.');
    } 


    if (success === 'true') {
        let countdown = 3;
        Swal.fire({
            title: 'Success!',
            text: 'Logout successfully!',
            icon: 'success',
            showConfirmButton: false,
            timer: countdown * 1000,
            timerProgressBar: true,
            willClose: () => {
                console.log('The alert was closed after 3 seconds');
            }
        });

        let interval = setInterval(() => {
            if (countdown > 0) {
                Swal.getContent().innerHTML = `<p>Closing in ${countdown} seconds...</p>`;
                countdown--;
            } else {
                clearInterval(interval);
                clearUrlParams();
            }
        }, 1000);
    }

    // Function to display error messages  
    function displayError(message) {
        const errorMessage = document.createElement('p');
        errorMessage.textContent = message;
        errorMessage.style.color = 'red';
        errorMessage.classList.add('error-message');

        // Insert the error message before the form  
        const form = document.querySelector('form');
        form.parentNode.insertBefore(errorMessage, form);
    }

    function clearUrlParams() {
        urlParams.searchParams.delete('success'); 
        urlParams.searchParams.delete('error'); 
        window.history.replaceState({}, document.title, urlParams); // Update browser history without reloading  
    }
});