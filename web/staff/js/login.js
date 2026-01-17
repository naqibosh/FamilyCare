document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const errorCode = urlParams.get('error');

    if (errorCode) {
        handleError(errorCode);
    }

    /**
     * Function to handle error popups
     */
    function handleError(code) {
        let errorMessage = '';

        switch (code) {
            case '1':
                errorMessage = 'Please enter both email and password.';
                break;  
            case '2':
                errorMessage = 'Database connection failed. Please try again later.';
                break;
            case '3':
                errorMessage = 'Invalid email or password. Please try again.';
                break;
            case 'invalidSession':
                errorMessage = 'Your session has expired. Please log in again.';
                break;
            default:
                errorMessage = 'An unknown error occurred. Please try again.';
        }

        Swal.fire({
            title: 'Login Error',
            text: errorMessage,
            icon: 'error',
            confirmButtonText: 'OK'
        }).then(() => {
            clearUrlParams();
        });
    }

    /**
     * Function to remove error parameters from the URL
     */
    function clearUrlParams() {
        const newUrl = window.location.origin + window.location.pathname;
        window.history.replaceState({}, document.title, newUrl);
    }
});
