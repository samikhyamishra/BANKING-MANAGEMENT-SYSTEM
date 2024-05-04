$(document).ready(function() {
  $('#logoutBtn').click(function(e) {
      e.preventDefault(); // Prevent the default behavior of the link

      var employeeId = "employeeId"; // Specify the employeeId to logout (replace '1' with the actual employeeId)

      var settings = {
          url: "http://localhost:8080/api/employee/logout/" + employeeId,
          method: "DELETE",
          timeout: 0,
      };

      $.ajax(settings)
          .done(function(response) {
              // Handle the response from the server
              console.log(response);
              alert("Logout successful!"); // Display a success message
              // Redirect to the login page or perform any other action
              window.location.href = '/Welcome.html'; // Replace '/Welcome.html' with your actual login page URL
          })
          .fail(function(xhr, status, error) {
              // Display an error message if the request fails
              console.error('Error:', error);
              alert('An error occurred while processing your request. Please try again later.');
          });
  });
});
