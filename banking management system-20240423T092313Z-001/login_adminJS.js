$(document).ready(function() {
    $('#loginForm').submit(function(e) {
        e.preventDefault(); // Prevent form submission

        var bankId = $("#bankId").val();
        var password = $("#password").val();

        // Check if username and password are not empty
        if (bankId.trim() === "" || password.trim() === "") {
            alert("Please enter both bankId and password.");
            return;
        }

        var loginData = {
            bankId: parseInt(bankId, 10),
            password: password
        };

        console.log("bankId:", bankId);
        console.log("Password:", password);


        var settings = {
            "url": "http://localhost:8080/api/banks/login",
            "method": "POST",
            "timeout": 0,
            "headers": {
              "Content-Type": "application/json"
            },
            "data": JSON.stringify(loginData),
        };

    $.ajax(settings)
       .done(function(response, textStatus, xhr) {
        console.log(response);
        // Check if login was successful
        if (xhr.status === 200) {
            // Successful loginconsole.log('Redirecting to AdminHome.html...');

            alert("Login successful!");
            console.log("Redirecting to admin dashboard...");
            window.location.href = 'AdminHome.html';
        } else {
            // Handle other status codes (e.g., 401 for unauthorized)
            alert("Login failed: " + response); // Display the response message
        }
    })
    .fail(function(xhr, status, error) {
        // Display error message if AJAX request fails
        console.error('Error:', error);
        alert('An error occurred while processing your request. Please try again later.');
    });

    });

// Forgot Password functionality
$('#forgotPasswordLink').click(function(e) {
    e.preventDefault(); // Prevent default link behavior

    var bankEmail = prompt("Please enter your email address:");
    if (bankEmail === null || bankEmail.trim() === "") {
        alert("Email address cannot be empty.");
        return;
    }

    // Send AJAX request to the ForgotPassword endpoint
    var settings = {
        "url": "http://localhost:8080/api/banks/ForgotPassword",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Content-Type": "application/json"
        },
        "data": JSON.stringify({ bankEmail: bankEmail }),
    };

    $.ajax(settings)
    .done(function(response) {
        // Check if the response indicates that the password reset email was sent successfully
        if (response === "Password reset email sent successfully.") {
            // Redirect the user to the password reset page
            window.location.href = '/reset-password.html'; // Replace '/reset-password.html' with your actual reset password page URL
        } else {
            // Display other responses from the server
            alert(response);
        }
    })
    .fail(function(xhr, status, error) {
        // Display error message if AJAX request fails
        console.error('Error:', error);
        alert('An error occurred while processing your request. Please try again later.');
    });

});
});