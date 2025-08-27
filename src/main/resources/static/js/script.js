// Wait for the document to be fully loaded before running the script
document.addEventListener('DOMContentLoaded', function() {

    // Find the button by its ID
    const logos  = document.querySelectorAll('.animated-logo');

    // Check if the button exists
    logos.forEach(logo => {
        logo.classList.add('compressed');
    })

});