$(document).ready(function () {
    $("#branch-registration-form").submit(function (event) {
        // Prevent the default form submission
        event.preventDefault();

        // Call the registerBranch function to handle branch registration
        registerBranch();
    });

    function registerBranch() {
        // Get the values from the form fields
        var branchName = $("#branchName").val();
        var branchAddress = $("#branchAddress").val();
        var createdBy = 1; // Hardcoded value for created by

        // Create an object with the branch data
        var branchData = {
            "branchName": branchName,
            "branchAddress": branchAddress,
            "createdBy": createdBy
        };

        // Send a POST request to create the branch
        $.ajax({
            url: "http://localhost:8080/api/branches/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(branchData),
            success: function (response) {
                // Handle successful response
                console.log("Branch registered:", response);
                alert("Branch registered successfully!");
                window.location.href= "/AdminHome.html";
                // You can redirect the user to another page if needed
            },
            error: function (xhr, status, error) {
                // Handle error response
                console.error("Error registering branch:", error);
                alert("An error occurred while registering the branch. Please try again later.");
                // You can display an error message to the user
            }
        });
    }
});
