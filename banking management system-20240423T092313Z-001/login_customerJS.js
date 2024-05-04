$(document).ready(function() {
    $('#loginForm').submit(function(e) {
        e.preventDefault(); // Prevent form submission

        var userName = $("#userName").val();
        var password = $("#password").val();

       // Check if username and password are not empty strings
       if (typeof userName !== 'string' || typeof password !== 'string' || !userName.trim() || !password.trim()) {
        alert("Please enter both userName and password.");
        return;
    }

        var loginData = {
            userName: userName,
            password: password
        };

        console.log("userName:", userName);
        console.log("Password:", password);


        var settings = {
            "url": "http://localhost:8080/api/customers/login",
            "method": "POST",
            "timeout": 0,
            "headers": {
              "Content-Type": "application/json"
            },
            "data": JSON.stringify(loginData),
        };

        $.ajax(settings)
        .done(function(response, textStatus, xhr) {
            if (xhr.status === 200) {
                // Login successful
                alert("Login successful!");
                localStorage.setItem('loggedInUserName', userName);

                console.log("Redirecting to dashboard...");
                window.location.href = '/CustomerHome.html';
            } else {
                // Login failed
                alert("Invalid username or password. Please try again.");
            }
        })
        .fail(function(xhr, status, error) {
            console.error('Error:', error);
            alert('An error occurred while processing your request. Please try again later.');
        });
    });

// Forgot Password functionality
$('#forgotPasswordLink').click(function(e) {
    e.preventDefault(); // Prevent default link behavior

    var email = prompt("Please enter your email address:");
    if (email === null || email.trim() === "") {
        alert("Email address cannot be empty.");
        return;
    }

    // Send AJAX request to the ForgotPassword endpoint
    var settings = {
        "url": "http://localhost:8080/api/employee/ForgotPassword",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Content-Type": "application/json"
        },
        "data": JSON.stringify({ email: email }),
    };

    $.ajax(settings)
    .done(function(response) {
        // Check if the response indicates that the password reset email was sent successfully
        if (response === "Password reset email sent successfully.") {
            // Redirect the user to the password reset page
            window.location.href = '/resetPasswordCust.html'; // Replace '/reset-password.html' with your actual reset password page URL
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