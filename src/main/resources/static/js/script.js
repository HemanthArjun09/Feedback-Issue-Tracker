// Wait for the document to be fully loaded before running the script
document.addEventListener('DOMContentLoaded', function() {

    // Find the button by its ID
    const learnMoreBtn = document.getElementById('learnMoreBtn');

    // Check if the button exists
    if (learnMoreBtn) {
        // Add a click event listener
        learnMoreBtn.addEventListener('click', function() {
            // Display an alert when the button is clicked
            alert('Thanks for your interest! More features are coming soon.');
        });
    }

    console.log("Custom script loaded successfully!");
});