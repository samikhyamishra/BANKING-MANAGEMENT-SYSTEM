$(document).ready(function () {
    // Function to generate a random account number
    function generateAccountNumber() {
        // Generate a random number between 100000000 and 999999999
        return Math.floor(100000000 + Math.random() * 900000000);
    }
    function generateBranchId() {
        // Generate a random number between 100000000 and 999999999
        return Math.floor(100000000 + Math.random() * 900000000);
    }
    // Populate the account number field with a random number on page load

    $("#registration-form").submit(function (event) {
        // Prevent the default form submission
        event.preventDefault();
        if (validateForm()) {
            registerUser(); // Call the registerUser function to handle registration
        }

        function validateForm() {
            return validateUserName() && validatePassword() && validateConfirmPassword();
        }
        
        function validateUserName() {
            var userName = $("#user_name").val();
            if (/\s/.test(userName)) {
                displayErrorMessage("User name must not contain any space.");
                return false;
            }
            return true;
        }
        
        function validatePassword() {
            var password = $("#password").val();
            if (password.length < 8) {
                displayErrorMessage("Password should contain at least 8 characters.");
                return false;
            } else if (!/(?=.[\d])(?=.[A-Z])(?=.[a-z])(?=.[!@#$%^&])[\w!@#$%^&]{8,}$/.test(password)) {
                displayErrorMessage("Password should contain Uppercase, lowercase, and numeric characters.");
                return false;
            }
            return true;
        }
        
        function validateConfirmPassword() {
            var password = $("#password").val();
            var confirmPassword = $("#repassword").val();
            if (password !== confirmPassword) {
                displayErrorMessage("Confirm Password must be the same as the Password.");
                return false;
            }
            return true;
        }
        
        function displayErrorMessage(message) {
            $("#message").text(message);
        }
        
        // Generate account number
        var accountNo = generateAccountNumber();
        var branchId = generateBranchId();

        // Get form data
        var formData = {
            "accountNo": accountNo, 
            "accountType": $("#accountType").val(),
            "accountHolderName": $("#accountHolderName").val(),
            "phoneNo": $("#phoneNo").val(),
            "address": $("#address").val(),
            "userName": $("#userName").val(),
            "email": $("#email").val(),
            "password": $("#password").val(),
            "createdById": 1,
            "branchId": branchId 
        };

        // Send POST request to create account
        $.ajax({
            url: "http://localhost:8080/api/accounts/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                console.log("Account created:", response);
                // Redirect to login_customer.html after successful account creation
                window.location.href = 'login_customer.html';
            },
            error: function (xhr, status, error) {
                console.error("Error creating account:", error);
                // Handle error (display error message, etc.)
            }
        });
        
    });
});

