// Wait for the document to be fully loaded before running the script
document.addEventListener('DOMContentLoaded', function() {

 const element = document.getElementById('animated-logo');
 const logo = document.getElementById('logo-letter');
 if (element) {
     logo.addEventListener('click', function() {
         // Redirect the browser to the /dashboard URL
         window.location.href = '/dashboard.html';});
     logo.style.cursor = 'pointer';
 }
});