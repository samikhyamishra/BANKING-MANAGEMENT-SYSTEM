$(document).ready(function () {
    $("#employee-registration-form").submit(function (event) {
        // Prevent the default form submission
        event.preventDefault();

        if (validateForm()) {
            registerEmployee(); // Call the registerEmployee function to handle registration
        }
    });

    function validateForm() {
        return validateEmployeeName() && validateEmployeePhoneNumber() && validateEmployeeEmail() && validatePassword();
    }

    function validateEmployeeName() {
        var employeeName = $("#employeeName").val();
        if (!/^[a-zA-Z\s]{3,}$/.test(employeeName)) {
            displayErrorMessage("Enter a valid name (at least 3 characters and only alphabets).");
            return false;
        }
        return true;
    }

    function validateEmployeePhoneNumber() {
        var employeePhoneNumber = $("#employeePhoneNumber").val();
        if (!/^[0-9]{10}$/.test(employeePhoneNumber)) {
            displayErrorMessage("Enter a valid 10-digit phone number.");
            return false;
        }
        return true;
    }

    function validateEmployeeEmail() {
        var employeeEmail = $("#employeeEmail").val();
        if (!/\S+@\S+\.\S+/.test(employeeEmail)) {
            displayErrorMessage("Enter a valid email address.");
            return false;
        }
        return true;
    }

    function validatePassword() {
        var password = $("#password").val();
        if (!/(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&])[\w!@#$%^&]{8,}$/.test(password)) {
            displayErrorMessage("Password must contain at least one number, one uppercase letter, one lowercase letter, one special character, and be at least 8 characters long.");
            return false;
        }
        return true;
    }

    function displayErrorMessage(message) {
        $("#error-message").text(message);
    }

    function registerEmployee() {
        var formData = {
            "employeeName": $("#employeeName").val(),
            "employeeAddress": $("#employeeAddress").val(),
            "employeePhoneNumber": $("#employeePhoneNumber").val(),
            "employeeEmail": $("#employeeEmail").val(),
            "password": $("#password").val()
        };

        $.ajax({
            "url": "http://localhost:8080/api/employee/empRegister",
            "method": "POST",
            "timeout": 0,
            "headers": {
              "Content-Type": "application/json"
            },
            data: JSON.stringify(formData),
            success: function (response) {
                // Handle successful response
                console.log("Employee registered:", response);
                window.location.href="/login_employee.html";
                alert(response); // Display success message
                // You can redirect the user to another page if needed
            },
            error: function (xhr, status, error) {
                // Handle error response
                console.error("Error registering employee:", error);
                alert("An error occurred while registering the employee. Please try again later.");
                // You can display an error message to the user
            }
        });
    }
});