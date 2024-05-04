$(document).ready(function() {
    $('#loginForm').submit(function(e) {
        e.preventDefault(); // Prevent form submission

        var employeeId = $("#employeeId").val();
        var password = $("#password").val();

        // Check if username and password are not empty
        if (employeeId.trim() === "" || password.trim() === "") {
            alert("Please enter both employeeId and password.");
            return;
        }

        var loginData = {
            employeeId: employeeId,
            password: password
        };

        console.log("employeeId:", employeeId);
        console.log("password:", password);


       

    $.ajax({
        "url": "http://localhost:8080/api/employee/login",
            "method": "POST",
            "timeout": 0,
            "headers": {
              "Content-Type": "application/json"
            },
            "data": JSON.stringify(loginData),
            success: function(response) {
                // Handle successful response
                console.log("Employee login:", response);
                alert("Employee login successfully!"); // Display success message
                // Redirect the user to the login page
                window.location.href = "/EmployeeHome.html";
            },
            error: function(xhr, status, error) {
                // Handle error response
                console.error("Error login employee:", error);
                alert("An error occurred while login the employee. Please try again later.");
                // You can display an error message to the user
            }
    });
    });

// Forgot Password functionality
$('#forgotPasswordLink').click(function(e) {
    e.preventDefault(); // Prevent default link behavior

    var employeeEmail = prompt("Please enter your email address:");
    if (employeeEmail === null || employeeEmail.trim() === "") {
        alert("Email address cannot be empty.");
        return;
    }

    // Send AJAX request to the ForgotPassword endpoint
    var settings = {
        "url": "http://localhost:8080/api/employee/forgotPassword",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Content-Type": "application/json"
        },
        "data": JSON.stringify({ employeeEmail: employeeEmail }),
    };

    $.ajax(settings)
    .done(function(response) {
        // Check if the response indicates that the password reset email was sent successfully
        if (response === "Password reset email sent successfully.") {
            // Redirect the user to the password reset page
            window.location.href = '/resetPasswordEmp.html'; // Replace '/reset-password.html' with your actual reset password page URL
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