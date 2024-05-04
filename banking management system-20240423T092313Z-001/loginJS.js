$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault(); // Prevent form submission

        // Get input values
        var userName = $('#userName').val();
        var password = $('#password').val();

        // Construct request body
        var requestBody = {
            userName: userName,
            password: password
        };

        // Make POST request to login API
        $.ajax({
            url: 'http://localhost:8080/api/customers/Validation',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(requestBody),
            success: function(data) {
                // Handle successful login
                console.log(data); // You can handle the response data here
                // For example, redirect to dashboard page
                window.location.href = 'home.html';
            },
            error: function(xhr, status, error) {
                // Handle login error
                console.error('Login error:', error);
                $('#result').text('Login failed. Please try again.');
            }
        });
    });
});
